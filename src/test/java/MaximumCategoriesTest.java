import org.junit.jupiter.api.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@DisplayName("Тестирование: MaximumCategories")
class MaximumCategoriesTest {

    MaximumCategories maximumCategories = new MaximumCategories();


    @BeforeEach
    void setUp() throws IOException {
        maximumCategories.readTsvFile();
        maximumCategories.readJsonFile("test.json");
        maximumCategories.maxValueMap();
        System.out.println("Вызываюсь до выполнения теста");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Вызываюсь после вызова теста");
    }

    @Test
    public void maxValueMapTest() {
        Assertions.assertEquals("одежда", maximumCategories.mapValueMax.get("category"));
        Assertions.assertEquals(150, (Long) maximumCategories.mapValueMax.get("sum"));
    }

    @Test
    public void maxValueMapTestOther() {
        maximumCategories.readJsonFile("test1.json");
        maximumCategories.maxValueMap();
        Assertions.assertEquals("другое", maximumCategories.mapValueMax.get("category"));
    }

    @Test
    void readJsonFileTest() {
        Assertions.assertEquals(150, maximumCategories.categoryJson.get("одежда"));
    }
}