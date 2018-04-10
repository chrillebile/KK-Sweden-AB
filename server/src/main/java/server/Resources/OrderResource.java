package server.Resources;

import server.Models.Order;

public class OrderResource {

    private final long id;
    private final long lastDeliveryTime;
    private final int customerId;

    public OrderResource(Order order) {
        this.id = order.getId();
        this.lastDeliveryTime = order.getLastDeliveryTime().getTime();
        this.customerId = order.getCustomerId();
    }

    public long getId() {
        return id;
    }

    public long getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public int getCustomerId() {
        return customerId;
    }
}
