package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.responses.Order_;

import java.util.List;

/**
 * Created by HP on 1/16/2018.
 */

public class MyPurchaseList {
    @SerializedName("current_page")
    @Expose
    private String currentPage;
    @SerializedName("data")
    @Expose
    private List<Order_> purchaseorderItems;

    @SerializedName("first_page_url")
    @Expose
    private String first_page_url;

    @SerializedName("from")
    @Expose
    private String from;


    @SerializedName("last_page")
    @Expose
    private String last_page;


    @SerializedName("last_page_url")
    @Expose
    private String last_page_url;


    @SerializedName("next_page_url")
    @Expose
    private String next_page_url;


    @SerializedName("path")
    @Expose
    private String path;


    @SerializedName("per_page")
    @Expose
    private String per_page;


    @SerializedName("prev_page_url")
    @Expose
    private String prev_page_url;



    @SerializedName("to")
    @Expose
    private String to;



    @SerializedName("total")
    @Expose
    private String total;


    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<Order_> getPurchaseorderItems() {
        return purchaseorderItems;
    }

    public void setPurchaseorderItems(List<Order_> purchaseorderItems) {
        this.purchaseorderItems = purchaseorderItems;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLast_page() {
        return last_page;
    }

    public void setLast_page(String last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
