
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingWindow {

    @SerializedName("Trade_Area_Id")
    @Expose
    private Integer tradeAreaId;
    @SerializedName("Tier_Id")
    @Expose
    private Integer tierId;
    @SerializedName("Store_Type_Id")
    @Expose
    private Integer storeTypeId;
    @SerializedName("Classification_Id")
    @Expose
    private Integer classificationId;
    @SerializedName("Store_Category_Id")
    @Expose
    private Integer storeCategoryId;
    @SerializedName("Brand_Id")
    @Expose
    private Integer brandId;
    @SerializedName("Window_Id")
    @Expose
    private Integer windowId;

    public Integer getTradeAreaId() {
        return tradeAreaId;
    }

    public void setTradeAreaId(Integer tradeAreaId) {
        this.tradeAreaId = tradeAreaId;
    }

    public Integer getTierId() {
        return tierId;
    }

    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }

    public Integer getStoreTypeId() {
        return storeTypeId;
    }

    public void setStoreTypeId(Integer storeTypeId) {
        this.storeTypeId = storeTypeId;
    }

    public Integer getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Integer classificationId) {
        this.classificationId = classificationId;
    }

    public Integer getStoreCategoryId() {
        return storeCategoryId;
    }

    public void setStoreCategoryId(Integer storeCategoryId) {
        this.storeCategoryId = storeCategoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

}
