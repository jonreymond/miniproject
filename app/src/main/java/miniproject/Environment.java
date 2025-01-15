package miniproject;

import miniproject.config.Config;
import miniproject.utils.Time;

import java.util.ArrayList;
import java.util.List;


public final class Environment {

    private Cooperative cooperative;
    private List<Farmer> farmers;
    private List<Truck> trucks;
    private List<Mall> malls;


    public Environment(List<List<String>> farmersParams, List<List<String>> mallsParams){
        
        this.cooperative =  new Cooperative();
        this.farmers = new ArrayList<>();
        this.trucks = new ArrayList<>();
        this.malls = new ArrayList<>();

        Truck.Builder truckBuilder = new Truck.Builder(Config.MIN_TRUCK_CAPACITY, Config.MAX_TRUCK_CAPACITY);
        
        for (List<String>  farmerParams: farmersParams) {
            Farmer.Builder farmerBuilder = Config.getFarmerFromParams(farmerParams);
            Farmer farmer = farmerBuilder.setCooperative(cooperative).build();
            
            Truck truck = truckBuilder.setId(Uid.createUid()).setCompany(farmer).build();
            farmer.addTruck(truck);
            this.farmers.add(farmer);
            this.trucks.add(truck);
        }

        for (List<String>  mallParams: mallsParams) {
            Mall.Builder mallBuilder = Config.getMallFromParams(mallParams);
            Mall mall = mallBuilder.setCooperative(cooperative).build();
            this.malls.add(mall);
        }
        for(int i = 0; i < Config.NUM_COOPERATIVE_TRUCKS; i++){
            Truck truck = truckBuilder.setId(Uid.createUid()).setCompany(cooperative).build();
            cooperative.addTruck(truck);
            this.trucks.add(truck);
        }
    }

    public void update(Time dt){
        cooperative.update(dt);

        for (Farmer farmer : farmers)
            farmer.update(dt);

        for (Truck truck : trucks)
            truck.update(dt);

        for (Mall mall : malls)
            mall.update(dt);

    }    
}
