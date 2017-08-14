
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingStock {

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
    @SerializedName("Sku_id")
    @Expose
    private Integer skuId;
    @SerializedName("Focus")
    @Expose
    private Integer focus;

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

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer focus) {
        this.focus = focus;
    }

}
