package miniproject.config;

import java.util.List;

import miniproject.Farmer;
import miniproject.Mall;
import miniproject.Position;
import miniproject.utils.Pair;
import miniproject.utils.Time;

public final class Config {

    
    // Files
    public static final String FARMER_DB_PATH = "app/src/main/resources/farmer_db.csv";
    public static final String MALL_DB_PATH = "app/src/main/resources/mall_db.csv";

    public static final String LOG_PATH = "app/src/main/java/miniproject/output/log.txt"; 
    public static final boolean LOG_VERBOSE = true;
 
   
    
    // Truck
    public static final double TRUCK_SPEED = 50;  // in km/h
    public static final double MIN_TRUCK_CAPACITY = 100;
    public static final double MAX_TRUCK_CAPACITY = 200;
    
    // Cooperative
    public static final int NUM_COOPERATIVE_TRUCKS = 10;
    
    public static final Position COOPERATIVE_POSITION = new Position(46.183847, 6.134639);
    public static final double COOPERATIVE_MAX_STOCK = 1000;
    public static final double COOPERATIVE_MAX_ORDER = MAX_TRUCK_CAPACITY;
    public static final double COOPERATIVE_MIN_ORDER = MIN_TRUCK_CAPACITY;
    


    public static final List<Pair<String, Class<?>>> mallAttributes = List.of(
            new Pair<>("mallId", Integer.class),
            new Pair<>("longitude", Double.class),
            new Pair<>("latitude", Double.class),
            new Pair<>("fullConsumption", Double.class),
            new Pair<>("maxStock", Double.class)
    );

    // public static final List<Pair<String, Class<?>>> truckAttributes = List.of(
    //         new Pair<>("truckId", Integer.class),
    //         new Pair<>("maxCapacity", Double.class),
    //         new Pair<>("speed", Double.class)
    // );

    // public static final List<Pair<String, Class<?>>> farmerAttributes = List.of(
    //         new Pair<>("farmerId", Integer.class),
    //         new Pair<>("longitude", Double.class),
    //         new Pair<>("latitude", Double.class),
    //         new Pair<>("freqDelivering", Integer.class),
    //         new Pair<>("quantityDelivering", Double.class)
    // );

    // public static final List<Pair<String, Class<?>>> cooperativeAttributes = List.of(
    //         new Pair<>("cooperativeId", Integer.class),
    //         new Pair<>("longitude", Double.class),
    //         new Pair<>("latitude", Double.class),
    //         new Pair<>("maxStock", Double.class)
    // );

    public static Farmer.Builder getFarmerFromParams(List<String> params) {
        // String name, Position address, Double maxStock, Time shipmentInterval, Double productionRate
        String name = params.get(0);
        Position address = new Position(Double.parseDouble(params.get(1)), Double.parseDouble(params.get(2)));
        Double maxStock = Double.parseDouble(params.get(3));
        // TODO : change to correct time
        Time shipmentInterval = Time.fromSeconds(Double.parseDouble(params.get(4)));
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
