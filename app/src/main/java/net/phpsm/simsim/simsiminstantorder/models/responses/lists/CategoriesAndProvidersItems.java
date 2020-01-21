package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.responses.ProviderCategory;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 12/01/2018.
 */

public class CategoriesAndProvidersItems {
    @SerializedName("types")
    private ArrayList<ProviderCategory> providerCategoryArrayList = new ArrayList<>();
    @SerializedName("providers")
    private ArrayList<Providers> providersArrayList = new ArrayList<>();

    public CategoriesAndProvidersItems() {
    }

    public CategoriesAndProvidersItems(ArrayList<ProviderCategory> providerCategoryArrayList, ArrayList<Providers> providersArrayList) {
        this.providerCategoryArrayList = providerCategoryArrayList;
        this.providersArrayList = providersArrayList;
    }

    public ArrayList<ProviderCategory> getProviderCategoryArrayList() {
        return providerCategoryArrayList;
    }

    public void setProviderCategoryArrayList(ArrayList<ProviderCategory> providerCategoryArrayList) {
        this.providerCategoryArrayList = providerCategoryArrayList;
    }

    public ArrayList<Providers> getProvidersArrayList() {
        return providersArrayList;
    }

    public void setProvidersArrayList(ArrayList<Providers> providersArrayList) {
        this.providersArrayList = providersArrayList;
    }
}
