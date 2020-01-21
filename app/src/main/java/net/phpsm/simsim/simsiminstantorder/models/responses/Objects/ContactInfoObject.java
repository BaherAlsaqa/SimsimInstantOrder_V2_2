package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 27/01/2018.
 */

public class ContactInfoObject {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("items")
    @Expose
    private ContactInfoItem items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContactInfoItem getItems() {
        return items;
    }

    public void setItems(ContactInfoItem items) {
        this.items = items;
    }
}
