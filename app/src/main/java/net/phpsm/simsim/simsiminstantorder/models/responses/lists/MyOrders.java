
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyOrders implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("items")
    @Expose
    private Items items;

    protected MyOrders(Parcel in) {
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }


}
