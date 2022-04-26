package cmpe;

import cmpe.client.Client;
import cmpe.server.Store;
import cmpe.utility.FileHandler;
import cmpe.utility.RecordHandler;

import static java.lang.System.exit;

public class Billing {

    public static void main(String[] args) {
        try {
            String shoppingListFile = "src/input/shopping_list.csv";
            String storageListFile= "src/input/storage_list.csv";
            FileHandler.deletePreviousLogFile();
            RecordHandler shoppingRecord = new RecordHandler(shoppingListFile);
            RecordHandler storageRecord = new RecordHandler(storageListFile);
            System.out.println(shoppingRecord);
            System.out.println(storageRecord);
            Client client = new Client(shoppingRecord.getRecord());
            System.out.println(client);
            Store store = new Store(storageRecord.getRecord());
            System.out.println(store);
        }
        catch (Exception e) {
            FileHandler.logErrorToFile(e);
            exit(-1);
        }
    }
}
