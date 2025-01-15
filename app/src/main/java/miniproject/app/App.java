/*
 * This source file was generated by the Gradle 'init' task
 */
package miniproject.app;

import java.util.List;

import miniproject.Environment;
import miniproject.Logger;

import miniproject.config.ConfigReader;
import miniproject.utils.Time;



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

        List<List<String>> farmersParams = configReader.getFarmersParams();
        List<List<String>> mallsParams = configReader.getMallsParams();
        farmersParams.removeFirst();
        mallsParams.removeFirst();

        Environment env = new Environment(farmersParams, mallsParams);

        Time time = Time.ZERO;

        int maxInter = 10;

        for(int i = 0; i < maxInter; i++){
            System.out.println(time);
            env.update(time);
            time = time.plus(Time.fromSeconds(10));
        }
    }
}
