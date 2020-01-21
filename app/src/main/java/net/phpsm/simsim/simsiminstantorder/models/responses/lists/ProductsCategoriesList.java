package net.phpsm.simsim.simsiminstantorder.models.responses.lists;

import net.phpsm.simsim.simsiminstantorder.models.ProductCategory;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by baher on 22/01/2018.
 */

public class ProductsCategoriesList {
    @SerializedName("status")
    public String status;
    @SerializedName("items")
    private ArrayList<ProductCategory> productCategoryArrayList = new ArrayList<>();

    public ProductsCategoriesList(String status, ArrayList<ProductCategory> productCategoryArrayList) {
        this.status = status;
        this.productCategoryArrayList = productCategoryArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ProductCategory> getProductCategoryArrayList() {
        return productCategoryArrayList;
    }

    public void setProductCategoryArrayList(ArrayList<ProductCategory> productCategoryArrayList) {
        this.productCategoryArrayList = productCategoryArrayList;
    }
}
