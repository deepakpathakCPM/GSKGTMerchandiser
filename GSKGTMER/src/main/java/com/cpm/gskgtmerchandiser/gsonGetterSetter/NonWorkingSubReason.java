
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonWorkingSubReason {

    @SerializedName("Sub_Reason_Id")
    @Expose
    private Integer subReasonId;
    @SerializedName("Sub_Reason")
    @Expose
    private String subReason;
    @SerializedName("Reason_Id")
    @Expose
    private Integer reasonId;

    public Integer getSubReasonId() {
        return subReasonId;
    }

    public void setSubReasonId(Integer subReasonId) {
        this.subReasonId = subReasonId;
    }

    public String getSubReason() {
        return subReason;
    }

    public void setSubReason(String subReason) {
        this.subReason = subReason;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

}
