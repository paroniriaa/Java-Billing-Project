package cmpe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Billing {

    public static void main(String[] args) {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/input/shopping_list.csv"))) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
            Client client = new Client(records);
            System.out.println(client);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final static String COMMA_DELIMITER = ",";

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
