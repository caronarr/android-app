package org.caronar.app.location;

import android.location.Location;

public interface GeoCoordinate {
    double getLatitude();
    double getLongitude();

    default double distanceTo(GeoCoordinate other) {
        if (getLatitude() == DEFAULT_LATITUDE
                || other.getLatitude() == DEFAULT_LATITUDE
                || getLongitude() == DEFAULT_LONGITUDE
                || other.getLongitude() == DEFAULT_LONGITUDE) {
            return -1;
        }
        Location a = new Location("");
        Location b = new Location("");
        this.intoAndroidLocation(a);
        other.intoAndroidLocation(b);
        return a.distanceTo(b);
    }

    default void intoAndroidLocation(Location loc) {
        if (getLatitude() == DEFAULT_LATITUDE || getLongitude() == DEFAULT_LONGITUDE) {
            return;
        }
        loc.setLatitude(getLatitude());
        loc.setLongitude(getLongitude());
    }

    double DEFAULT_LATITUDE = 100;
    double DEFAULT_LONGITUDE = 300;
}
