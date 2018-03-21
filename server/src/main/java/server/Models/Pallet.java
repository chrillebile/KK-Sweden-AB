package server.Models;

import java.sql.Timestamp;
import java.util.Date;

public class Pallet {

    private final long id;
    private final int amount;
    private final Date productionDate;
    private final boolean isBlocked;
    private final String location;
    private final Timestamp deliveryTime;

    public Pallet(long id, int amount, Date productionDate, boolean isBlocked, String location, Timestamp deliveryTime) {
        this.id = id;
        this.amount = amount;
        this.productionDate = productionDate;
        this.isBlocked = isBlocked;
        this.location = location;
        this.deliveryTime = deliveryTime;
    }

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public String getLocation() {
        return location;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }
}
