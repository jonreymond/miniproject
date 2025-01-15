package miniproject;

import miniproject.utils.Time;

public class Mall extends Company {
    public enum State {
        IDLE,
        ORDERING
    }

    private final Double consumptionRate;
    private final Double minThresholdStock;
    private Cooperative cooperative;
    private Shipment shipment;
    private State state;


    public Mall(String name, Position address, Double maxStock, Double consumptionRate, Double minThresholdStock, Cooperative cooperative) {
        super(name, address, maxStock);

        this.consumptionRate = consumptionRate;
        this.minThresholdStock = minThresholdStock;
        this.state = State.IDLE;
        this.cooperative = cooperative;
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

        // TODO: check what to do with
        boolean a = this.removeFromCurrentStock(this.consumptionRate);

        if(state == State.IDLE || this.getCurrentStock() < this.minThresholdStock){
            state = State.ORDERING;
            // TODO : check quantity ordered policy: depends on waiting list
            shipment = new Shipment(cooperative, this.maxStock - this.getCurrentStock());
            boolean b = this.cooperative.orderStock(this, this.maxStock - this.getCurrentStock());


            
        }
    }

    @Override
    public boolean receiveGoods(double quantity) {
        if(state == State.ORDERING){
            if(shipment.getQuantity() == quantity){
                boolean success = this.addToCurrentStock(quantity);
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
                cooperative.inform(shipment, "Incorrect quantity received: " + quantity + ", expected: " + shipment.getQuantity());
                shipment = null;
                state = State.IDLE;
                return success;
            }     
        }
        else {
            cooperative.inform(null, "No shipment asked");
            return false;
        }
    }



}
