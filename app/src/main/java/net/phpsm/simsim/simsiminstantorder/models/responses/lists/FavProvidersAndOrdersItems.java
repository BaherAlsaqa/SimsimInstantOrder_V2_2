package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.FavoritsOrders;
import net.phpsm.simsim.simsiminstantorder.models.responses.FavoritsProviders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baher on 13/01/2018.
 */

public class FavProvidersAndOrdersItems {

    @SerializedName("providers")
    @Expose
    private List<FavoritsProviders> providers = new ArrayList<>();
    @SerializedName("orders")
    @Expose
    private List<FavoritsOrders> orders = new ArrayList<>();

    public List<FavoritsProviders> getProviders() {
        return providers;
    }

    public void setProviders(List<FavoritsProviders> providers) {
        this.providers = providers;
    }

    public List<FavoritsOrders> getOrders() {
        return orders;
    }

    public void setOrders(List<FavoritsOrders> orders) {
        this.orders = orders;
    }

}
