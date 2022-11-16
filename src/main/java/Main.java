import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            System.out.println("Сервер запущен!");
            MaximumCategories maximumCategories = new MaximumCategories();
            maximumCategories.readTsvFile();
            while (true) {
                try (Socket socket = serverSocket.accept(); // ждем подключения
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream());) {
                    maximumCategories.readJsonFile(in.readLine());
                    maximumCategories.saveJsonFile();
                    out.println(maximumCategories.getOutJsonFile());
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}

