import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MaximumCategories implements Serializable {

    protected Map<String, String> categoriesTsv;
    protected Map<String, Category> categoryJson;


    public MaximumCategories() {
        categoriesTsv = new HashMap<>();
        categoryJson = new HashMap<>();
    }

    public void readJsonFile(ProductBuy productBuy) { // проверяем категорию товара
        if (categoriesTsv.containsKey(productBuy.title)) {
            Category sumOld = categoryJson.get(categoriesTsv.get(productBuy.title));
            sumOld.totalSum(productBuy);
        } else {
            Category newCategory;
            if (categoryJson.containsKey("другое")) {
                newCategory = categoryJson.get("другое");
            } else {
                newCategory = new Category("другое");
            }
            newCategory.addItem(productBuy.title);
            newCategory.totalSum(productBuy);
            categoriesTsv.put(productBuy.title, "другое");
            categoryJson.put("другое", newCategory);
        }
        System.out.println("Товар \"" + productBuy.title + "\", приобретённый на сумму " + productBuy.sum +
                " р., занесён в категорию \"" + categoriesTsv.get(productBuy.title) + "\".");
    }

    public String stringToJson() {
        Category category = mapValueMax();
        return "\"maxCategory\": {" +
                "    \"category\": \"" + category.getCategoryName() + "\"," +
                "    \"sum\": \"" + category.getSum() + "\"" +
                "  }";
    }

    public void readTsvFile() throws FileNotFoundException {  //считываем categories.tsv
        try {
            File tsvFile = new File("categories.tsv");

            Scanner reader = new Scanner(tsvFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] lineSplit = line.split("\t");

                Category currentCategory;
                String productName = lineSplit[0];
                String categoryName = lineSplit[1];
                if (categoryJson.containsKey(categoryName)) {
                    currentCategory = categoryJson.get(categoryName);
                } else {
                    currentCategory = new Category(categoryName);
                }
                currentCategory.addItem(productName);
                categoriesTsv.put(productName, categoryName);
                categoryJson.put(categoryName, currentCategory);
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Category mapValueMax() {
        int maxSum = Integer.MIN_VALUE;
        Category maxCategory = null;

        for (Map.Entry<String, Category> entry : categoryJson.entrySet()) {
            if (entry.getValue().getSum() > maxSum) {
                maxSum = entry.getValue().getSum();
                maxCategory = entry.getValue();
            }
        }
        return maxCategory;
    }

}

