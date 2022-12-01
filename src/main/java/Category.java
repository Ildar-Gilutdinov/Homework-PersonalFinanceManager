import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Category {

    private final Set<String> items = new TreeSet<>();
    private final String categoryName;
    private int sum;

    private final List addedItems = new ArrayList<ProductBuy>();

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean itemCategorised(String itemName) {
        return items.contains(itemName);
    }

    public void addItem(String itemName) {
        items.add(itemName);
    }


    public boolean totalSum(ProductBuy productBuy) {
        if (itemCategorised(productBuy.title)) {
            sum += productBuy.sum;
            addedItems.add(productBuy);
            return true;
        }
        return false;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getSum() {
        return sum;
    }

}