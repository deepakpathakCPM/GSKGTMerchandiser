
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingWindowGetterSetter {

    @SerializedName("Mapping_Window")
    @Expose
    private List<MappingWindow> mappingWindow = null;

    public List<MappingWindow> getMappingWindow() {
        return mappingWindow;
    }

    public void setMappingWindow(List<MappingWindow> mappingWindow) {
        this.mappingWindow = mappingWindow;
    }

}
