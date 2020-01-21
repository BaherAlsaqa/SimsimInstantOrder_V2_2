package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 24/01/2018.
 */

public class Product {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("additionals")
    @Expose
    private ArrayList<Additionals> additionalsArrayList = null;

    public Product(Integer id, Integer amount, ArrayList<Additionals> additionalsArrayList) {
        this.id = id;
        this.amount = amount;
        this.additionalsArrayList = additionalsArrayList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ArrayList<Additionals> getAdditionalsArrayList() {
        return additionalsArrayList;
    }

    public void setAdditionalsArrayList(ArrayList<Additionals> additionalsArrayList) {
        this.additionalsArrayList = additionalsArrayList;
    }
}
