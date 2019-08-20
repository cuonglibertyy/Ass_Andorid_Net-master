package com.example.ass_andorid_net.modelpostLater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VersionHistory implements Serializable {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("href")
    @Expose
    private String href;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
