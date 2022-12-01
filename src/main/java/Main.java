import com.google.gson.Gson;

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

            Gson gson = new Gson();
            MaximumCategories maximumCategories = new MaximumCategories();
            maximumCategories.readTsvFile();

            while (true) {
                try (Socket socket = serverSocket.accept(); // ждем подключения
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
                    String input = in.readLine();
                    ProductBuy client = gson.fromJson(input, ProductBuy.class);
                    maximumCategories.readJsonFile(client);

                    String server = maximumCategories.stringToJson();
                    System.out.println("Траты клиента: ");
                    System.out.println(server);
                    out.println(server);

                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}

