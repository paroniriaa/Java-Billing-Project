package cmpe.server;

import java.util.*;

public class Bill {
    Integer totalPrice;
    HashMap<String, Item> billingList;

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HashMap<String, Item> getBillingList() {
        return billingList;
    }

    public void setBillingList(HashMap<String, Item> billingList) {
        this.billingList = billingList;
    }

    public List<List<String>> generateBillingInfo() {
        List<List<String>> billingRecord = new ArrayList<>();
        List<String> header = new ArrayList<>();
        Collections.addAll(header, "Item", "Quantity", "Price", "TotalPrice");
        billingRecord.add(header);
        //System.out.println(billingRecord.toString());
        Set<String> shoppingItemsList = billingList.keySet();
        for (String item : shoppingItemsList){
            List<String> itemEntry = new ArrayList<>();
            Collections.addAll(itemEntry,
                    billingList.get(item).getItemName(),
                    billingList.get(item).getItemQuantity().toString(),
                    billingList.get(item).getItemPrice().toString());
            billingRecord.add(itemEntry);
        }
        billingRecord.get(1).add(totalPrice.toString());
        return billingRecord;
        //System.out.println(billingRecord.toString());
    }

    @Override
    public String toString() {
        return "Bill{" +
                "totalPrice=" + totalPrice +
                ", billingList=" + billingList +
                '}';
    }
}
