package cmpe.client;

import java.util.HashMap;
import java.util.List;

public class Client {
    public enum CardNumberType {
        CARD_NUMBER_A("5.41E+15"),
        CARD_NUMBER_B("4.12E+12"),
        CARD_NUMBER_C("3.41E+14"),
        CARD_NUMBER_D("6.01E+15");

        public final String label;

        CardNumberType(String label) {
            this.label = label;
        }
    }

    private CardNumberType shoppingCardType;
    private String shoppingCardNumber;
    private HashMap<String, Integer> shoppingList;

    public Client(){

    }

    public Client(List<List<String>> shoppingInfo) {
        String cardNumberLabel = String.valueOf(shoppingInfo.get(1).get(2));
        CardNumberType cardNumberType = findCardNumberTypeByLabel(cardNumberLabel);
        if (cardNumberType != null){
            setShoppingCardType(cardNumberType);
            setShoppingCardNumber(cardNumberType.label);
        }

        HashMap<String, Integer> list = new HashMap<>();
        if (shoppingInfo != null && !shoppingInfo.isEmpty()){
            shoppingInfo.remove(0);
            for (List entry : shoppingInfo){
                String fetchedName = String.valueOf(entry.get(0));
                String itemName = fetchedName.substring(0, 1).toUpperCase() + fetchedName.substring(1).toLowerCase();
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

    public CardNumberType getShoppingCardType() {
        return shoppingCardType;
    }

    public void setShoppingCardType(CardNumberType shoppingCardType) { this.shoppingCardType = shoppingCardType; }

    public String getShoppingCardNumber() { return shoppingCardNumber; }

    public void setShoppingCardNumber(String shoppingCardNumber) { this.shoppingCardNumber = shoppingCardNumber; }

    public HashMap<String, Integer> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(HashMap<String, Integer> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public CardNumberType findCardNumberTypeByLabel(String cardNumberLabel) {
        for (CardNumberType type : CardNumberType.values()) {
            if (type.label.equals(cardNumberLabel)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Client {" +
                "shoppingCardType='" + shoppingCardType + '\'' +
                ", shoppingCardNumber='" + shoppingCardNumber + '\'' +
                ", shoppingList=" + shoppingList +
                '}';
    }
}
