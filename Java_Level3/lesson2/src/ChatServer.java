


import auth.AuthServiceImpl;
import auth.Users;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServer {

    private static final Pattern AUTH_PATTERN = Pattern.compile("^/auth (\\w+) (\\w+)$");

    private AuthServiceImpl authService = new AuthServiceImpl();

    private static Map<String, ClientHandler> clientHandlerMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ChatServer chatServer = new ChatServer();
        chatServer.start(7777);

        //Нужно ли создавать отдельный класс для взаимодействия с БД?
        //Если да, то там нужен статический метод?
        Class.forName("org.sqlite.JDBC"); //ошибка при регистрации драйвера


        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:ChatClient.db")){ //readUsers предоставляет доступ к базе данных
            Statement statement = connection.createStatement(); //statement - хранит команды SQL


            //заготовка на будущее для добавления и удаления пользователей из БД
            PreparedStatement addUsers = connection.prepareStatement("INSERT INTO Users (username, password)");//правильный запрос?
            addUsers.setString(1, "Денис");
            addUsers.setString(2, "789");

            readUsers(statement);

        }

    }

    private static void readUsers(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Users"); //отправка в БД запроса на чтение данных

        Map<Integer, Users> selectResult = new HashMap<>();
        while (resultSet.next()) {
            //Считывание данных из столбцов БД
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");

            //Создаю сущность для авторизации нового клиента
            Users users = new Users();
            users.setId(id);
            users.setUsername(username);
            users.setPassword(password);

            selectResult.put(id, users);

        }


    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream inp = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("New client connected!");

                try {
                    String authMessage = inp.readUTF();
                    Matcher matcher = AUTH_PATTERN.matcher(authMessage);
                    if (matcher.matches()) {
                        String username = matcher.group(1);
                        String password = matcher.group(2);
                        //Тут нужно совместить клиентов из БД с процессом авторизации, но не соображу как это сделать
                        //Нужен ли теперь ClientHandlerMap, раз теперь есть БД
                        //Но если его убрать, класс ClientHandler перестанет работать
                        if (Users.authUser(username, password)) {
                            clientHandlerMap.put(username, new ClientHandler(username, socket, this));
                            out.writeUTF("/auth successful");
                            out.flush();
                            System.out.printf("Authorization for user %s successful%n", username);
                        } else {
                            System.out.printf("Authorization for user %s failed%n", username);
                            out.writeUTF("/auth fails");
                            out.flush();
                            socket.close();
                        }
                    } else {
                        System.out.printf("Incorrect authorization message %s%n", authMessage);
                        out.writeUTF("/auth fails");
                        out.flush();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String userTo, String userFrom, String msg) throws IOException {
        ClientHandler userToClientHandler = clientHandlerMap.get(userTo);
        if (userToClientHandler != null) {
            userToClientHandler.sendMessage(userFrom, msg);
        } else {
            System.out.printf("User %s not found. Message from %s is lost.%n", userTo, userFrom);
        }
    }


}
