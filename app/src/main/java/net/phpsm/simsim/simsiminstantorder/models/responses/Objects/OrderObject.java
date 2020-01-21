package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 24/01/2018.
 */

public class OrderObject {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errors")
    @Expose
    private String error;
    @SerializedName("items")
    @Expose
    private OrderResponse items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public OrderResponse getItems() {
        return items;
    }

    public void setItems(OrderResponse items) {
        this.items = items;
    }
}
