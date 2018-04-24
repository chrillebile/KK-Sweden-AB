package server.Models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Pallet {

    private long id;
    private LocalDate productionDate;
    private boolean isBlocked;
    private String location;
    private Timestamp deliveryTime;

    public Pallet(long id, LocalDate productionDate, boolean isBlocked, String location, Timestamp deliveryTime) {
        this.id = id;
        this.productionDate = productionDate;
        this.isBlocked = isBlocked;
        this.location = location;
        this.deliveryTime = deliveryTime;
    }

    public Pallet() {
    }

    public long getId() {
        return id;
    }

    public LocalDate getProductionDate() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
