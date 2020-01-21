package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 23/01/2018.
 */

public class DeliveryStatusList {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    private ArrayList<Delivery_Status> delivery_statusArrayList = new ArrayList<>();

    public DeliveryStatusList(String status, ArrayList<Delivery_Status> delivery_statusArrayList) {
        this.status = status;
        this.delivery_statusArrayList = delivery_statusArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Delivery_Status> getDelivery_statusArrayList() {
        return delivery_statusArrayList;
    }

    public void setDelivery_statusArrayList(ArrayList<Delivery_Status> delivery_statusArrayList) {
        this.delivery_statusArrayList = delivery_statusArrayList;
    }
}
