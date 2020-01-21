package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 12/01/2018.
 */

public class HomeObject {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public HomeItemsObject homeItemsObject;

    public HomeObject(String status, HomeItemsObject homeItemsObject) {
        this.status = status;
        this.homeItemsObject = homeItemsObject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HomeItemsObject getHomeItemsObject() {
        return homeItemsObject;
    }

    public void setHomeItemsObject(HomeItemsObject homeItemsObject1) {
        this.homeItemsObject = homeItemsObject1;
    }
}
