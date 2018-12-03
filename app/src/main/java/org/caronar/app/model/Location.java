package org.caronar.app.model;

import org.caronar.app.location.GeoCoordinate;

public interface Location extends GeoCoordinate, BaseModel {

    @Override long getId();
    String getName();
    @Override double getLatitude();
    @Override double getLongitude();
    Editor edit();

    interface Editor {
        Editor setName(String name);
        Editor setLatitude(double latitude);
        Editor setLongitude(double longitude);
        Location finalizeEdition();

        class Default implements Editor {
            private final Location mLocation;

            public Default(Location location) {
                mLocation = location;
            }

            @Override public Editor setName(String name) {
                return this;
            }

            @Override public Editor setLatitude(double latitude) {
                return this;
            }

            @Override public Editor setLongitude(double longitude) {
                return this;
            }

            @Override public Location finalizeEdition() {
                return mLocation;
            }
        }
    }

    Location DEFAULT = new Location() {

        @Override public long getId() {
            return 0;
        }

        @Override public double getLatitude() {
            return Location.DEFAULT_LATITUDE;
        }

        @Override public double getLongitude() {
            return Location.DEFAULT_LONGITUDE;
        }

        @Override public String getName() {
            return DEFAULT_NAME;
        }

        @Override public Editor edit() {
            return new Editor.Default(this);
        }

    };

    String DEFAULT_NAME = "location_without_name";
}
