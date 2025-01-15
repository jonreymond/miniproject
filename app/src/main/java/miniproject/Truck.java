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
        super("Truck", new Position(company.getPosition()));
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
                //TODO : determine something to do ?
                break;
            case DELIVERING:
                Shipment shipment = shipments.get(0);
                Boolean isArrived = move(shipment.getDestination(), dt);
                if(isArrived){
                    //TODO : deliver goods
                    Boolean success = deliver(shipment);
                    // inform company of success
                    company.receiveShipmentDetails(shipment, success);

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
        Double distanceCovered = Config.TRUCK_SPEED * dt.toSeconds();
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
        // TODO : see if do something else (normally should not happen), like inform the company
        if(shipment.getQuantity() > currentCapacity){
            return false;
        }
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

    public Boolean addShipment(Shipment shipment) {
        Double reservedGoods = 0.0;
        for(Shipment s : shipments){
            reservedGoods += s.getQuantity();
        }
        if(this.currentCapacity - reservedGoods < shipment.getQuantity()){
            return false;
        }
        shipments.add(shipment);
        return true;
    }

    public Boolean send(){
        if(shipments.isEmpty() || this.state != State.IDLE)
            return false;
        // TODO : check here if minCapacity is respected
        state = State.DELIVERING;
        return true;
    }

    public State getState() {
        return state;
    }

    
}
