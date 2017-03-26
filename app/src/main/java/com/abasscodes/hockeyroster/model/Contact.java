package com.abasscodes.hockeyroster.model;

import com.google.gson.annotations.SerializedName;

public class Contact {
    private String name;
    private String position;
    @SerializedName("imageUrl")
    private String imageUrl;

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPosition() {
        return position;
    }
}
