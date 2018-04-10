package server.Models;

import java.sql.Timestamp;

public class Order {

    private final long id;
    private final Timestamp lastDeliveryTime;
    private final int customerId;

    public Order(long id, Timestamp lastDeliveryTime, int customerId) {
        this.id = id;
        this.lastDeliveryTime = lastDeliveryTime;
        this.customerId = customerId;
    }

    public long getId() {
        return id;
    }

    public Timestamp getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public int getCustomerId() {
        return customerId;
    }
}
