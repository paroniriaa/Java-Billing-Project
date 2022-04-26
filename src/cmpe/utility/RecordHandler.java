package cmpe.utility;

import cmpe.server.Bill;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RecordHandler {
    private final static String COMMA_DELIMITER = ",";

    private String csvFilePath;
    private List<List<String>> record;

    public RecordHandler(String csvFilePath) {
        setCsvFilePath(csvFilePath);
        setRecord(getRecordFromFile(csvFilePath));
    }

    public RecordHandler(String csvFilePath, Bill bill){
        setCsvFilePath(csvFilePath);
        setRecord(bill.generateBillingInfo());
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public List<List<String>> getRecord() {
        return record;
    }

    public void setRecord(List<List<String>> record) {
        this.record = record;
    }

    public List<List<String>> getRecordFromFile(String csvFilePath){
        List<List<String>> convertedRecords = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(csvFilePath))) {
            while (scanner.hasNextLine()) {
                convertedRecords.add(getRecordEntryFromFileLine(scanner.nextLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedRecords;
    }

    public List<String> getRecordEntryFromFileLine(String line) {
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
        System.out.println("Generating billing_list.csv from the billing record...");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath, false))){
            //System.out.println(record.toString());
            for (List<String> recordEntry : record){
                String recordEntryString =  recordEntry.stream().map(Object::toString).collect(Collectors.joining(COMMA_DELIMITER));
                writer.write(recordEntryString);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "RecordHandler {" +
                "csvFilePath='" + csvFilePath + '\'' +
                ", record=" + record +
                '}';
    }
}
