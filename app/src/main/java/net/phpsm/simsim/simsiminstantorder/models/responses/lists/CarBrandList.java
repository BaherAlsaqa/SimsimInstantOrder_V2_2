package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.responses.CarBrands;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 29/11/2017.
 */

public class CarBrandList {

    @SerializedName("status")
    public String status;
    @SerializedName("items")
    private ArrayList<CarBrands> carBrandsArrayList = new ArrayList<>();
    /*@SerializedName("items")
    private ArrayList<Provider> providersList = new ArrayList<>();*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CarBrands> getCarBrandsArrayList() {
        return carBrandsArrayList;
    }

    public void setCarBrandsArrayList(ArrayList<CarBrands> carBrandsArrayList) {
        this.carBrandsArrayList = carBrandsArrayList;
    }

    /*public ArrayList<Provider> getProvidersList() {
        return providersList;
    }

    public void setProvidersList(ArrayList<Provider> providersList) {
        this.providersList = providersList;
    }*/
}
