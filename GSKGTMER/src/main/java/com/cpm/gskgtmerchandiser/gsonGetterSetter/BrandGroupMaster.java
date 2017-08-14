
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandGroupMaster {

    @SerializedName("Brand_Group_Id")
    @Expose
    private Integer brandGroupId;
    @SerializedName("Brand_Group")
    @Expose
    private String brandGroup;
    @SerializedName("Company_Id")
    @Expose
    private Integer companyId;
    @SerializedName("Brand_Group_Sequence")
    @Expose
    private Integer brandGroupSequence;

    public Integer getBrandGroupId() {
        return brandGroupId;
    }

    public void setBrandGroupId(Integer brandGroupId) {
        this.brandGroupId = brandGroupId;
    }

    public String getBrandGroup() {
        return brandGroup;
    }

    public void setBrandGroup(String brandGroup) {
        this.brandGroup = brandGroup;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getBrandGroupSequence() {
        return brandGroupSequence;
    }

    public void setBrandGroupSequence(Integer brandGroupSequence) {
        this.brandGroupSequence = brandGroupSequence;
    }

}
