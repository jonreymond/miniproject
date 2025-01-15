package miniproject;

import java.util.ArrayList;
import java.util.List;

import miniproject.utils.Time;

public class Mall extends Company {
    public enum State {
        IDLE,
        ORDERING
    }

    private final Double consumptionRate;
    private final Double minThresholdStock;
    private Cooperative cooperative;
    private List<Shipment> shipments;
    private State state;


    public Mall(String name, Position address, Double maxStock, Double consumptionRate, Double minThresholdStock, Cooperative cooperative) {
        super(name, address, maxStock);

        this.consumptionRate = consumptionRate;
        this.minThresholdStock = minThresholdStock;
        this.state = State.IDLE;
        this.cooperative = cooperative;
        this.shipments = new ArrayList<Shipment>();
    }

    public final static class Builder {
        private final String name;
        private final Position address;
        private final Double maxStock;
        private final Double consumptionRate;
        private final Double minThresholdStock;

        private Cooperative cooperative;


        public Builder(String name, Position address, Double maxStock, Double consumptionRate, Double minThresholdStock){
            this.name = name;
            this.address = address;
            this.maxStock = maxStock;
            this.consumptionRate = consumptionRate;
            this.minThresholdStock = minThresholdStock;
        }

        public Mall.Builder setCooperative(Cooperative cooperative) {
            this.cooperative = cooperative;
            return this;
        }
        
        public Mall build(){
            return new Mall(name, address, maxStock, consumptionRate, minThresholdStock, cooperative);
        }

    }

    @Override
    public void update(Time dt) {

        this.removeFromCurrentStock(this.consumptionRate);

        if(state == State.IDLE && this.getCurrentStock() < this.minThresholdStock){
            Double desiredOrderQuantity = this.maxStock - this.getCurrentStock();
            if(desiredOrderQuantity > cooperative.getMinOrder()){
                state = State.ORDERING;

                while(desiredOrderQuantity > cooperative.getMinOrder()) {
                    Double orderedQuantity = Math.min(desiredOrderQuantity, cooperative.getMaxOrder());
                    shipments.add(new Shipment(cooperative, orderedQuantity));
                    this.cooperative.order(this, orderedQuantity);
                    desiredOrderQuantity -= orderedQuantity;
                }
            }  
        }
    }

    @Override
    public boolean receiveGoods(double quantity) {
        if(state == State.ORDERING){
            Shipment shipment = getShipmentFromQuantity(currentStock);
            if(shipment.getQuantity() == quantity){
                boolean success = this.addToCurrentStock(quantity);
                // cooperative.inform(this, "Correct quantity received: " + quantity);
                shipment = null;
                state = State.IDLE;
                return success;
            }
            else if(shipment.getQuantity() < quantity){
                shipment = shipment.changeQuantity(shipment.getQuantity() - quantity);
                return this.addToCurrentStock(quantity);
            }
            else {
                boolean success = this.addToCurrentStock(shipment.getQuantity());
                // cooperative.inform(this,"Incorrect quantity received: " + quantity + ", expected: " + shipment.getQuantity());
                shipment = null;
                state = State.IDLE;
                return success;
            }     
        }
        else {
            // cooperative.inform(this,": Incorrect shipment arrival: No shipment asked");
            return false;
        }
    }

    private Shipment getShipmentFromQuantity(Double quantity){
        List<Shipment> ships= new ArrayList<>(this.shipments);
        Shipment trueShipment = null;
        for(Shipment shipment : ships){
            if(shipment.getQuantity() == quantity){
                trueShipment = shipment;
                break;
            }
        }
        if(trueShipment == null && !ships.isEmpty()){
            trueShipment = ships.get(0);
        }
        return trueShipment;
      
    }

    @Override
    public void inform(Entity entity, String message) {
        cooperative.inform(this, message);
    }

}
