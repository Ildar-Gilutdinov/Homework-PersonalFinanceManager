import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static org.json.simple.JSONValue.parse;


public class Client {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название товара и стоимость: ");
        String[] input = scanner.nextLine().split(" ");
        String product = input[0];
        long sum = Long.parseLong(input[1]);
        ProductBuy request = new ProductBuy(product, sum);
        String message = request.stringToJson();
        request.saveJson(message); //сохраняем в json

        try (Socket socket = new Socket("127.0.0.1", 8989);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("request.json"); //считываем сохраненный файл
            System.out.println(parse(new FileReader(in.readLine())).toString());
        }
    }
}