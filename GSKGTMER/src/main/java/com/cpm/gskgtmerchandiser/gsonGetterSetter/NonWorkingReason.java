
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonWorkingReason {

    @SerializedName("Reason_Id")
    @Expose
    private Integer reasonId;
    @SerializedName("Reason")
    @Expose
    private String reason;
    @SerializedName("Entry_Allow")
    @Expose
    private Boolean entryAllow;
    @SerializedName("Image_Allow")
    @Expose
    private Boolean imageAllow;

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getEntryAllow() {
        return entryAllow;
    }

    public void setEntryAllow(Boolean entryAllow) {
        this.entryAllow = entryAllow;
    }

    public Boolean getImageAllow() {
        return imageAllow;
    }

    public void setImageAllow(Boolean imageAllow) {
        this.imageAllow = imageAllow;
    }

}
