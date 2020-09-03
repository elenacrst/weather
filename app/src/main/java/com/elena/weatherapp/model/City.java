package com.elena.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("name")
    private String name;
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
