package javafx;

import java.io.*;

public class ChatLog {

    public static void main(String[] args) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ChatLog.txt"))) {
            for (int i = 0; i < 100; i++) {
                writer.write("Java\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("ChatLog.txt"))) {
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
