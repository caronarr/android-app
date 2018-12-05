package org.caronar.app.model;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.ride.Schedule;

import java.util.Date;

public final class DriverOffer implements BaseModel, Schedule {

    @SerializedName("id")
    private Long mId;

    @SerializedName("origin")
    private Location mOrigin;

    @SerializedName("destination")
    private Location mDestination;

    @SerializedName("scheduledTime")
    private Date mScheduledTime;

    @SerializedName("requestedTip")
    private double mRequestedTip;

    @SerializedName("driver")
    private User mDriver;

    public DriverOffer() {
        this(0, new Location(), new Location(), Schedule.DEFAULT_SCHEDULED_TIME,
                0, new User());
    }

    private DriverOffer(long id, Location origin, Location destination, Date scheduledTime,
                       double requestedTip, User driver) {
        mId = id;
        mOrigin = origin;
        mDestination = destination;
        mScheduledTime = scheduledTime;
        mRequestedTip = requestedTip;
        mDriver = driver;
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

    public double getRequestedTip() {
        return mRequestedTip;
    }

    public void setRequestedTip(double requestedTip) {
        mRequestedTip = requestedTip;
    }

    public User getDriver() {
        return mDriver;
    }

    public void setDriver(User driver) {
        mDriver = driver;
    }

    public static final DriverOffer DEFAULT = new DriverOffer(
            0,
            Location.DEFAULT,
            Location.DEFAULT,
            Schedule.DEFAULT_SCHEDULED_TIME,
            0,
            User.DEFAULT
    );
}
