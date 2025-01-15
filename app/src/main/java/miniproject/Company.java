package miniproject;

/**
 * The class Company represents a company that can receive goods and handle a stock.
 */
public abstract class Company extends Entity {
    // TODO : say if rename to currentStock
    protected Double currentStock;
    protected final Double maxStock;


    public Company(String name, Position address, Double maxStock) {
        super(name, address);
        this.maxStock = maxStock;
        this.currentStock = 0.0;


    }
    /** TODO
     * This method is responsible for receiving goods into the company's stock.
     *
     * @param quantity The quantity of goods to be received.
     * @return {@code true} if the goods were successfully received and added to the stock,
     *         {@code false} otherwise.
     */
    public abstract boolean receiveGoods(double quantity);



    /**
     * This method is responsible for sending a notification to a specified entity with a given message.
     *
     * @param entity The entity to which the notification will be sent.
     * @param message The content of the notification message.
     */
    public abstract void inform(Entity entity, String message);

    /**
     * This method is responsible for adding a specified quantity of goods to the company's stock.
     *
     * @param quantity The quantity of goods to be added to the stock.
     * @return {@code true} if the goods were successfully added to the stock,
     *         {@code false} if the addition would exceed the maximum stock capacity.
     */
    protected Boolean addToCurrentStock(Double quantity){
        if((currentStock + quantity) > maxStock){
            return false;
        }
        currentStock += quantity;
        return true;
    }

    /**
     * This method is responsible for removing a specified quantity of goods from the company's stock.
     *
     * @param quantity The quantity of goods to be removed from the stock.
     * @return {@code true} if the goods were successfully removed from the stock,
     *         {@code false} if the removal would result in a negative stock value.
     */
    protected Boolean removeFromCurrentStock(Double quantity){
        if(currentStock - quantity < 0){
            return false;
        }
        currentStock -= quantity;
        return true;
    }

    /**
     * This method retrieves the current stock quantity of the company.
     *
     * @return The current stock quantity of the company.
     */
    public Double getCurrentStock() {
        return currentStock;
    }

    
   
}
