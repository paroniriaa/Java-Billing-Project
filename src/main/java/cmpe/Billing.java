package cmpe;

import cmpe.client.Client;
import cmpe.server.Bill;
import cmpe.server.Register;
import cmpe.server.Storage;
import cmpe.utility.ErrorHandler;
import cmpe.utility.FileHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Billing {

    private static final Logger logger = LogManager.getLogger(Billing.class);

    public static void main(String[] args) {

        try {
            applicationInitialization();
            FileHandler storageFile = new FileHandler(FileHandler.getStorageListFilePath());
            Storage storage = new Storage(storageFile.getFileRecord());
            FileHandler clientFile = new FileHandler(FileHandler.getShoppingListFilePath());
            Client client = new Client(clientFile.getFileRecord());
            Register register = new Register(client, storage);
            Bill bill = register.checkOut();
            FileHandler billFile = new FileHandler(bill);
            billFile.getFileFromRecord();
            applicationTermination();
        }
        catch (Exception e) {
            ErrorHandler.logExceptionToFileAndExit(logger, e);
        }
    }

    public static void applicationInitialization(){
        logger.info("Billing application initiated, shopping process begin...");
        FileHandler.deletePreviousGeneratedFiles();
    }

    public static void applicationTermination(){
        logger.info("shopping process completed, billing application terminated...");
    }
}
