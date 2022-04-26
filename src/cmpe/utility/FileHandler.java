package cmpe.utility;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileHandler {

    public static void deletePreviousErrorLogFile() {
        File previousErrorLogFile = new File("src/output/error_log.txt");
        if (previousErrorLogFile.delete()) {
            System.out.println("Deleted the previous error log file: " + previousErrorLogFile.getName() + ", continue to the shopping process...");
        } else {
            System.out.println("No previous error log file found to be deleted, continue to the shopping process...");
        }
    }

    public static void deletePreviousBillingFile() {
        File previousBillingFile = new File("src/output/billing_list.csv");
        if (previousBillingFile.delete()) {
            System.out.println("Deleted the previous billing file: " + previousBillingFile.getName() + ", continue to the shopping process...");
        } else {
            System.out.println("No previous billing file found to be deleted, continue to the shopping process...");
        }
    }

    public static void logErrorToFile(Exception e) {
        try {
            FileWriter New_File = new FileWriter("src/output/error_log.txt", false);
            BufferedWriter Buff_File = new BufferedWriter(New_File);
            PrintWriter Print_File = new PrintWriter(Buff_File, true);
            e.printStackTrace();
            e.printStackTrace(Print_File);
        }
        catch (Exception ie) {
            throw new RuntimeException("Cannot write the exception to the log file", ie);
        }
    }
}
