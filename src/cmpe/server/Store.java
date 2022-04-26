package cmpe.server;

import java.util.HashMap;
import java.util.List;

public class Store {
    private HashMap<String, Item> ItemList;

    public HashMap<String, Item> getItemList() {
        return ItemList;
    }

    public void setItemList(HashMap<String, Item> itemList) {
        ItemList = itemList;
    }

    public Store(List<List<String>> storageInfo) {
        HashMap<String, Item> list = new HashMap<>();
        if (storageInfo != null && !storageInfo.isEmpty()){
            storageInfo.remove(0);
            for (List entry : storageInfo){
                Item newItem = new Item();
                String fetchedName = String.valueOf(entry.get(0));
                String itemName = fetchedName.substring(0, 1).toUpperCase() + fetchedName.substring(1).toLowerCase();
                String itemCategoryName = String.valueOf(entry.get(1));
                Item.Category itemCategory = newItem.findCategoryByName(itemCategoryName);
                Integer itemQuantity = Integer.parseInt((String) entry.get(2));
                Integer itemPrice = Integer.parseInt((String) entry.get(3));

                if (itemName != null && !itemName.isBlank() && itemName.matches("[a-zA-Z]+")){
                    newItem.setItemName(itemName);
                }
                if (itemCategory != null){
                    newItem.setItemCategory(itemCategory);
                }
                if (itemQuantity != null && itemQuantity >= 0){
                    newItem.setItemQuantity(itemQuantity);
                }
                if (itemPrice != null && itemPrice >= 0){
                    newItem.setItemPrice(itemPrice);
                }
                list.put(itemName, newItem);
            }
            setItemList(list);
        }
    }

    @Override
    public String toString() {
        return "Store {" +
                "ItemList=" + ItemList +
                '}';
    }
}
