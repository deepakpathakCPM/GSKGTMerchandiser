package com.cpm.gskgtmerchandiser.GetterSetter;

import java.util.ArrayList;

/**
 * Created by ashishc on 09-01-2017.
 */

public class AddittionalGetterSetter {


    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getBrand_id() {
        return Brand_id;
    }

    public void setBrand_id(String brand_id) {
        Brand_id = brand_id;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    public String getSku_id() {
        return Sku_id;
    }

    public void setSku_id(String sku_id) {
        Sku_id = sku_id;
    }

    public String getStore_id() {
        return Store_id;
    }

    public void setStore_id(String store_id) {
        Store_id = store_id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    String Brand,Brand_id,Sku,Sku_id,Store_id,Image="";


    String Image2="";

    public String getImage3() {
        return Image3;
    }

    public void setImage3(String image3) {
        Image3 = image3;
    }

    public String getImage2() {
        return Image2;
    }

    public void setImage2(String image2) {
        Image2 = image2;
    }

    String Image3="";

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    String key_id;

    public String getBtn_toogle() {
        return btn_toogle;
    }

    public void setBtn_toogle(String btn_toogle) {
        this.btn_toogle = btn_toogle;
    }

    String btn_toogle;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    String categoryId;

    ArrayList<AdditionalDialogGetterSetter> skuDialogList = new ArrayList<>();

    public ArrayList<AdditionalDialogGetterSetter> getSkuDialogList() {
        return skuDialogList;
    }

    public void setSkuDialogList(ArrayList<AdditionalDialogGetterSetter> skuDialogList) {
        this.skuDialogList = skuDialogList;
    }
}
