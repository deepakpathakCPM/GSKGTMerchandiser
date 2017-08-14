
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JCPGetterSetter implements Serializable
{

    @SerializedName("Journey_Plan")
    @Expose
    private List<JourneyPlan> journeyPlan = null;
    private final static long serialVersionUID = 3272221198033373344L;

    public List<JourneyPlan> getJourneyPlan() {
        return journeyPlan;
    }

    public void setJourneyPlan(List<JourneyPlan> journeyPlan) {
        this.journeyPlan = journeyPlan;
    }

}
