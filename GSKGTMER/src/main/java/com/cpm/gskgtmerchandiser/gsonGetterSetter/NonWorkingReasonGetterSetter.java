
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NonWorkingReasonGetterSetter {

    @SerializedName("Non_Working_Reason")
    @Expose
    private List<NonWorkingReason> result = null;

    public List<NonWorkingReason> getResult() {
        return result;
    }

    public void setResult(List<NonWorkingReason> result) {
        this.result = result;
    }

}
