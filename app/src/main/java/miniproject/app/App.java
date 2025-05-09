package miniproject.app;

import java.util.List;

import miniproject.Environment;
import miniproject.config.Config;
import miniproject.config.ConfigReader;
import miniproject.utils.Time;



public class App {
    public void launch() {
        System.out.println("Launch application");
        ConfigReader configReader = new ConfigReader();

        List<List<String>> farmersParams = configReader.getFarmersParams();
        List<List<String>> mallsParams = configReader.getMallsParams();
        farmersParams.removeFirst();
        mallsParams.removeFirst();

        Environment env = new Environment(farmersParams, mallsParams);

        Time time = Time.ZERO;

        for(int i = 0; i < Config.MAX_ITER; i++){
            env.update(time);
            time = time.plus(Time.fromMinutes(Config.UPDATE_STEP_IN_MINUTES));
        }
        System.out.println("End application");

    }



    public static void main(String[] args) {
        new App().launch();   
    }
}
