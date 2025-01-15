package miniproject;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import miniproject.config.Config;
import miniproject.utils.Time;
import miniproject.utils.Utils;

/**
 * The class Cooperative represents a cooperative that can receive goods from farmers and deliver them to malls.
 */
public class Cooperative extends Company {

    private Set<Farmer> farmers;
    private Set<Mall> malls;
    private Set<Truck> trucks;
    private final Double maxOrder;
    private final Double minOrder;

    private Double reservedStock;

    private List<Shipment> notProcessedShipments;
    private List<Shipment> processedShipments;



    public Cooperative(Set<Farmer> farmers, Set<Mall> malls, Set<Truck> trucks) {
        super("Cooperative", Config.COOPERATIVE_POSITION, Config.COOPERATIVE_MAX_STOCK);
        this.maxOrder = Config.COOPERATIVE_MAX_ORDER;
        this.minOrder = Config.COOPERATIVE_MAX_ORDER;
        this.farmers = farmers;
        this.malls = malls;
        this.trucks = trucks;

        this.reservedStock = 0.0;

        this.notProcessedShipments = new LinkedList<Shipment>();
        this.processedShipments = new LinkedList<Shipment>();
    }

    public Cooperative() {
        super("Cooperative", Config.COOPERATIVE_POSITION, Config.COOPERATIVE_MAX_STOCK);

        this.maxOrder = Config.COOPERATIVE_MAX_ORDER;
        this.minOrder = Config.COOPERATIVE_MIN_ORDER;

        this.farmers = new HashSet<Farmer>();
        this.malls = new HashSet<Mall>();
        this.trucks = new HashSet<Truck>();

        this.reservedStock = 0.0;

        this.notProcessedShipments = new LinkedList<Shipment>();
        this.processedShipments = new LinkedList<Shipment>();
    }



    @Override
    public void update(Time dt) {
        processShipments();
        for (Truck truck : trucks) {
            if(truck.readyToGo()){
                truck.send();
            }
        }
    }

    /**
     * Adds a farmer to the set of farmers associated with this cooperative.
     *
     * @param farmer The farmer to be added.
     *
     * @throws IllegalArgumentException If the provided farmer is null.
     */
    public void addFarmer(Farmer farmer) {
        Utils.requireNonNull(farmer);
        farmers.add(farmer);
    }
    /**
     * Removes a farmer from the set of farmers associated with this cooperative if it is present.
     *
     * @param farmer The farmer to be removed.
     *
     * @throws IllegalArgumentException If the provided farmer is null.
     */
    public void removeFarmer(Farmer farmer) {
        Utils.requireNonNull(farmer);
        farmers.remove(farmer);
    }

    /**
     * Adds a mall to the set of malls associated with this cooperative.
     *
     * @param mall The mall to be added.
     *
     * @throws IllegalArgumentException If the provided mall is null.
     */
    public void addMall(Mall mall) {
        Utils.requireNonNull(mall);
        malls.add(mall);
    }
    /**
     * Removes a mall from the set of malls associated with this cooperative if it is present.
     *
     * @param mall The mall to be removed.
     *
     * @throws IllegalArgumentException If the provided mall is null.
     */
    public void removeMall(Mall mall) {
        Utils.requireNonNull(mall);
        malls.remove(mall);
    }

    /**
     * Adds a truck to the set of trucks associated with this cooperative.
     *
     * @param truck The truck to be added.
     *
     * @throws IllegalArgumentException If the provided truck is null.
     */
    public void addTruck(Truck truck) {
        trucks.add(truck);
    }
    /**
     * Removes a truck from the set of trucks associated with this cooperative if it is present.
     *
     * @param truck The truck to be removed.
     *
     * @throws IllegalArgumentException If the provided truck is null.
     */
    public void removeTruck(Truck truck) {
        trucks.remove(truck);
    }


    /**
     * Reserves a specified quantity of stock from the cooperative for a given farmer. 
     * If the reservation is successful, the reserved stock is incremented by the quantity.
     * The cooperative then wait to receive the goods from the given farmer.
     *
     * @param farmer The farmer for whom the stock is being reserved.
     * @param quantity The quantity of stock to be reserved.
     *
     * @return {@code true} if the stock reservation is successful: the reserved stock does not exceed the maximum allowed stock
     *         {@code false} otherwise.
     */
    public Boolean reserveStock(Farmer farmer, Double quantity) {
        if (reservedStock + quantity > maxStock) {
            return false;
        }

        reservedStock += quantity;
        return true;
    }

    /**
     * Orders a specified quantity of stock from the cooperative to a given mall.
     * If the order is successful, a new shipment is created and added to the list of shipments.
     *
     * @param mall The mall for which the stock is being ordered.
     * @param quantity The quantity of stock to be ordered. Must not exceed the maximum order limit.
     *
     * @return {@code true} if the stock order is successful: a new shipment is created and added to the list of shipments.
     *         {@code false} if the quantity exceeds the maximum order limit or is below the minimum order limit.
     *
     * @throws IllegalArgumentException If the provided mall is null.
     */
    public Boolean order(Mall mall, Double quantity) {
        Utils.requireNonNull(mall);

        if(quantity > maxOrder || quantity < minOrder) {
            return false;
        }
        Shipment shipment = new Shipment(mall, quantity);
        notProcessedShipments.add(shipment);
        System.out.println("Order accepted");
        return true;
    }

    /**
     * Returns the maximum order limit for the cooperative.
     *
     * @return The maximum order limit for the cooperative.
     */
    public Double getMaxOrder() {
        return this.maxOrder;
    }

    /**
     * Returns the minimum order limit for the cooperative.
     *
     * @return The minimum order limit for the cooperative. This value is used to ensure that the cooperative
     *         does not place orders below a certain threshold.
     */
    public Double getMinOrder() {
        return this.minOrder;
    }



    /**
     * Processes the shipments in the notProcessedShipments list.
     * This method iterates over the list, checks if a truck is available to deliver the shipment,
     * and if the shipment quantity does not exceed the current stock.
     * If all conditions are met, the shipment is assigned to a truck, the stock is updated,
     * and the shipment is moved from the notProcessedShipments list to the processedShipments list.
     */
    private void processShipments() {
        System.out.println("process shipments");
        if(notProcessedShipments.isEmpty())
            return;

        boolean canDeliver = true;
        int numberOfShipments = notProcessedShipments.size();
        int i = 0;
        while (canDeliver && i < numberOfShipments) {
            Shipment shipment = notProcessedShipments.get(0);
            if (shipment.getQuantity() <= this.getCurrentStock()) {

                Truck truck = getAvailableTruck(shipment.getQuantity());
                if(truck == null){
                    canDeliver = false;
                }
                else{
                    truck.addShipment(shipment);
                    this.removeFromCurrentStock(shipment.getQuantity());
                    this.notProcessedShipments.remove(shipment);
                    this.processedShipments.add(shipment);
                }

            } else {
                canDeliver = false;
            }
            i++;
        }
        System.out.println("Shipments processed");
    }

    private Truck getAvailableTruck(Double quantity){
        Truck availableTruck = null;
        for(Truck truck : trucks){
                if(truck.getState() == Truck.State.IDLE && truck.getCurrentCapacity() <= quantity){
                    availableTruck = truck;
                    break;
                }
            }
        return availableTruck;
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

