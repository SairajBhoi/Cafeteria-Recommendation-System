package server.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String LOG_DIRECTORY = "logs/";

    static {
        File logDir = new File(LOG_DIRECTORY);
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
    }

    public static void logOperation(int userId, String operation, String message) {
        String timestamp = dtf.format(LocalDateTime.now());
        String logMessage = String.format("[%s] Operation: %s, Message: %s", timestamp, operation, message);
        String userLogDirectory = LOG_DIRECTORY + userId + "/";
        String logFileName = userLogDirectory + "log_" + dateFormatter.format(LocalDate.now()) + ".log";

        File userDir = new File(userLogDirectory);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
