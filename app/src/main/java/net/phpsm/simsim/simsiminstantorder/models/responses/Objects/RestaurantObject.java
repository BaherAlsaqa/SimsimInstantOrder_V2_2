package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by baher on 12/01/2018.
 */

public class RestaurantObject {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    public RestaurantItemsObject restaurantItemsObject;

    public RestaurantObject(String status, RestaurantItemsObject restaurantItemsObject1) {
        this.status = status;
        this.restaurantItemsObject = restaurantItemsObject1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RestaurantItemsObject getRestaurantItemsObject() {
        return restaurantItemsObject;
    }

    public void setRestaurantItemsObject(RestaurantItemsObject homeItemsObject1) {
        this.restaurantItemsObject = homeItemsObject1;
    }
}
