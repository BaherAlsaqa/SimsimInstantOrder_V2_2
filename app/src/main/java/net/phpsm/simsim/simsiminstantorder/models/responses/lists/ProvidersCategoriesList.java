package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.responses.ProviderCategory;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 15/01/2018.
 */

public class ProvidersCategoriesList {

    @SerializedName("status")
    public String status;
    @SerializedName("items")
    private ArrayList<ProviderCategory> providerCategoryArrayList = new ArrayList<>();

    public ProvidersCategoriesList(){}

    public ProvidersCategoriesList(String status, ArrayList<ProviderCategory> providerCategoryArrayList) {
        this.status = status;
        this.providerCategoryArrayList = providerCategoryArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ProviderCategory> getProviderCategoryArrayList() {
        return providerCategoryArrayList;
    }

    public void setProviderCategoryArrayList(ArrayList<ProviderCategory> providerCategoryArrayList) {
        this.providerCategoryArrayList = providerCategoryArrayList;
    }
}
