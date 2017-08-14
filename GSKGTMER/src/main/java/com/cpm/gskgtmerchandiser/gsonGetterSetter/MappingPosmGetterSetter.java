
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappingPosmGetterSetter {

    @SerializedName("Mapping_Posm")
    @Expose
    private List<MappingPosm> mappingPosm = null;

    public List<MappingPosm> getMappingPosm() {
        return mappingPosm;
    }

    public void setMappingPosm(List<MappingPosm> mappingPosm) {
        this.mappingPosm = mappingPosm;
    }

}
