package server.Resources;

import server.Models.Order;
import java.sql.Timestamp;

public class OrderResource {

    private final long id;
    private final Timestamp lastDeliveryTime;
    private final int customerId;

    public OrderResource(Order order) {
        this.id = order.getId();
        this.lastDeliveryTime = order.getLastDeliveryTime();
        this.customerId = order.getCustomerId();
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
