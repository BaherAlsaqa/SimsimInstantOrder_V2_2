
package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.phpsm.simsim.simsiminstantorder.models.Branche;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.DeliveryStatus;

import java.io.Serializable;
import java.util.List;

public class Datum implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("products_list")
    @Expose
    private String productsList;
    @SerializedName("price_additional_item")
    @Expose
    private Double priceAdditionalItem;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("total_price")
    @Expose
    private Double totalPrice;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("count_additional")
    @Expose
    private Integer countAdditional;
    @SerializedName("order_code")
    @Expose
    private String orderCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("customer_track_time")
    @Expose
    private String customerTrackTime;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("branch")
    @Expose
    private Branche branche;
    @SerializedName("provider")
    @Expose
    private providerData provider;
    @SerializedName("order_items")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("order_additionals")
    @Expose
    private List<OrderAdditional> orderAdditionals = null;

    @SerializedName("delivery_status")
    @Expose
      private DeliveryStatus delivery_status;

    @SerializedName("is_favorite")
    @Expose
    private Boolean is_favorite;

    public DeliveryStatus getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(DeliveryStatus delivery_status) {
        this.delivery_status = delivery_status;
    }

    public Datum(List<OrderItem> orderItems, List<OrderAdditional> orderAdditionals){
        this.orderItems=orderItems;
        this.orderAdditionals=orderAdditionals;
    }
    protected Datum(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        status = in.readString();
        productsList = in.readString();
        if (in.readByte() == 0) {
            priceAdditionalItem = null;
        } else {
            priceAdditionalItem = Double.valueOf(in.readInt());
        }
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = Double.valueOf(in.readInt());
        }
        if (in.readByte() == 0) {
            totalPrice = null;
        } else {
            totalPrice = Double.valueOf(in.readInt());
        }
        paymentMethod = in.readString();
        if (in.readByte() == 0) {
            countAdditional = null;
        } else {
            countAdditional = in.readInt();
        }
        orderCode = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            providerId = null;
        } else {
            providerId = in.readInt();
        }
        if (in.readByte() == 0) {
            customerId = null;
        } else {
            customerId = in.readInt();
        }
        deliveryTime = in.readString();
        if (in.readByte() == 0) {
            branchId = null;
        } else {
            branchId = in.readInt();
        }
        byte tmpIs_favorite = in.readByte();
        is_favorite = tmpIs_favorite == 0 ? null : tmpIs_favorite == 1;
    }



    public Boolean getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(Boolean is_favorite) {
        this.is_favorite = is_favorite;
    }






    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductsList() {
        return productsList;
    }

    public void setProductsList(String productsList) {
        this.productsList = productsList;
    }

    public Double getPriceAdditionalItem() {
        return priceAdditionalItem;
    }

    public void setPriceAdditionalItem(Double priceAdditionalItem) {
        this.priceAdditionalItem = priceAdditionalItem;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getCountAdditional() {
        return countAdditional;
    }

    public void setCountAdditional(Integer countAdditional) {
        this.countAdditional = countAdditional;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCustomerTrackTime() {
        return customerTrackTime;
    }

    public void setCustomerTrackTime(String customerTrackTime) {
        this.customerTrackTime = customerTrackTime;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public providerData getProvider() {
        return provider;
    }

    public void setProvider(providerData provider) {
        this.provider = provider;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderAdditional> getOrderAdditionals() {
        return orderAdditionals;
    }

    public void setOrderAdditionals(List<OrderAdditional> orderAdditionals) {
        this.orderAdditionals = orderAdditionals;
    }


}
