
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WindowMaster {

    @SerializedName("Window_Id")
    @Expose
    private Integer windowId;
    @SerializedName("Window")
    @Expose
    private String window;

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

}
