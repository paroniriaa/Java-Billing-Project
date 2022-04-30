package cmpe.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;

import static java.lang.System.exit;

public class ErrorHandler {
    private static final String errorTxtFile = "src/output/error.txt";

    private static final Logger errorLogger = LogManager.getLogger(ErrorHandler.class);

    public static void logErrorToFileAndExit(Logger logger, String errorString) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(errorTxtFile, false))){
            writer.write(errorString);
            logger.error(errorString);
            errorLogger.error("Writing error message the the error txt file...the application has been terminated.");
            writer.close();
            exit(-1);
        }
        catch (Exception ie) {
            throw new RuntimeException("Cannot write error messages to the error txt file,", ie);
        }
    }

    public static void logExceptionToFileAndExit(Logger logger, Exception e) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(errorTxtFile, false))){
            writer.write(e.toString());
            logger.error(e.toString());
            errorLogger.error("Writing exception message ro the error txt file...the application has been terminated.");
            writer.close();
            exit(-1);
        }
        catch (Exception ie) {
            throw new RuntimeException("Cannot write exception messages to the error txt file.", ie);
        }
    }
}
