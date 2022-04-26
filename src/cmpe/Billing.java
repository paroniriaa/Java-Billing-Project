package cmpe;

import cmpe.client.Client;
import cmpe.server.Bill;
import cmpe.server.Register;
import cmpe.server.Store;
import cmpe.utility.FileHandler;
import cmpe.utility.RecordHandler;

import static java.lang.System.exit;

public class Billing {

    public static void main(String[] args) {
        String shoppingListFile = "src/input/shopping_list.csv";
        String storageListFile = "src/input/storage_list.csv";
        String billingListFile = "src/output/billing_list.csv";

        try {
            FileHandler.deletePreviousErrorLogFile();
            FileHandler.deletePreviousBillingFile();
            RecordHandler shoppingRecord = new RecordHandler(shoppingListFile);
            //System.out.println(shoppingRecord);
            RecordHandler storageRecord = new RecordHandler(storageListFile);
            //System.out.println(storageRecord);
            Client client = new Client(shoppingRecord.getRecord());
            //System.out.println(client);
            Store store = new Store(storageRecord.getRecord());
            //System.out.println(store);
            Register register = new Register(client, store);
            Bill bill = register.checkOut();
            RecordHandler billingRecord = new RecordHandler(billingListFile, bill);
            billingRecord.getFileFromRecord();


        }
        catch (Exception e) {
            FileHandler.logErrorToFile(e);
            exit(-1);
        }
    }
}
