package cmpe.server;

public class Item {
    protected enum Category {
        Essentials,
        Luxury,
        Misc
    }

    private String itemName = null;
    private Category itemCategory = null;
    private Integer itemQuantity = null;
    private Integer itemPrice = null;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Category getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Category itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "Item {" +
                "itemName='" + itemName + '\'' +
                ", itemCategory=" + itemCategory +
                ", itemQuantity=" + itemQuantity +
                ", itemPrice=" + itemPrice +
                '}';
    }

    public Item() {

    }

    public Item(String itemName, Category itemCategory, Integer itemQuantity, Integer itemPrice) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public Category findCategoryByName(String categoryName) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(categoryName)) {
                return category;
            }
        }
        return null;
    }
}
