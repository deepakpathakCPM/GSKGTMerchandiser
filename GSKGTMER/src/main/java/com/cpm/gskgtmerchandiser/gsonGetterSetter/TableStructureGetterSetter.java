
package com.cpm.gskgtmerchandiser.gsonGetterSetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableStructureGetterSetter {

    @SerializedName("Table_Structure")
    @Expose
    private List<TableQuery> result = null;

    public List<TableQuery> getResult() {
        return result;
    }

    public void setResult(List<TableQuery> result) {
        this.result = result;
    }

}
