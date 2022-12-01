import com.google.gson.Gson;
import org.junit.jupiter.api.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DisplayName("Тестирование: MaximumCategories")
class MaximumCategoriesTest {

    public Gson gson = new Gson();
    public String currentDate = Client.nowDate();
    MaximumCategories maximumCategories = new MaximumCategories();

    MaximumCategoriesTest() throws IOException {
    }


    @BeforeEach
    void setUp() throws IOException {
        maximumCategories.readTsvFile();
        System.out.println("Вызываюсь до выполнения теста");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Вызываюсь после вызова теста");
    }

    @Test
    public void readJsonFileMapTest() {
        ProductBuy client = gson.fromJson("{\"title\": \"шапка\", \"date\": \"" + currentDate + "\", \"sum\": 500}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"мыло\", \"date\": \"" + currentDate + "\", \"sum\": 120}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"телевизор\", \"date\": \"" + currentDate + "\", \"sum\": 40000}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"Книга\", \"date\": \"" + currentDate + "\", \"sum\": 1500}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        Assertions.assertEquals(41500, maximumCategories.mapValueMax().getSum());
    }

    @Test
    public void readJsonFileMapTestOther() {
        ProductBuy client = gson.fromJson("{\"title\": \"шапка\", \"date\": \"" + currentDate + "\", \"sum\": 500}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"мыло\", \"date\": \"" + currentDate + "\", \"sum\": 120}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"телевизор\", \"date\": \"" + currentDate + "\", \"sum\": 40000}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"Книга\", \"date\": \"" + currentDate + "\", \"sum\": 1500}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        Assertions.assertEquals("другое", maximumCategories.mapValueMax().getCategoryName());
    }

    @Test
    void getServerResponse() {
        ProductBuy client = gson.fromJson("{\"title\": \"шапка\", \"date\": \"" + currentDate + "\", \"sum\": 500}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"тапки\", \"date\": \"" + currentDate + "\", \"sum\": 350}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        client = gson.fromJson("{\"title\": \"Книга\", \"date\": \"" + currentDate + "\", \"sum\": 350}", ProductBuy.class);
        maximumCategories.readJsonFile(client);

        Assertions.assertEquals("\"maxCategory\": {" + "    \"category\": \"" + "одежда" + "\"," + "    \"sum\": \"" + "850" + "\"" + "  }", maximumCategories.stringToJson());
    }
}