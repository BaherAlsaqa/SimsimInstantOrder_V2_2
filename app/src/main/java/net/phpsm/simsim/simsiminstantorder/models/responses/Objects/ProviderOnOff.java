package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 12/01/2018.
 */

public class ProviderOnOff {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public String items;

    public ProviderOnOff(String status, String items) {
        this.status = status;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
