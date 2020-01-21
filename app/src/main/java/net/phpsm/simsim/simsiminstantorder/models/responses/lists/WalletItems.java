
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletItems {

    @SerializedName("wallet")
    @Expose
    private List<Wallet> wallet = null;
    @SerializedName("total_points")
    @Expose
    private Integer totalPoints;

    @SerializedName("total_price")
    @Expose
    private double total_price;

    public List<Wallet> getWallet() {
        return wallet;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setWallet(List<Wallet> wallet) {
        this.wallet = wallet;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

}
