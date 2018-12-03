package org.caronar.app.dao.rest;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.model.Location;

final class ManagedLocation extends ManagedModel implements Location, Location.Editor {

    @SerializedName("name")
    private String mName = Location.DEFAULT_NAME;

    @SerializedName("lat")
    private double mLatitude = 0;

    @SerializedName("lng")
    private double mLongitude = 0;

    @Override public String getName() {
        return mName;
    }

    @Override public double getLatitude() {
        return mLatitude;
    }

    @Override public double getLongitude() {
        return mLongitude;
    }

    @Override public Location.Editor edit() {
        return this;
    }

    @Override public Location.Editor setName(String name) {
        mName = name == null ? Location.DEFAULT_NAME : name;
        return this;
    }

    @Override public Location.Editor setLatitude(double latitude) {
        mLatitude = latitude;
        return this;
    }

    @Override public Location.Editor setLongitude(double longitude) {
        mLongitude = longitude;
        return this;
    }

    @Override public Location finalizeEdition() {
        return this;
    }

    void assign(Location other) {
        if (other == null)
            other = Location.DEFAULT;
        if (other == this)
            return;
        mId = other.getId();
        setName(other.getName());
        setLatitude(other.getLatitude());
        setLongitude(other.getLongitude());
    }
}
