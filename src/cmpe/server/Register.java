package cmpe.server;
import cmpe.client.Client;

import java.util.HashMap;
import java.util.Set;

import static java.lang.System.exit;

public class Register {
    private final static Integer ESSENTIALS_ITEM_CAP = 3;
    private final static Integer LUXURY_ITEM_CAP = 4;
    private final static Integer MISC_ITEM_CAP = 6;
    private Client client;
    private Store store;
    private Bill bill;
    private Set<String> shoppingItemsList;

    public Register() {

    }

    public Register(Client client, Store store) {
        this.client = client;
        this.store = store;
        this.shoppingItemsList = client.getShoppingList().keySet();
        this.bill = new Bill();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Set<String> getShoppingItemsList() {
        return shoppingItemsList;
    }

    public void setShoppingItemsList(Set<String> shoppingItemsList) {
        this.shoppingItemsList = shoppingItemsList;
    }

    public Bill checkOut(){
        if (itemQuantityCapValidation()){
            if(itemCategoryCapValidation()){
                calculateFinalPrice();
                writeBillingList();
                return bill;
            }
            else{
                System.out.println("Item Category Cap Validation failed, shopping process has been terminated.");
                exit(-1);
            }
        }
        else{
            System.out.println("Item Quantity Cap Validation failed, shopping process has been terminated.");
            exit(-1);
        }
        return null;
    }

    public Boolean itemQuantityCapValidation(){
        System.out.println("Performing item quantity cap validation...");
        for (String item : shoppingItemsList){
            if (store.getItemList().containsKey(item)){
                Integer itemStorageQuantity = store.getItemList().get(item).getItemQuantity();
                Integer itemPurchaseQuantity = client.getShoppingList().get(item);
                if(itemStorageQuantity >= itemPurchaseQuantity){
                    Integer newQuantity = itemStorageQuantity - itemPurchaseQuantity;
                    store.getItemList().get(item).setItemQuantity(newQuantity);
                }
                else{
                    System.out.println("Intended purchase item " + item + " with quantity " + itemPurchaseQuantity +
                                        " has exceed the current storage quantity of " + itemStorageQuantity);
                    break;
                }
            }
            else{
                System.out.println("Intended purchase item " + item + " cannot be found in the current storage");
                break;
            }
            return true;
        }
        return false;
    }

    public Boolean itemCategoryCapValidation(){
        System.out.println("Performing item category cap validation...");
        Integer essentialsItemCount = 0;
        Integer luxuryItemCount = 0;
        Integer miscItemCount = 0;
        for (String item : shoppingItemsList){
            Item.Category itemCategoryType = store.getItemList().get(item).getItemCategory();
            if (itemCategoryType == Item.Category.Essentials){
                essentialsItemCount = essentialsItemCount + client.getShoppingList().get(item);
            }
            else if (itemCategoryType == Item.Category.Luxury ){
                luxuryItemCount = luxuryItemCount + client.getShoppingList().get(item);
            }
            else if (itemCategoryType == Item.Category.Misc){
                miscItemCount = miscItemCount + client.getShoppingList().get(item);
            }
            else{
                System.out.println("Intended purchase item " + item +
                                    " dose not have corresponding category type, shopping process has been terminated.");
                exit(-1);
            }
        }
        if (essentialsItemCount > ESSENTIALS_ITEM_CAP){
            System.out.println("Intended purchase items within the " + Item.Category.Essentials.name() +
                                " category is " + essentialsItemCount +
                                ", which has been exceed its cap limit of " + ESSENTIALS_ITEM_CAP);
            exit(-1);
        }
        if (luxuryItemCount > LUXURY_ITEM_CAP){
            System.out.println("Intended purchase items within the " + Item.Category.Luxury.name() +
                                " category is " + luxuryItemCount +
                                ", which has been exceed its cap limit of " + LUXURY_ITEM_CAP);
            exit(-1);
        }
        if (miscItemCount > MISC_ITEM_CAP){
            System.out.println("Intended purchase items within the " + Item.Category.Misc.name() +
                                " category is " + miscItemCount +
                                ", which has been exceed its cap limit of " + MISC_ITEM_CAP);
            exit(-1);
        }
        return true;
    }

    public void calculateFinalPrice(){
        System.out.println("Performing final price calculation...");
        Integer finalPrice = 0;
        Integer currentItemTotal = 0;
        for (String item : shoppingItemsList){
            currentItemTotal = store.getItemList().get(item).getItemPrice() * client.getShoppingList().get(item);
            finalPrice = finalPrice + currentItemTotal;
        }
        bill.setTotalPrice(finalPrice);
    }

    public void writeBillingList(){
        System.out.println("Performing billing record writing...");
        HashMap<String, Item> billingList = new HashMap<>();
        for (String item : shoppingItemsList){
            billingList.put(item, store.getItemList().get(item));
            billingList.get(item).setItemQuantity(client.getShoppingList().get(item));
        }
        bill.setBillingList(billingList);
    }
}
