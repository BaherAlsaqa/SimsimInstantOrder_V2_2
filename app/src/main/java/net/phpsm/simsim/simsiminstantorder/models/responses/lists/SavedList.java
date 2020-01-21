package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.Save;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 19/01/2018.
 */

public class SavedList {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    private ArrayList<Save> saveArrayList = new ArrayList<>();

    public SavedList(String status, ArrayList<Save> saveArrayList) {
        this.status = status;
        this.saveArrayList = saveArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Save> getSaveArrayList() {
        return saveArrayList;
    }

    public void setSaveArrayList(ArrayList<Save> saveArrayList) {
        this.saveArrayList = saveArrayList;
    }
}
