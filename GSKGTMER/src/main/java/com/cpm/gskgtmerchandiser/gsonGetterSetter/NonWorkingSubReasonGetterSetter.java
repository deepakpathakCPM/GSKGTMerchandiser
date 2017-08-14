
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonWorkingSubReasonGetterSetter {

    @SerializedName("Non_Working_Sub_Reason")
    @Expose
    private List<NonWorkingSubReason> result = null;

    public List<NonWorkingSubReason> getResult() {
        return result;
    }

    public void setResult(List<NonWorkingSubReason> result) {
        this.result = result;
    }

}
