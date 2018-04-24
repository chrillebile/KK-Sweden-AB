package server.Resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import server.Models.RawMaterial;

import java.time.LocalDate;

public class RawMaterialResource {

    private final long id;
    private final String name;
    private final int amount;
    private final String unit;
    private final LocalDate lastDeliveryTime;
    private final int lastDeliveryAmount;

    public RawMaterialResource(RawMaterial rawMaterial) {
        this.id = rawMaterial.getId();
        this.name = rawMaterial.getName();
        this.amount = rawMaterial.getAmount();
        this.unit = rawMaterial.getUnit();
        this.lastDeliveryTime = rawMaterial.getLastDeliveryTime();
        this.lastDeliveryAmount = rawMaterial.getLastDeliveryAmount();
    }

    @JsonProperty("id")
    public long getRawMaterialId() {
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
