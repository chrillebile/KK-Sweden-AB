package server.Models;

import java.time.LocalDate;

public class RawMaterial {

    private final long id;
    private final String name;
    private final int amount;
    private final String unit;
    private final LocalDate lastDeliveryTime;
    private final int lastDeliveryAmount;

    public RawMaterial(long id, String name, int amount, String unit, LocalDate lastDeliveryTime, int lastDeliveryAmount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.lastDeliveryTime = lastDeliveryTime;
        this.lastDeliveryAmount = lastDeliveryAmount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getLastDeliveryTime() {
        return lastDeliveryTime;
    }

    public int getLastDeliveryAmount() {
        return lastDeliveryAmount;
    }
}
