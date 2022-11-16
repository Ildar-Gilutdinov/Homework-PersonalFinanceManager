import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductBuy {

    private final String title;
    private final Date date;
    private final long sum;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");

    public ProductBuy(String title, long sum) {
        this.title = title;
        this.date = new Date();
        this.sum = sum;
    }

    public String stringToJson() { //вывода
        return "{\"title\": " + "\"" + title + "\", \"date\": " + "\"" + formatter.format(date) + "\", \"sum\": " + sum + "}";
    }

    public void saveJson(String s) throws FileNotFoundException { //метод сохранения
        try (PrintWriter out = new PrintWriter("request.json")) {
            out.println(s);
        }
    }
}
