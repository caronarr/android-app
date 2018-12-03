package org.caronar.app.model;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.location.GeoCoordinate;

public final class Location implements GeoCoordinate, BaseModel {

    @SerializedName("id")
    private long mId = 0;

    @SerializedName("name")
    private String mName = DEFAULT_NAME;

    @SerializedName("latitude")
    private double mLatitude = GeoCoordinate.DEFAULT_LATITUDE;

    @SerializedName("longitude")
    private double mLongitude = GeoCoordinate.DEFAULT_LONGITUDE;

    @Override public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    @Override public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public static final Location DEFAULT = new Location();

    public static final String DEFAULT_NAME = "location_without_name";
}
