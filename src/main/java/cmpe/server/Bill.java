package cmpe.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Bill {
    private static final Logger logger = LogManager.getLogger(Bill.class);

    Integer totalPrice = 0;
    HashMap<String, Item> billingList = new HashMap<>();

    protected Integer getTotalPrice() {
        return totalPrice;
    }

    protected void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    protected HashMap<String, Item> getBillingList() {
        return billingList;
    }

    protected void setBillingList(HashMap<String, Item> billingList) {
        this.billingList = billingList;
    }

    public Bill(Integer totalPrice, HashMap<String, Item> billingList) {
        logger.info("Creating bill entity...");
        this.totalPrice = totalPrice;
        this.billingList = billingList;
        logger.info("Bill entity created: " + this);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "totalPrice=" + totalPrice +
                ", billingList=" + billingList +
                '}';
    }


    public List<List<String>> generateBillingInfo() {
        logger.info("Generating billing record from bill...");
        List<List<String>> billingRecord = new ArrayList<>();
        List<String> header = new ArrayList<>();
        Collections.addAll(header, "Item", "Quantity", "Price", "TotalPrice");
        billingRecord.add(header);
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
    }
}
