package cmpe.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecordHandler {
    private final static String COMMA_DELIMITER = ",";

    private String csvFilePath;
    private List<List<String>> record;

    public RecordHandler(String csvFilePath) {
        setCsvFilePath(csvFilePath);
        setRecord(getConvertedRecord(csvFilePath));
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

    public static List<List<String>> getConvertedRecord(String csvFilePath){
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(csvFilePath))) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    public static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    @Override
    public String toString() {
        return "RecordHandler {" +
                "csvFilePath='" + csvFilePath + '\'' +
                ", record=" + record +
                '}';
    }
}
