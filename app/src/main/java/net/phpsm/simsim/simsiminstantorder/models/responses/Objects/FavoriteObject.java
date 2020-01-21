package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by baher on 13/01/2018.
 */

public class FavoriteObject {
    @SerializedName("providers")
    @Expose
    private List<Providers> providers = null;
    @SerializedName("orders")
    @Expose
    private List<Object> orders = null;

    public List<Providers> getProviders() {
        return providers;
    }

    public void setProviders(List<Providers> providers) {
        this.providers = providers;
    }

    public List<Object> getOrders() {
        return orders;
    }

    public void setOrders(List<Object> orders) {
        this.orders = orders;
    }

}
