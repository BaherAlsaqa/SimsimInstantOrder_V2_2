package net.phpsm.simsim.simsiminstantorder.models.responses.Objects;

import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 17/01/2018.
 */

public class Products {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("priceAdditional")
    @Expose
    private Double priceAdditional;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("trial")
    @Expose
    private String trial;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("image_product")
    @Expose
    private String imageProduct;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("product_category_id")
    @Expose
    private Integer productCategoryId;
    @SerializedName("url_image_product")
    @Expose
    private String urlImageProduct;
    @SerializedName("product_additionals")
    @Expose
    private ArrayList<ProductAdditional> productAdditionals = null;
    @SerializedName("stockout")
    @Expose
    private Stockout stockout;
    @SerializedName("provider")
    @Expose
    private Providers provider;

    public Products(){}

    public Products(Integer id, String title, String description, Double price, String discount, String trial, String createdAt, String updatedAt, String deletedAt, Integer providerId, String imageProduct, String amount, Integer productCategoryId, String urlImageProduct, ArrayList<ProductAdditional> productAdditionals, Stockout stockout, Providers provider) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
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
        this.productAdditionals = productAdditionals;
        this.stockout = stockout;
        this.provider = provider;
    }


    public Double getPriceAdditional() {
        return priceAdditional;
    }

    public void setPriceAdditional(Double priceAdditional) {
        this.priceAdditional = priceAdditional;
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

    public ArrayList<ProductAdditional> getProductAdditionals() {
        return productAdditionals;
    }

    public void setProductAdditionals(ArrayList<ProductAdditional> productAdditionals) {
        this.productAdditionals = productAdditionals;
    }

    public Stockout getStockout() {
        return stockout;
    }

    public void setStockout(Stockout stockout) {
        this.stockout = stockout;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }
}
