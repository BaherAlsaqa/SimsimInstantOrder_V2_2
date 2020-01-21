package net.phpsm.simsim.simsiminstantorder.models;

import java.util.ArrayList;

/**
 * Created by baher on 24/01/2018.
 */

public class UpOrder {
    public Integer id;
    public String title;
    public int quantity;
    public String description;
    public Double price;
    public Double priceAdditional;
    public String discount;
    public String trial;
    public String createdAt;
    public String updatedAt;
    public String deletedAt;
    public Integer providerId;
    public String imageProduct;
    public String amount;
    public Integer productCategoryId;
    public String urlImageProduct;
    private ArrayList<ProductAdditional> additions = null;

    public UpOrder(Integer id, String title, int quantity, String description, Double price,Double priceAdditional, String discount, String trial, String createdAt, String updatedAt, String deletedAt, Integer providerId, String imageProduct, String amount, Integer productCategoryId, String urlImageProduct, ArrayList<ProductAdditional> additions) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.priceAdditional = priceAdditional;
        this.discount = discount;
        this.trial = trial;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.providerId = providerId;
        this.imageProduct = imageProduct;
        this.amount = amount;
        this.productCategoryId = productCategoryId;
        this.urlImageProduct = urlImageProduct;
        this.additions = additions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceAdditional() {
        return priceAdditional;
    }

    public void setPriceAdditional(Double priceAdditional) {
        this.priceAdditional = priceAdditional;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getUrlImageProduct() {
        return urlImageProduct;
    }

    public void setUrlImageProduct(String urlImageProduct) {
        this.urlImageProduct = urlImageProduct;
    }

    public ArrayList<ProductAdditional> getAdditions() {
        return additions;
    }

    public void setAdditions(ArrayList<ProductAdditional> additions) {
        this.additions = additions;
    }
}
