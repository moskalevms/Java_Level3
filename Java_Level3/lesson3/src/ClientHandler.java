

import auth.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ClientHandler {

    private static final Pattern MESSAGE_PATTERN = Pattern.compile("^/w (\\w+) (.+)", Pattern.MULTILINE);
    private static final String MESSAGE_SEND_PATTERN = "/w %s %s";
    private static final Pattern CHANGE_LOGIN_PATTERN = Pattern.compile("^/w changeLogin (\\w+)");


    private final Thread handleThread;
    private final DataInputStream inp;
    private final DataOutputStream out;
    private final ChatServer server;
    private final User user;
    private final Socket socket;

    public ClientHandler(User user, Socket socket, ChatServer server) throws IOException {
        this.user = user;
        this.socket = socket;
        this.server = server;
        this.inp = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        this.handleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        String msg = inp.readUTF();
                        System.out.printf("Message from user %s: %s%n", ClientHandler.this.user.login, msg);

                        Matcher changeLoginMatcher = CHANGE_LOGIN_PATTERN.matcher(msg);
                        if (changeLoginMatcher.matches()) {
                            String newLogin = changeLoginMatcher.group(1);
                            server.changeLogin(user, newLogin);
                            continue;
                        }

                        Matcher matcher = MESSAGE_PATTERN.matcher(msg);
                        if (matcher.matches()) {
                            String userTo = matcher.group(1);
                            String message = matcher.group(2);
                            server.sendMessage(user, userTo, message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.printf("Client %s disconnected%n", ClientHandler.this.user);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribeClient(ClientHandler.this);
                }
            }
        });
        handleThread.start();
    }

    public void sendMessage(String userTo, String msg) throws IOException {
        out.writeUTF(String.format(MESSAGE_SEND_PATTERN, userTo, msg));
    }

    public User getUser() {
        return user;
    }

    public void sendUsersList(String allUsers) throws IOException {
        out.writeUTF(allUsers);
    }
}
