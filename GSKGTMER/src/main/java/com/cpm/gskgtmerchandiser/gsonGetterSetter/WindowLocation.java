
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindowLocation {

    @SerializedName("Window_Location_Id")
    @Expose
    private Integer windowLocationId;
    @SerializedName("Window_Location")
    @Expose
    private String windowLocation;

    public Integer getWindowLocationId() {
        return windowLocationId;
    }

    public void setWindowLocationId(Integer windowLocationId) {
        this.windowLocationId = windowLocationId;
    }

    public String getWindowLocation() {
        return windowLocation;
    }

    public void setWindowLocation(String windowLocation) {
        this.windowLocation = windowLocation;
    }

}
