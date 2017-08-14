
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PosmMaster {

    @SerializedName("Posm_Id")
    @Expose
    private Integer posmId;
    @SerializedName("Posm")
    @Expose
    private String posm;

    public Integer getPosmId() {
        return posmId;
    }

    public void setPosmId(Integer posmId) {
        this.posmId = posmId;
    }

    public String getPosm() {
        return posm;
    }

    public void setPosm(String posm) {
        this.posm = posm;
    }

}
