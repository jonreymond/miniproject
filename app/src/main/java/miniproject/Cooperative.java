package miniproject;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import miniproject.config.Config;
import miniproject.utils.Time;

public class Cooperative extends Company {

    private Set<Farmer> farmers;
    private Set<Mall> malls;
    private Set<Truck> trucks;

    private Double reservedStock;

    private List<Shipment> shipments;


    public Cooperative(Set<Farmer> farmers, Set<Mall> malls, Set<Truck> trucks) {
        super("Cooperative", Config.COOPERATIVE_POSITION, Config.COOPERATIVE_MAX_STOCK);

        this.farmers = farmers;
        this.malls = malls;
        this.trucks = trucks;

        this.reservedStock = 0.0;

        this.shipments = new LinkedList<Shipment>();
    }

    public Cooperative() {
        super("Cooperative", Config.COOPERATIVE_POSITION, Config.COOPERATIVE_MAX_STOCK);

        this.farmers = new TreeSet<Farmer>();
        this.malls = new TreeSet<Mall>();
        this.trucks = new TreeSet<Truck>();

        this.reservedStock = 0.0;

        this.shipments = new LinkedList<Shipment>();

    }



    @Override
    public void update(Time dt) {
        // TODO : implement Cooperative update

    }

    public void addFarmer(Farmer farmer) {
        farmers.add(farmer);
    }
    public void removeFarmer(Farmer farmer) {
        farmers.remove(farmer);
    }

    public void addMall(Mall mall) {
        malls.add(mall);
    }
    public void removeMall(Mall mall) {
        malls.remove(mall);
    }

    public void addTruck(Truck truck) {
        trucks.add(truck);
    }
    public void removeTruck(Truck truck) {
        trucks.remove(truck);
    }


    public Boolean reserveStock(Farmer farmer, Double quantity) {
        if (reservedStock + quantity > maxStock) {
            return false;
        }

        reservedStock += quantity;
        return true;
    }

    public Boolean orderStock(Mall mall, Double quantity) {
        Shipment shipment = new Shipment(mall, quantity);
        // TODO : before accepting, check if already have a shipment for this mall
        shipments.add(shipment);
        
        return true;
    }

    public void inform(Shipment shipment, String message) {
        // TODO implement
    }

    @Override
    public boolean receiveGoods(double quantity) {
        if (reservedStock < quantity) {
            return false;
        }
        // TODO : other method to do that
        reservedStock -= quantity;
        return this.addToCurrentStock(quantity);
    }
}

