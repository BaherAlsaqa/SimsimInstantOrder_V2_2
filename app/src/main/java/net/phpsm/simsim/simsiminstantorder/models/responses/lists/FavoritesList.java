package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 13/01/2018.
 */

public class FavoritesList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("items")
    @Expose
    private FavProvidersAndOrdersItems items;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FavProvidersAndOrdersItems getItems() {
        return items;
    }

    public void setItems(FavProvidersAndOrdersItems items) {
        this.items = items;
    }


}
