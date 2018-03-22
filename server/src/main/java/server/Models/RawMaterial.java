package server.Models;

import java.sql.Timestamp;

public class RawMaterial {

    private final long id;
    private final String name;
    private final int amount;
    private final String unit;
    private final Timestamp lastDeliveryTime;
    private final int lastDeliveryAmount;

    public RawMaterial(long id, String name, int amount, String unit, Timestamp lastDeliveryTime, int lastDeliveryAmount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.lastDeliveryTime = lastDeliveryTime;
        this.lastDeliveryAmount = lastDeliveryAmount;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public Timestamp getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public int getLastDeliveryAmount() {
        return lastDeliveryAmount;
    }
}
