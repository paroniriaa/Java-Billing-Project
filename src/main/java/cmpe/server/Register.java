package cmpe.server;
import cmpe.utility.ErrorHandler;
import cmpe.client.Client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Register {
    private static final Logger logger = LogManager.getLogger(Register.class);

    private final static Integer ESSENTIALS_ITEM_CAP = 3;
    private final static Integer LUXURY_ITEM_CAP = 4;
    private final static Integer MISC_ITEM_CAP = 6;

    private Client client = null;
    private Storage storage = null;
    private Set<String> shoppingItemsList = new HashSet<>();

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Storage getStore() {
        return storage;
    }

    public void setStore(Storage storage) {
        this.storage = storage;
    }

    public Set<String> getShoppingItemsList() {
        return shoppingItemsList;
    }

    public void setShoppingItemsList(Set<String> shoppingItemsList) {
        this.shoppingItemsList = shoppingItemsList;
    }

    public Register(Client client, Storage storage) {
        this.client = client;
        this.storage = storage;
        this.shoppingItemsList = client.getItemQuantityList().keySet();
        logger.info("Register entity created: " + this);
    }

    @Override
    public String toString() {
        return "Register{" +
                "client=" + client +
                ", storage=" + storage +
                ", shoppingItemsList=" + shoppingItemsList +
                '}';
    }

    public Bill checkOut(){
        logger.info("Check out initiated...");
        itemQuantityCapValidation();
        itemCategoryCapValidation();
        int finalPrice = calculateFinalPrice();
        HashMap<String, Item> billingList = writeBillingList();
        Bill bill = new Bill(finalPrice, billingList);
        logger.info("Check out completed...");
        return bill;
    }

    private void itemQuantityCapValidation(){
        logger.info("Performing item quantity cap validation...");
        for (String item : shoppingItemsList){
            if (storage.getItemList().containsKey(item)){
                Integer itemStorageQuantity = storage.getItemList().get(item).getItemQuantity();
                Integer itemPurchaseQuantity = client.getItemQuantityList().get(item);
                if(itemStorageQuantity >= itemPurchaseQuantity){
                    Integer newQuantity = itemStorageQuantity - itemPurchaseQuantity;
                    storage.getItemList().get(item).setItemQuantity(newQuantity);
                }
                else{
                    ErrorHandler.logErrorToFileAndExit(logger, "Intended purchase item " + item +
                            " with quantity " + itemPurchaseQuantity + " has exceed the current storage quantity of " +
                            itemStorageQuantity);
                }
            }
            else{
                ErrorHandler.logErrorToFileAndExit(logger, "Intended purchase item " + item +
                        " cannot be found in the current storage.");
            }
        }
    }

    private void itemCategoryCapValidation(){
        logger.info("Performing item category cap validation...");
        int essentialsItemCount = 0;
        int luxuryItemCount = 0;
        int miscItemCount = 0;
        for (String item : shoppingItemsList){
            Item.Category itemCategoryType = storage.getItemList().get(item).getItemCategory();
            if (itemCategoryType == Item.Category.Essentials){
                essentialsItemCount = essentialsItemCount + client.getItemQuantityList().get(item);

            }
            else if (itemCategoryType == Item.Category.Luxury ){
                luxuryItemCount = luxuryItemCount + client.getItemQuantityList().get(item);
            }
            else if (itemCategoryType == Item.Category.Misc){
                miscItemCount = miscItemCount + client.getItemQuantityList().get(item);
            }
            else{
                ErrorHandler.logErrorToFileAndExit(logger, "Intended purchase item " + item +
                                            " dose not have corresponding category type.");
            }
        }
        itemCategoryCapComparison(essentialsItemCount, luxuryItemCount, miscItemCount);
    }

    private void itemCategoryCapComparison(int essentialsItemCount, int luxuryItemCount, int miscItemCount){
        logger.info("Performing item category cap comparison...");
        if (essentialsItemCount > ESSENTIALS_ITEM_CAP){
            ErrorHandler.logErrorToFileAndExit(logger,"Intended purchase items within the " +
                    Item.Category.Essentials.name() + " category is " + essentialsItemCount +
                    ", which has been exceeded its cap limit of " + ESSENTIALS_ITEM_CAP);
        }
        if (luxuryItemCount > LUXURY_ITEM_CAP){
            ErrorHandler.logErrorToFileAndExit(logger,"Intended purchase items within the " +
                    Item.Category.Luxury.name() + " category is " + luxuryItemCount +
                    ", which has been exceeded its cap limit of " + LUXURY_ITEM_CAP);
        }
        if (miscItemCount > MISC_ITEM_CAP){
            ErrorHandler.logErrorToFileAndExit(logger,"Intended purchase items within the " +
                    Item.Category.Misc.name() + " category is " + miscItemCount +
                    ", which has been exceeded its cap limit of " + MISC_ITEM_CAP);
        }
    }

    private int calculateFinalPrice(){
        logger.info("Performing final price calculation...");
        int finalPrice = 0;
        int currentItemTotal;
        for (String item : shoppingItemsList){
            currentItemTotal = storage.getItemList().get(item).getItemPrice() * client.getItemQuantityList().get(item);
            finalPrice = finalPrice + currentItemTotal;
        }
        return finalPrice;
    }

    private HashMap<String, Item> writeBillingList() {
        logger.info("Performing billing info writing...");
        HashMap<String, Item> billingList = new HashMap<>();
        for (String item : shoppingItemsList) {
            billingList.put(item, storage.getItemList().get(item));
            billingList.get(item).setItemQuantity(client.getItemQuantityList().get(item));
        }
        return billingList;
    }
}
