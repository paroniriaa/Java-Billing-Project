package cmpe;

import java.util.HashMap;
import java.util.List;

public class Client {
    private String shoppingCardNumber;
    private HashMap<String, Integer> shoppingList;

    public Client(List<List<String>> shoppingInfo) {
        String cardNumber = shoppingInfo.get(1).get(2);
        if (cardNumber != null && !cardNumber.isBlank()){
            setShoppingCardNumber(cardNumber);
        }

        HashMap<String, Integer> list = new HashMap<>();
        if (shoppingInfo != null && !shoppingInfo.isEmpty()){
            shoppingInfo.remove(0);
            for (List entry : shoppingInfo){
                String itemName = String.valueOf(entry.get(0));
                itemName = itemName.substring(0, 1).toUpperCase() + itemName.substring(1);
                Integer itemQuantity = Integer.parseInt((String) entry.get(1));
                if (itemName != null && !itemName.isBlank() && itemName.matches("[a-zA-Z]+")){
                    list.put(itemName, itemQuantity);
                }
                if (itemQuantity != null && itemQuantity >= 0){
                    list.put(itemName, itemQuantity);
                }
            }
            setShoppingList(list);
        }
    }

    public String getShoppingCardNumber() {
        return shoppingCardNumber;
    }

    public void setShoppingCardNumber(String shoppingCardNumber) {
        this.shoppingCardNumber = shoppingCardNumber;
    }

    public HashMap<String, Integer> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(HashMap<String, Integer> shoppingList) {
        this.shoppingList = shoppingList;
    }
}
