package server.Resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import server.Models.Pallet;

import java.sql.Timestamp;
import java.util.Date;

public class PalletResource {
    private final long id;
    private final int amount;
    private final Date productionDate;
    private final boolean isBlocked;
    private final String location;
    private final Timestamp deliveryTime;

    public PalletResource(Pallet pallet) {
        this.id = pallet.getId();
        this.amount = pallet.getAmount();
        this.productionDate = pallet.getProductionDate();
        this.isBlocked = pallet.isBlocked();
        this.location = pallet.getLocation();
        this.deliveryTime = pallet.getDeliveryTime();
    }

    @JsonProperty("id")
    public long getResourceId() {
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
