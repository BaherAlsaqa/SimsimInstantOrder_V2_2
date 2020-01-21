package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.QAItems;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by THE Pro4Apps INC on 1/25/2018.
 */

public class QAList {
    @SerializedName("status")
    boolean status;
    @SerializedName("items")
    ArrayList<QAItems> items = new ArrayList<>();

    public QAList() {
    }

    public QAList(boolean status, ArrayList<QAItems> items) {
        this.status = status;
        this.items = items;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<QAItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<QAItems> items) {
        this.items = items;
    }
}

