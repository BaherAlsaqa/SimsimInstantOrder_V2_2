package net.phpsm.simsim.simsiminstantorder.models.Objects;

/**
 * Created by MSI on 5/26/2018.
 */

public class mediaObject {
    int orderId;
    int orderItemId;
    String itemName;
    String itemUrl;

    public mediaObject(int orderId, int orderItemId, String itemName, String itemUrl) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        this.itemName = itemName;
        this.itemUrl = itemUrl;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
