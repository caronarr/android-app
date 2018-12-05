package org.caronar.app.model;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.ride.Schedule;

import java.util.Date;

public final class RiderOffer implements BaseModel, Schedule {

    @SerializedName("id")
    private Long mId;

    @SerializedName("origin")
    private Location mOrigin;

    @SerializedName("destination")
    private Location mDestination;

    @SerializedName("scheduledTime")
    private Date mScheduledTime;

    @SerializedName("offeredTip")
    private double mOfferedTip;

    @SerializedName("rider")
    private User mRider;

    public RiderOffer() {
        this(0, new Location(), new Location(), Schedule.DEFAULT_SCHEDULED_TIME,
                0, new User());
    }

    private RiderOffer(long id, Location origin, Location destination, Date scheduledTime,
                       double offeredTip, User rider) {
        mId = id;
        mOrigin = origin;
        mDestination = destination;
        mScheduledTime = scheduledTime;
        mOfferedTip = offeredTip;
        mRider = rider;
    }

    @Override public long getId() {
        return mId != null ? mId : 0;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override public Location getOrigin() {
        return mOrigin;
    }

    public void setOrigin(Location origin) {
        mOrigin = origin;
    }

    @Override public Location getDestination() {
        return mDestination;
    }

    public void setDestination(Location destination) {
        mDestination = destination;
    }

    @Override public Date getScheduledTime() {
        return mScheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        mScheduledTime = scheduledTime;
    }

    public double getOfferedTip() {
        return mOfferedTip;
    }

    public void setOfferedTip(double offeredTip) {
        mOfferedTip = offeredTip;
    }

    public User getRider() {
        return mRider;
    }

    public void setRider(User rider) {
        mRider = rider;
    }

    public static final RiderOffer DEFAULT = new RiderOffer(
            0,
            Location.DEFAULT,
            Location.DEFAULT,
            Schedule.DEFAULT_SCHEDULED_TIME,
            0,
            User.DEFAULT);
}
