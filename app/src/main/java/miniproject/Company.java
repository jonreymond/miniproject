package miniproject;

public abstract class Company extends Entity {
    // TODO : say if rename to currentStock
    protected Double currentStock;
    protected Double maxStock;


    public Company(String name, Position address, Double maxStock) {
        super(name, address);
        this.maxStock = maxStock;
        this.currentStock = 0.0;


    }
    public abstract boolean receiveGoods(double quantity);

    public void receiveShipmentDetails(Shipment shipment, Boolean success) {
        // TODO : add a logger
        System.err.println("Shipment to " + shipment.getClient().getName() + " " + (success ? "succeeded" : "failed"));
    }

    protected Boolean addToCurrentStock(Double quantity){
        //TODO : handle if want max load, another param ?
        if(currentStock + quantity > maxStock){
            return false;
        }
        currentStock += quantity;
        return true;
    }

    protected Boolean removeFromCurrentStock(Double quantity){
        if(currentStock - quantity < 0){
            return false;
        }
        currentStock -= quantity;
        return true;
    }

    public Double getCurrentStock() {
        return currentStock;
    }
   
}
