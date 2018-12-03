package org.caronar.app.ride;

import org.caronar.app.location.GeoCoordinate;

import java.util.Date;

public interface Schedule {
    GeoCoordinate getOrigin();
    GeoCoordinate getDestination();
    Date getScheduledTime();

    default void distanceTo(Schedule other, double[] outDistance) {
        outDistance[0] = getOrigin().distanceTo(other.getOrigin());
        outDistance[1] = getDestination().distanceTo(other.getDestination());
        outDistance[2] = ((double) getScheduledTime().getTime() - other.getScheduledTime().getTime()) / 1000;
    }

    Date DEFAULT_SCHEDULED_TIME = new Date(0);
}
