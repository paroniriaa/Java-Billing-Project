package cmpe.client;
import cmpe.utility.ErrorHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    protected enum CardNumberType {
        CARD_NUMBER_A("5410000000000000"),
        CARD_NUMBER_B("4120000000000"),
        CARD_NUMBER_C("341000000000000"),
        CARD_NUMBER_D("6010000000000000");

        public final String label;
        CardNumberType(String label) {
            this.label = label;
        }
        String showNumber(){ return label;}
    }

    private CardNumberType cardNumberType = null;
    private String cardNumber = "";
    private HashMap<String, Integer> itemQuantityList = new HashMap<>();

    public CardNumberType getCardNumberType() {
        return cardNumberType;
    }

    public void setCardNumberType(CardNumberType cardNumberType) { this.cardNumberType = cardNumberType; }

    public String getCardNumber() { return cardNumber; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public HashMap<String, Integer> getItemQuantityList() {
        return itemQuantityList;
    }

    public void setItemQuantityList(HashMap<String, Integer> itemQuantityList) { this.itemQuantityList = itemQuantityList; }

    @Override
    public String toString() {
        return "Client{" +
                "cardNumberType=" + cardNumberType +
                ", cardNumber='" + cardNumber + '\'' +
                ", itemQuantityList=" + itemQuantityList +
                '}';
    }

    public Client(List<List<String>> shoppingInfo) {
        logger.info("Creating client entity...");
        checkShoppingInfoHeader(shoppingInfo);
        checkAndSetShoppingCard(shoppingInfo);
        checkAndSetShoppingList(shoppingInfo);
        logger.info("Client entity created: " + this);
    }

    private void checkShoppingInfoHeader(List<List<String>> shoppingInfo){
        logger.info("Checking client shopping list header info...");
        List<String> header = new ArrayList<>();
        Collections.addAll(header,"Item","Quantity","CardNumber");
        if(!shoppingInfo.get(0).equals(header)){
            ErrorHandler.logErrorToFileAndExit(logger, "Cannot identify current client's shopping list header info " +
                    shoppingInfo.get(0) + ", the only accepted header info is as follows:" + header);
        }
    }

    private void checkAndSetShoppingCard(List<List<String>> shoppingInfo){
        logger.info("Checking client shopping list card info...");
        if(hasCardInfo(shoppingInfo.get(1).size())){
            String clientCardNumberLabel = String.valueOf(shoppingInfo.get(1).get(2));
            CardNumberType clientCardNumberType = findCardNumberTypeByLabel(clientCardNumberLabel);
            if(isValidCardNumberType(clientCardNumberType)){
                setCardNumberType(clientCardNumberType);
                assert clientCardNumberType != null;
                setCardNumber(clientCardNumberType.label);
            }else{
                List<String> allValidCreditCardNumbers = new ArrayList<>();
                for(CardNumberType cardNumberType : CardNumberType.values()){
                    allValidCreditCardNumbers.add(cardNumberType.showNumber());
                }
                ErrorHandler.logErrorToFileAndExit(logger, "Cannot identify current client's credit card " +
                        clientCardNumberLabel + ", the only accepted credit cards are as follows:" + allValidCreditCardNumbers);
            }
        }
        else{
            ErrorHandler.logErrorToFileAndExit(logger, "Cannot obtain current client's credit card info, " +
                    "please make sure the credit card number is presented on the 2nd row and 3rd column.");
        }
    }

    private void checkAndSetShoppingList(List<List<String>> shoppingInfo){
        logger.info("Checking client shopping list item info...");
        shoppingInfo.remove(0);
        for (List<String> entry : shoppingInfo){
            if(isValidEntrySize(entry.size())){
                String fetchedName = String.valueOf(entry.get(0));
                String itemName = fetchedName.substring(0, 1).toUpperCase() + fetchedName.substring(1).toLowerCase();
                int itemQuantity = Integer.parseInt(entry.get(1));
                if (isValidItemName(itemName)){
                    if (isValidItemQuantity(itemQuantity)){
                        logger.info("Adding new item from the current entry..." + entry);
                        itemQuantityList.put(itemName, itemQuantity);
                    }
                    else{
                        ErrorHandler.logErrorToFileAndExit(logger, "Current processing entry: " + entry +
                                " has item quantity " + itemQuantity + ", which less than minimal quantity 1.");
                    }
                }
                else{
                    ErrorHandler.logErrorToFileAndExit(logger, "Current processing entry: " + entry +
                            " has item name " + itemName + ", which is blank or not in ASCII form.");
                }
            }
            else{
                ErrorHandler.logErrorToFileAndExit(logger, "Current processing entry: " + entry +
                        " have column less than 2 " + ", which implies in-complete info for this item.");
            }
        }
    }

    private Boolean hasCardInfo(int secondEntrySize){
        //logger.info("Checking if client has credit card...");
        return secondEntrySize > 2;
    }

    private CardNumberType findCardNumberTypeByLabel(String cardNumberLabel) {
        //logger.info("Finding client credit card number type...");
        for (CardNumberType type : CardNumberType.values()) {
            if (type.label.equals(cardNumberLabel)) {
                return type;
            }
        }
        return null;
    }

    private Boolean isValidCardNumberType(CardNumberType clientCardNumberType){
        logger.info("Checking if client has valid credit card number...");
        if(clientCardNumberType == null){
            return false;
        }
        for (CardNumberType markedCardNumberType : CardNumberType.values()){
            if (clientCardNumberType == markedCardNumberType){
                return true;
            }
        }
        return false;
    }

    private Boolean isValidEntrySize(int entrySize){
        //logger.info("Checking if current entry size" + entrySize + " is valid...");
        return entrySize > 1;
    }

    private Boolean isValidItemName(String itemName){
        //logger.info("Checking if current item name " + itemName + " is valid...");
        return !(itemName.isBlank() && itemName.matches("[a-zA-Z]+"));
    }

    private Boolean isValidItemQuantity( int itemQuantity ){
        //logger.info("Checking if current item quantity " + itemQuantity + " is valid...");
        return itemQuantity > 0;
    }
}
