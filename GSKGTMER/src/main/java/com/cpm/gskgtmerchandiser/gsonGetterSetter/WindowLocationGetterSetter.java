
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindowLocationGetterSetter {

    @SerializedName("Window_Location")
    @Expose
    private List<WindowLocation> windowLocation = null;

    public List<WindowLocation> getWindowLocation() {
        return windowLocation;
    }

    public void setWindowLocation(List<WindowLocation> windowLocation) {
        this.windowLocation = windowLocation;
    }

}
