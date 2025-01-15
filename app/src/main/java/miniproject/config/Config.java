package miniproject.config;

import java.util.List;

import miniproject.Farmer;
import miniproject.Mall;
import miniproject.Position;
import miniproject.utils.Time;

public final class Config {

    
    // Files
    public static final String FARMER_DB_PATH = "app/src/main/resources/farmer_db.csv";
    public static final String MALL_DB_PATH = "app/src/main/resources/mall_db.csv";

    public static final String LOG_PATH = "app/src/main/java/miniproject/output/log.txt"; 
    public static final boolean LOG_VERBOSE = true;
 
    public static final int UPDATE_STEP_IN_MINUTES = 60;
    public static final int MAX_ITER = 1000;
   
    
    // Truck
    public static final double TRUCK_SPEED = 50;  // in km/h
    public static final double MIN_TRUCK_CAPACITY = 100;
    public static final double MAX_TRUCK_CAPACITY = 200;
    
    // Cooperative
    public static final int NUM_COOPERATIVE_TRUCKS = 10;
    
    public static final Position COOPERATIVE_POSITION = new Position(46.183847, 6.134639);
    public static final double COOPERATIVE_MAX_STOCK = 20000;
    public static final double COOPERATIVE_MAX_ORDER = MAX_TRUCK_CAPACITY;
    public static final double COOPERATIVE_MIN_ORDER = MIN_TRUCK_CAPACITY;
    


    public static Farmer.Builder getFarmerFromParams(List<String> params) {
        // String name, Position address, Double maxStock, Time shipmentInterval, Double productionRate
        String name = params.get(0);
        Position address = new Position(Double.parseDouble(params.get(1)), Double.parseDouble(params.get(2)));
        Double maxStock = Double.parseDouble(params.get(3));

        Time shipmentInterval = Time.fromDays(Double.parseDouble(params.get(4)));
        Double productionRate = Double.parseDouble(params.get(5));

        return new Farmer.Builder(name, address, maxStock, shipmentInterval, productionRate);
    }

    
    public static Mall.Builder getMallFromParams(List<String> params){
        // String name, Position address, Double maxStock, Double consumptionRate, Double minThresholdStock
        String name = params.get(0);
        Position address = new Position(Double.parseDouble(params.get(1)), Double.parseDouble(params.get(2)));
        Double maxStock = Double.parseDouble(params.get(3));
        Double consumptionRate = Double.parseDouble(params.get(4));
        Double minThresholdStock = Double.parseDouble(params.get(5));

        return new Mall.Builder(name, address, maxStock, consumptionRate, minThresholdStock);
    }


    
}
