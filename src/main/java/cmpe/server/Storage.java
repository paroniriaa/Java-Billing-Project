package cmpe.server;
import cmpe.utility.ErrorHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class Storage {
    private static final Logger logger = LogManager.getLogger(Storage.class);

    private HashMap<String, Item> itemList = new HashMap<>();

    public HashMap<String, Item> getItemList() {
        return itemList;
    }

    public void setItemList(HashMap<String, Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "Storage {" +
                "ItemList=" + itemList +
                '}';
    }

    public Storage(List<List<String>> storageInfo) {
        logger.info("Creating storage entity...");
        checkAndSetStorageList(storageInfo);
        logger.info("Storage entity created: " + this);
    }

    private void checkAndSetStorageList(List<List<String>> storageInfo){
        logger.info("Checking storage info...");
        storageInfo.remove(0);
        for (List<String> entry : storageInfo){
            if(isValidEntrySize(entry.size())){
                Item newItem = createNewItemFromEntry(entry);
                itemList.put(newItem.getItemName(), newItem);
            }
            else{
                ErrorHandler.logErrorToFileAndExit(logger,"Current processing entry: " + entry +
                        " have column less than 4, which implies incomplete info for this item.");
            }
        }
    }

    private Boolean isValidEntrySize(int entrySize){
        //logger.info("Checking if current entry size" + entrySize + "has valid length...");
        return entrySize > 3;
    }

    private Item createNewItemFromEntry(List<String> entry){
        logger.info("Adding new item from the current entry..." + entry);
        Item newItem = new Item();
        String fetchedName = String.valueOf(entry.get(0));
        String itemName = fetchedName.substring(0, 1).toUpperCase() + fetchedName.substring(1).toLowerCase();
        String itemCategoryName = String.valueOf(entry.get(1));
        Item.Category itemCategory = newItem.findCategoryByName(itemCategoryName);
        int itemQuantity = Integer.parseInt(entry.get(2));
        int itemPrice = Integer.parseInt(entry.get(3));

        checkAndSetItemName(newItem, itemName);
        checkAndSetItemCategory(newItem, itemCategory);
        checkAndSetItemQuantity(newItem, itemQuantity);
        checkAndSetItemPrice(newItem, itemPrice);

        return newItem;
    }

    private void checkAndSetItemName(Item newItem, String itemName){
        if (isValidItemName(itemName)){
            newItem.setItemName(itemName);
        }
        else{
            ErrorHandler.logErrorToFileAndExit(logger, "Current processing item has item name " + itemName +
                    ", which is blank or not in ASCII form.");
        }
    }

    private Boolean isValidItemName(String itemName){
        //logger.info("Checking if item " + itemName + "has valid item name...");
        return !(itemName.isBlank() && itemName.matches("[a-zA-Z]+"));
    }

    private void checkAndSetItemCategory(Item newItem, Item.Category itemCategory){
        for (Item.Category markedCategory : Item.Category.values()){
            if (itemCategory == markedCategory){
                newItem.setItemCategory(itemCategory);
                break;
            }
        }
        if(newItem.getItemCategory() == null){
            ErrorHandler.logErrorToFileAndExit(logger, "Current processing item has item category " + itemCategory +
                    ", the only accepted item category are as follows: " + Arrays.toString(Item.Category.values()));
        }
    }

    private void checkAndSetItemQuantity(Item newItem, int itemQuantity){
        if (itemQuantity >= 0){
            newItem.setItemQuantity(itemQuantity);
        }
        else{
            ErrorHandler.logErrorToFileAndExit(logger, "Current processing item has item quantity " + itemQuantity +
                    ", which less than minimal quantity 0.");
        }
    }

    private void checkAndSetItemPrice(Item newItem, int itemPrice){
        if (itemPrice > 0){
            newItem.setItemPrice(itemPrice);
        }
        else{
            ErrorHandler.logErrorToFileAndExit(logger, "Current processing item has item price " + itemPrice +
                    ", which less than or equal to minimal price 0.");
        }
    }

}
