package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 1/16/2018.
 */

public class Mypurchase {
    @SerializedName("status")

    public String status;
    @SerializedName("items")

    public MyPurchaseList items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MyPurchaseList getItems() {
        return items;
    }

    public void setItems(MyPurchaseList items) {
        this.items = items;
    }
}
