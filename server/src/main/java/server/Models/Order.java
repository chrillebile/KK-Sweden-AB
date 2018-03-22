package server.Models;

import java.sql.Timestamp;

public class Order {

    private final long id;
    private final Timestamp lastDeliveryTime;

    public Order(long id, Timestamp lastDeliveryTime) {
        this.id = id;
        this.lastDeliveryTime = lastDeliveryTime;
    }

    public long getId() {
        return id;
    }

    public Timestamp getLastDeliveryTime() {
        return lastDeliveryTime;
    }
}
