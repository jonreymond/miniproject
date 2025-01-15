package miniproject;

import miniproject.utils.Time;
import miniproject.utils.Utils;

public class Farmer extends Company {

    private Cooperative cooperative;
    private final Time shipmentInterval;
    private Time time;
    private Truck truck;
    private final Double productionRate;


    public Farmer(String name, Position address, Double maxStock, Time shipmentInterval, Double productionRate, Cooperative cooperative) {
        super(name, address, maxStock);
        Utils.requireNonNull("Cannot instantiate Farmer with null Truck",truck);
        Utils.requireNonNull("Cannot instantiate Farmer with null Cooperative",cooperative);
        this.cooperative = cooperative;
        this.time = Time.ZERO;

        this.shipmentInterval = shipmentInterval;

        this.productionRate = productionRate;
    }

    public final static class Builder {
        private final String name;
        private final Position address;
        private final Double maxStock;
        private final Time shipmentInterval;
        private final Double productionRate;

        private Cooperative cooperative;


        public Builder(String name, Position address, Double maxStock, Time shipmentInterval, Double productionRate){
            this.name = name;
            this.address = address;
            this.maxStock = maxStock;
            this.shipmentInterval = shipmentInterval;
            this.productionRate = productionRate;
        }

        public Farmer.Builder setCooperative(Cooperative cooperative) {
            this.cooperative = cooperative;
            return this;
        }


        public Farmer build(){
            return new Farmer(name, address, maxStock, shipmentInterval, productionRate, this.cooperative);
        }
    }

    @Override
    public void update(Time dt) {
        this.time = this.time.plus(dt);
        // TODO: check what to do with
        boolean isFull = this.addToCurrentStock(this.productionRate);

        if(this.time.compareTo(this.shipmentInterval) >= 0){

            this.time = this.time.minus(this.shipmentInterval);

            
            if(this.truck.getState() == Truck.State.IDLE){
               
                double loadedGoods =  this.truck.loadMax(this.getCurrentStock());
                boolean successLoad = this.removeFromCurrentStock(loadedGoods);

                boolean successReserve = this.cooperative.reserveStock(this, truck.getCurrentCapacity());
                if(successReserve){
                    Shipment shipment = new Shipment(this.cooperative, truck.getCurrentCapacity());
                    boolean a = this.truck.addShipment(shipment);
                    boolean b = this.truck.send();
                    
                }
            }
        }
    }

    public boolean addTruck(Truck truck) {
        Utils.requireNonNull(truck);
        if(this.truck == null){
            this.truck = truck;
            return true;
        }
        return false;
    }


    public Cooperative getCooperative() {
        return cooperative;
    }

    @Override
    public boolean receiveGoods(double quantity) {
        return this.addToCurrentStock(quantity);
    }


    
}
