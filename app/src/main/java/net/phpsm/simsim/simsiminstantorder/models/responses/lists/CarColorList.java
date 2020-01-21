package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.responses.CarColors;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 29/11/2017.
 */

public class CarColorList {

    @SerializedName("status")
    public String status;
    @SerializedName("items")
    private ArrayList<CarColors> carColorsArrayList = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CarColors> getCarColorsArrayList() {
        return carColorsArrayList;
    }

    public void setCarColorsArrayList(ArrayList<CarColors> carColorsArrayList1) {
        this.carColorsArrayList = carColorsArrayList1;
    }
}
