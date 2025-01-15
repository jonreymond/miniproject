package miniproject;

public final class Shipment {
    private final Company client;
    private final Double quantity;

    public Shipment(Company client, Double quantity) {
        this.client = client;
        this.quantity = quantity;
    }

    public Position getDestination() {
        return client.getPosition();
    }

    public Company getClient() {
        return client;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Shipment changeQuantity(Double quantity){
        return new Shipment(this.client, quantity);
    }
    
}
