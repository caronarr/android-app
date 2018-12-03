package org.caronar.app.dao.rest;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.model.Location;
import org.caronar.app.model.RiderOffer;
import org.caronar.app.model.User;
import org.caronar.app.ride.Schedule;

import java.util.Date;

final class ManagedRiderOffer extends ManagedModel implements RiderOffer, RiderOffer.Editor {
    @SerializedName("origin")
    private ManagedLocation mOrigin = new ManagedLocation();

    @SerializedName("destination")
    private ManagedLocation mDestination = new ManagedLocation();

    @SerializedName("scheduledTime")
    private Date mScheduledTime = Schedule.DEFAULT_SCHEDULED_TIME;

    @SerializedName("rider")
    private ManagedUser mRider = new ManagedUser();

    @SerializedName("offeredTip")
    private double mOfferedTip = 0;

    @Override public Location getOrigin() {
        return mOrigin;
    }

    @Override public Location getDestination() {
        return mDestination;
    }

    @Override public Date getScheduledTime() {
        return mScheduledTime;
    }

    @Override public double getOfferedTip() {
        return mOfferedTip;
    }

    @Override public User getRider() {
        return mRider;
    }

    @Override public RiderOffer.Editor edit() {
        return this;
    }

    @Override public RiderOffer.Editor setOrigin(Location origin) {
        mOrigin.assign(origin);
        return this;
    }

    @Override public RiderOffer.Editor setDestination(Location destination) {
        mDestination.assign(destination);
        return this;
    }

    @Override public RiderOffer.Editor setScheduledTime(Date scheduledTime) {
        mScheduledTime = scheduledTime;
        return this;
    }

    @Override public RiderOffer.Editor setOfferedTip(double offeredTip) {
        mOfferedTip = offeredTip;
        return this;
    }

    @Override public Editor setRider(User rider) {
        mRider.assign(rider);
        return this;
    }

    @Override public RiderOffer finalizeEdition() {
        return this;
    }

    void assign(RiderOffer other) {
        if (other == null)
            other = RiderOffer.DEFAULT;
        if (other == this)
            return;
        mId = other.getId();
        setOrigin(other.getOrigin());
        setDestination(other.getDestination());
        setScheduledTime(other.getScheduledTime());
        setRider(other.getRider());
    }
}
