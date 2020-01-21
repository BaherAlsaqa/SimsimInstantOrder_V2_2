package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.Branche;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 15/01/2018.
 */

public class ProvidersCheckin {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    private ArrayList<Branche> providersArrayList = new ArrayList<>();

    public ProvidersCheckin(String status, ArrayList<Branche> providersArrayList) {
        this.status = status;
        this.providersArrayList = providersArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Branche> getProvidersArrayList() {
        return providersArrayList;
    }

    public void setProvidersArrayList(ArrayList<Branche> providersArrayList) {
        this.providersArrayList = providersArrayList;
    }
}
