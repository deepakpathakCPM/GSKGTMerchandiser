
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrandGroupMasterGetterSetter {

    @SerializedName("Brand_Group_Master")
    @Expose
    private List<BrandGroupMaster> brandGroupMaster = null;

    public List<BrandGroupMaster> getBrandGroupMaster() {
        return brandGroupMaster;
    }

    public void setBrandGroupMaster(List<BrandGroupMaster> brandGroupMaster) {
        this.brandGroupMaster = brandGroupMaster;
    }

}
