package cmpe.utility;

import cmpe.server.Bill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileHandler {
    private static final String shoppingListFile = "src/input/shopping_list.csv";
    private static final String storageListFile = "src/input/storage_list.csv";
    private static final String billingListFile = "src/output/billing_list.csv";
    private static final String normalLogFile = "src/output/billing_log.log";
    private static final String errorTxtFile = "src/output/error.txt";

    private final static String COMMA_DELIMITER = ",";

    private static final Logger logger = LogManager.getLogger(FileHandler.class);

    private String filePath = "";
    private List<List<String>> fileRecord = new ArrayList<>();

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<List<String>> getFileRecord() {
        return fileRecord;
    }

    public void setFileRecord(List<List<String>> fileRecord) {
        this.fileRecord = fileRecord;
    }

    public static String getShoppingListFilePath() {
        return shoppingListFile;
    }

    public static String getStorageListFilePath() {
        return storageListFile;
    }

    public static String getBillingListFilePath() {
        return billingListFile;
    }

    public FileHandler(String filePath) {
        if(isValidFilePath(filePath)){
            setFilePath(filePath);
        }
        else{
            ErrorHandler.logErrorToFileAndExit(logger, "Path " + filePath + " does not point to any file.");
        }

        List<List<String>> fetchedRecord = getRecordFromFile(filePath);
        if(isValidRecord(fetchedRecord)){
            setFileRecord(fetchedRecord);
        }
        else{
            ErrorHandler.logErrorToFileAndExit(logger, "File with path " + filePath + " is an empty file or does not contain any concrete item info.");
        }
    }

    public FileHandler(Bill bill){
        setFilePath(getBillingListFilePath());
        setFileRecord(bill.generateBillingInfo());
    }

    @Override
    public String toString() {
        return "FileHandler{" +
                "filePath='" + filePath + '\'' +
                ", fileRecord=" + fileRecord +
                '}';
    }

    private Boolean isValidFilePath(String filePath){
        return !filePath.isBlank();
    }

    private Boolean isValidRecord(List<List<String>> fetchedRecord){
        return !fetchedRecord.isEmpty() && fetchedRecord.size() > 1;
    }

    public List<List<String>> getRecordFromFile(String csvFilePath){
        //List<List<String>> convertedRecords = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(csvFilePath))) {
            while (scanner.hasNextLine()) {
                fileRecord.add(getRecordEntryFromFileLine(scanner.nextLine()));
            }
        } catch (Exception e) {
            ErrorHandler.logExceptionToFileAndExit(logger, e);
        }
        return fileRecord;
    }

    private List<String> getRecordEntryFromFileLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public void getFileFromRecord() {
        logger.info("Generating billing_list.csv from the billing record...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))){
            for (List<String> recordEntry : fileRecord){
                String recordEntryString = recordEntry.stream().map(Object::toString).collect(Collectors.joining(COMMA_DELIMITER));
                writer.write(recordEntryString);
                writer.newLine();
            }
        } catch (Exception e) {
            ErrorHandler.logExceptionToFileAndExit(logger, e);
        }
    }

    public static void deletePreviousGeneratedFiles() {
        deletePreviousErrorLogFile();
        deletePreviousNormalLogFile();
        deletePreviousBillingFile();
    }

    private static void deletePreviousErrorLogFile() {
        File previousErrorTxtFile = new File(errorTxtFile);
        if (previousErrorTxtFile.delete()) {
            logger.info("Deleted the previous error txt file: " + previousErrorTxtFile.getName() + ", continue to the shopping process...");
        } else {
            logger.info("No previous error txt file found to be deleted, continue to the shopping process...");
        }
    }

    private static void deletePreviousNormalLogFile() {
        File previousBillingLogFile = new File(normalLogFile);
        if (previousBillingLogFile.delete()) {
            logger.info("Deleted the previous billing log file: " + previousBillingLogFile.getName() + ", continue to the shopping process...");
        } else {
            logger.info("No previous billing log file found to be deleted, continue to the shopping process...");
        }
    }

    private static void deletePreviousBillingFile() {
        File previousBillingCsvFile = new File(billingListFile);
        if (previousBillingCsvFile.delete()) {
            logger.info("Deleted the previous billing csv file: " + previousBillingCsvFile.getName() + ", continue to the shopping process...");
        } else {
            logger.info("No previous billing csv file found to be deleted, continue to the shopping process...");
        }
    }
}
