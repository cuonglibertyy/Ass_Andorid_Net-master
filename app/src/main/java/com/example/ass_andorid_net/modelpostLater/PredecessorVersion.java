package com.example.ass_andorid_net.modelpostLater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PredecessorVersion implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("href")
    @Expose
    private String href;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
