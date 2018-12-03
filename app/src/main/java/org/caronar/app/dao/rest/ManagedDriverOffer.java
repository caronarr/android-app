package org.caronar.app.dao.rest;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.Location;
import org.caronar.app.model.User;
import org.caronar.app.ride.Schedule;

import java.util.Date;

final class ManagedDriverOffer extends ManagedModel implements DriverOffer, DriverOffer.Editor {
    @SerializedName("origin")
    private ManagedLocation mOrigin = new ManagedLocation();

    @SerializedName("destination")
    private ManagedLocation mDestination = new ManagedLocation();

    @SerializedName("scheduledTime")
    private Date mScheduledTime = Schedule.DEFAULT_SCHEDULED_TIME;

    @SerializedName("requestedTip")
    private double mRequestedTip = 0;

    @SerializedName("driver")
    private ManagedUser mDriver = new ManagedUser();

    @Override public Location getOrigin() {
        return mOrigin;
    }

    @Override public Location getDestination() {
        return mDestination;
    }

    @Override public Date getScheduledTime() {
        return mScheduledTime;
    }

    @Override public double getRequestedTip() {
        return mRequestedTip;
    }

    @Override public User getDriver() {
        return mDriver;
    }

    @Override public DriverOffer.Editor edit() {
        return this;
    }

    @Override public DriverOffer.Editor setOrigin(Location origin) {
        mOrigin.assign(origin);
        return this;
    }

    @Override public DriverOffer.Editor setDestination(Location destination) {
        mDestination.assign(destination);
        return this;
    }

    @Override public DriverOffer.Editor setScheduledTime(Date scheduledTime) {
        mScheduledTime = scheduledTime == null ? Schedule.DEFAULT_SCHEDULED_TIME : scheduledTime;
        return this;
    }

    @Override public DriverOffer.Editor setRequestedTip(double tip) {
        mRequestedTip = tip;
        return this;
    }

    @Override public DriverOffer.Editor setDriver(User driver) {
        mDriver.assign(driver);
        return this;
    }

    @Override public DriverOffer finalizeEdition() {
        return this;
    }

    void assign(DriverOffer other) {
        if (other == null)
            other = DriverOffer.DEFAULT;
        if (other == this)
            return;
        mId = other.getId();
        setOrigin(other.getOrigin());
        setDestination(other.getDestination());
        setScheduledTime(other.getScheduledTime());
        setRequestedTip(other.getRequestedTip());
        setDriver(other.getDriver());
    }
}
