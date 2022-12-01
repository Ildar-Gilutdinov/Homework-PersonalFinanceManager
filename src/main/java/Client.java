import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Client {

    private static final int PORT = 8989;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) throws IOException {
        while (true) {
            try (Socket clientSocket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Введите название товара и стоимость: ");
                String input = scanner.readLine();
                String[] scannerReader = input.split(" ");
                if (input.equals("end")) {
                    System.out.println("Программа завершена");
                    break;
                } else if (scannerReader.length > 1) {
                    String product = scannerReader[0];
                    String date = nowDate();
                    int sum = Integer.parseInt(scannerReader[1]);

                    out.println("{\"title\": \"" + product + "\", \"date\": \"" + date + "\", " +
                            "\"sum\": " + sum + "}");
                    System.out.println("Данные о покупке переданы");
                    System.out.println(in.readLine());

                } else {
                    System.out.println("Неверный ввод.");
                }
            }
        }
    }
    public static String nowDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
