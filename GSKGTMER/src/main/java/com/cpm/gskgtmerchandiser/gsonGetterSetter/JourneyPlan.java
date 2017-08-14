
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JourneyPlan implements Serializable
{

    @SerializedName("Store_Id")
    @Expose
    private Integer storeId;
    @SerializedName("Visit_Date")
    @Expose
    private String visitDate;
    @SerializedName("Store_Name")
    @Expose
    private String storeName;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("Landmark")
    @Expose
    private String landmark;
    @SerializedName("Pincode")
    @Expose
    private String pincode;
    @SerializedName("Store_Layout")
    @Expose
    private String storeLayout;
    @SerializedName("Store_Size")
    @Expose
    private String storeSize;
    @SerializedName("Contact_Person")
    @Expose
    private String contactPerson;
    @SerializedName("Contact_No")
    @Expose
    private String contactNo;
    @SerializedName("Profile_Pic")
    @Expose
    private String profilePic;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Store_Type")
    @Expose
    private String storeType;
    @SerializedName("Store_Category")
    @Expose
    private String storeCategory;
    @SerializedName("Classification")
    @Expose
    private String classification;
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
    @SerializedName("Reason_Id")
    @Expose
    private Integer reasonId;
    @SerializedName("Sub_Reason_Id")
    @Expose
    private Integer subReasonId;
    @SerializedName("Remark")
    @Expose
    private String remark;
    @SerializedName("Image_Name")
    @Expose
    private String imageName;
    @SerializedName("Upload_Status")
    @Expose
    private String uploadStatus;
    @SerializedName("Geo_Tag")
    @Expose
    private String geoTag;
    private final static long serialVersionUID = -8422266243599263607L;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStoreLayout() {
        return storeLayout;
    }

    public void setStoreLayout(String storeLayout) {
        this.storeLayout = storeLayout;
    }

    public String getStoreSize() {
        return storeSize;
    }

    public void setStoreSize(String storeSize) {
        this.storeSize = storeSize;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreCategory() {
        return storeCategory;
    }

    public void setStoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

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

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public Integer getSubReasonId() {
        return subReasonId;
    }

    public void setSubReasonId(Integer subReasonId) {
        this.subReasonId = subReasonId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

}
