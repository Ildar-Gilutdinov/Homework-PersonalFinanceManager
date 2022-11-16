import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MaximumCategories implements Serializable {

    protected Map<String, String> categoriesTsv;
    protected Map<String, Long> categoryJson;
    protected Map<String, Object> mapValueMax;
    private final String outJsonFile;

    public MaximumCategories() {
        categoriesTsv = new HashMap<>();
        categoryJson = new HashMap<>();
        mapValueMax = new HashMap<>();
        outJsonFile = "outJsonFile.json";
    }

    public String getOutJsonFile() {
        return outJsonFile;
    }

    public void saveJsonFile() throws IOException { // сохраняем в json
        JSONObject obj = new JSONObject();
        obj.put("MaximumCategory", maxValueMap());
        try (PrintWriter file = new PrintWriter(outJsonFile)) {
            file.write(obj.toJSONString());
        }
    }

    public void readJsonFile(String jsonFile) { // проверяем категорию товара
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(jsonFile));
            JSONObject jsonObject = (JSONObject) obj;
            String title = (String) jsonObject.get("title");
            long sum = (Long) jsonObject.get("sum");
            if (categoriesTsv.containsKey(title)) {
                if (!categoryJson.containsKey(categoriesTsv.get(title))) {
                    categoryJson.put(categoriesTsv.get(title), sum);
                } else {
                    Long sumOld = categoryJson.get(categoriesTsv.get(title));
                    categoryJson.put(categoriesTsv.get(title), sumOld + sum);
                }
            } else if (categoryJson.containsKey("другое")) {
                long sumOld = categoryJson.get("другое");
                categoryJson.put("другое", sumOld + sum);
            } else {
                categoryJson.put("другое", sum);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void readTsvFile() throws IOException {  //считываем categories.tsv
        try (BufferedReader tsVFile = new BufferedReader(new FileReader("categories.tsv"))) {
            String tsv = tsVFile.readLine();
            while (tsv != null) {
                String[] tsvArray = tsv.split("\t");
                categoriesTsv.put(tsvArray[0], tsvArray[1]);
                tsv = tsVFile.readLine();
            }
        }
    }

    public Map<String, Object> maxValueMap() {
        String category = null;
        long sum = Collections.max(categoryJson.values());
        for (Object s : categoryJson.keySet()) {
            if (categoryJson.get(s).equals(sum)) {
                category = (String) s;
            }
        }
        mapValueMax.put("category", category);
        mapValueMax.put("sum", sum);
        return mapValueMax;
    }


}

