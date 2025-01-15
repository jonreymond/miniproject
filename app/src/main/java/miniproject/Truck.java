package miniproject;

import java.util.LinkedList;
import miniproject.config.Config;


import miniproject.utils.Time;
import miniproject.utils.Utils;

public class Truck extends Entity {

    public enum State {
        IDLE,
        DELIVERING,
        RETURNING
    }

    private State state;
    private final Uid id;
    private final Double minCapacity;
    private final Double maxCapacity;
    private final Company company;
    private Double currentCapacity;

    private LinkedList<Shipment> shipments;




    public Truck(Uid id, Double minCapacity, Double maxCapacity, Company company) {
        super(id.toString(), new Position(company.getPosition()));
        this.id = id;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
        this.company = company;
        this.currentCapacity = 0.0;
        this.state = State.IDLE;
        this.shipments = new LinkedList<Shipment>();

    }

    public final static class Builder {
        private Uid id;
        private final Double minCapacity;
        private final Double maxCapacity;
        private Company company;

        public Builder(Double minCapacity, Double maxCapacity){
            this.minCapacity = minCapacity;
            this.maxCapacity = maxCapacity;
        }

        public Truck.Builder setId(Uid id) {
            this.id = id;
            return this;
        }

        public Truck.Builder setCompany(Company company){
            this.company = company;
            return this;
        }

        public Truck build(){
            return new Truck(id, minCapacity, maxCapacity, company);
        }

    }

    @Override
    public void update(Time dt) {
        switch(this.state){
            case IDLE:
                break;
            case DELIVERING:
                Shipment shipment = shipments.get(0);
                Boolean isArrived = move(shipment.getDestination(), dt);
                if(isArrived){
                    Boolean success = deliver(shipment);
                    // inform company of success
                    String isSuccessfull = success ? "success" : "failure";
                    company.inform(this, shipment.getClient().getName() + " shipment is a " + isSuccessfull 
                                    + ", quantity " + (Math.round(shipment.getQuantity() * 100.0) / 100.0));

                    shipments.remove(0);

                    if(shipments.isEmpty())
                        state = State.RETURNING;
                }
                
                break;
            case RETURNING:
                isArrived = move(company.getPosition(), dt);
                if(isArrived){
                    state = State.IDLE;
                }
                break;
        }   
    }

    private Boolean move(Position destination, Time dt){
        if(destination == null)
            return true;
        boolean isArrived = false;

        //TODO : see which factor to use for time
        Double distanceCovered = Config.TRUCK_SPEED * dt.toHours();
        this.position = Position.moveTo(this.position, destination, distanceCovered);

        if(position.equals(destination)){
            isArrived = true;
        }
        return isArrived;
    }

    public Uid getId() {
        return id;
    }

    public Double getCurrentCapacity() {
        return currentCapacity;
    }

    private Boolean deliver(Shipment shipment) {
        Boolean success = shipment.getClient().receiveGoods(shipment.getQuantity());
        
        if(success){
            currentCapacity -= shipment.getQuantity();
            return true;
        }
        return false;
    }


    // TODO: add condition: should be IDLE
    public Boolean load(Double quantity) {
        if (currentCapacity + quantity > maxCapacity)
            return false;
        else {
            currentCapacity += quantity;
            return true;
        }
    }

    // TODO: add condition: should be IDLE
    public Double loadMax(Double quantity){
        Double availableCapacity = maxCapacity - currentCapacity;
        Double loadedGoods = Math.min(quantity, availableCapacity);
        Boolean success = load(loadedGoods);
        if(success)
            return loadedGoods;
        else
            return 0.0;
    }

    /**
     * Adds a shipment to the truck's delivery route.
     *
     * @param shipment The shipment to be added to the truck's delivery route.
     * @return true if the shipment is successfully added, false otherwise.
     *
     * This method checks if the truck has enough capacity to accommodate the shipment's quantity.
     * If the truck has enough capacity, the shipment is added to the truck's list of shipments,
     * and the shipment's quantity is loaded onto the truck.
     * If the truck does not have enough capacity, the method returns false without adding the shipment.
     *
     * @see Shipment
     * @see Truck#load(Double)
     */
    public Boolean addShipment(Shipment shipment) {
        Utils.requireNonNull(shipment);
        shipments.add(shipment);
        
        return this.load(shipment.getQuantity());
    }

    /**
     * Initiates a delivery route for the truck.
     *
     * @return true if the delivery route is successfully initiated, false otherwise.
     *
     * This method checks if the truck is ready to start a delivery route by calling the {@link #readyToGo()} method.
     * If the truck is ready, the method sets the truck's state to {@link State#DELIVERING} and returns true.
     * If the truck is not ready, the method returns false without changing the truck's state.
     *
     * @see #readyToGo()
     */
    public Boolean send(){
        if(!this.readyToGo()){

            return false;
        }    
        state = State.DELIVERING;
        return true;
    }

    /**
     * Retrieves the current state of the truck.
     *
     * @return The current state of the truck.
     *
     * The truck can be in one of the following states:
     * - IDLE: The truck is not currently delivering or returning to the company.
     * - DELIVERING: The truck is currently on a delivery route.
     * - RETURNING: The truck is returning to the company after completing a delivery route.
     */
    public State getState() {
        return state;
    }

    /**
     * Checks if the truck is ready to start a delivery route.
     *
     * @return true if the truck is ready to go, false otherwise.
     *
     * The truck is considered ready to go if:
     * - There are no shipments to deliver.
     * - The truck's current state is IDLE.
     * - The truck's current capacity is greater than or equal to its minimum capacity.
     *
     * This method does not perform any actions; it only checks the conditions.
     */
    public boolean readyToGo(){
        return (!shipments.isEmpty() && (this.state == State.IDLE) && this.currentCapacity >= this.minCapacity);
    }

    
}
