package miniproject;

import miniproject.config.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public final class Logger {


    public Logger() {

        // check if file exists
        File logFile = new File(Config.LOG_PATH);
    
        try {
            Files.deleteIfExists(logFile.toPath());
            logFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        if (Config.LOG_VERBOSE) {
            System.out.println(message);
        }
        try {
                Files.write(Paths.get(Config.LOG_PATH), (message + "\r\n").getBytes() , StandardOpenOption.APPEND);
                    }catch (IOException e) {
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    
}
