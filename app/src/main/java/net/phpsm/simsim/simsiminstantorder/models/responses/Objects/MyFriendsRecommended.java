package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 21/11/2017.
 */

public class MyFriendsRecommended implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("type_recommend")
    @Expose
    private String typeRecommend;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("order_item_id")
    @Expose
    private int orderItemId;
    @SerializedName("customer_id")
    @Expose
    private int customerId;
    @SerializedName("is_freind")
    @Expose
    private boolean is_freind;
    @SerializedName("provider_id")
    @Expose
    private int provider_id;
    @SerializedName("orderitems")
    @Expose
    private ArrayList<Orderitem> orderitemArrayList = null;
    @SerializedName("customer")
    @Expose
    private CustomerObject customer;
    @SerializedName("provider")
    @Expose
    private Providers providers;

    public MyFriendsRecommended(){}

    protected MyFriendsRecommended(Parcel in) {
        id = in.readInt();
        typeRecommend = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        deletedAt = in.readString();
        orderItemId = in.readInt();
        customerId = in.readInt();
        provider_id = in.readInt();
    }

    public static final Creator<MyFriendsRecommended> CREATOR = new Creator<MyFriendsRecommended>() {
        @Override
        public MyFriendsRecommended createFromParcel(Parcel in) {
            return new MyFriendsRecommended(in);
        }

        @Override
        public MyFriendsRecommended[] newArray(int size) {
            return new MyFriendsRecommended[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeRecommend() {
        return typeRecommend;
    }

    public void setTypeRecommend(String typeRecommend) {
        this.typeRecommend = typeRecommend;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public CustomerObject getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerObject customer) {
        this.customer = customer;
    }

    public boolean getIs_freind() {
        return is_freind;
    }

    public void setIs_freind(boolean is_freind) {
        this.is_freind = is_freind;
    }

    public ArrayList<Orderitem> getOrderitemArrayList() {
        return orderitemArrayList;
    }

    public void setOrderitemArrayList(ArrayList<Orderitem> orderitemArrayList) {
        this.orderitemArrayList = orderitemArrayList;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(typeRecommend);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(deletedAt);
        dest.writeInt(orderItemId);
        dest.writeInt(customerId);
        dest.writeInt(provider_id);
    }
}
