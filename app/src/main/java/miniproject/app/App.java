/*
 * This source file was generated by the Gradle 'init' task
 */
package miniproject.app;

import miniproject.Logger;
import miniproject.config.Config;
import miniproject.config.ConfigReader;



public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    // remark : relative path + will overwrite the file
    public static String output_path_filename = "app/src/main/resources/log.txt";

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());


        Logger logger = new Logger();

        ConfigReader configReader = new ConfigReader();

        




    }
}
