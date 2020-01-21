package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HP on 1/16/2018.
 */

public  class MyPurchaseData implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("products_list")
    @Expose
    private String products_list;

    @SerializedName("price_additional_item")
    @Expose
    private Double price_additional_item;
    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("total_price")
    @Expose
    private Double total_price;

    @SerializedName("payment_method")
    @Expose
    private String payment_method;

    @SerializedName("count_additional")
    @Expose
    private Integer count_additional;

    @SerializedName("order_code")
    @Expose
    private String order_code;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("deleted_at")
    @Expose
    private String deleted_at;

    @SerializedName("provider_id")
    @Expose
    private int provider_id;


    @SerializedName("customer_id")
    @Expose
    private int customer_id;


    @SerializedName("delivery_time")
    @Expose
    private String delivery_time;


    @SerializedName("customer_track_time")
    @Expose
    private String customer_track_time;


    @SerializedName("branch_id")
    @Expose
    private int branch_id;

    @SerializedName("provider")
    @Expose
    private providerData provider;

    @SerializedName("order_items")
    @Expose
    private List<PurchaseorderItems> orderItems;

    @SerializedName("order_additionals")
    @Expose
    private List<OrderAdditionals> order_additionals;

    protected MyPurchaseData(Parcel in) {
        id = in.readInt();
        status = in.readString();
        products_list = in.readString();
        price_additional_item = Double.valueOf(in.readInt());
        price = Double.valueOf(in.readInt());
        total_price = Double.valueOf(in.readInt());
        payment_method = in.readString();
        count_additional = in.readInt();
        order_code = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        deleted_at = in.readString();
        provider_id = in.readInt();
        customer_id = in.readInt();
        delivery_time = in.readString();
        customer_track_time = in.readString();
        branch_id = in.readInt();
    }

    public static final Creator<MyPurchaseData> CREATOR = new Creator<MyPurchaseData>() {
        @Override
        public MyPurchaseData createFromParcel(Parcel in) {
            return new MyPurchaseData(in);
        }

        @Override
        public MyPurchaseData[] newArray(int size) {
            return new MyPurchaseData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProducts_list() {
        return products_list;
    }

    public void setProducts_list(String products_list) {
        this.products_list = products_list;
    }

    public Double getPrice_additional_item() {
        return price_additional_item;
    }

    public void setPrice_additional_item(Double price_additional_item) {
        this.price_additional_item = price_additional_item;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Integer getCount_additional() {
        return count_additional;
    }

    public void setCount_additional(Integer count_additional) {
        this.count_additional = count_additional;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getCustomer_track_time() {
        return customer_track_time;
    }

    public void setCustomer_track_time(String customer_track_time) {
        this.customer_track_time = customer_track_time;
    }

    public int getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(int branch_id) {
        this.branch_id = branch_id;
    }

    public providerData getProvider() {
        return provider;
    }

    public void setProvider(providerData provider) {
        this.provider = provider;
    }

    public List<PurchaseorderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<PurchaseorderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderAdditionals> getOrder_additionals() {
        return order_additionals;
    }

    public void setOrder_additionals(List<OrderAdditionals> order_additionals) {
        this.order_additionals = order_additionals;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(status);
        dest.writeString(products_list);
        dest.writeDouble(price_additional_item);
        dest.writeDouble(price);
        dest.writeDouble(total_price);
        dest.writeString(payment_method);
        dest.writeInt(count_additional);
        dest.writeString(order_code);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(deleted_at);
        dest.writeInt(provider_id);
        dest.writeInt(customer_id);
        dest.writeString(delivery_time);
        dest.writeString(customer_track_time);
        dest.writeInt(branch_id);
    }
}
