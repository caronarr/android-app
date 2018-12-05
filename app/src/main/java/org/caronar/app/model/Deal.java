package org.caronar.app.model;

import com.google.gson.annotations.SerializedName;

public final class Deal implements BaseModel{

    @SerializedName("id")
    private Long mId;

    @SerializedName("driverOffer")
    private DriverOffer mDriverOffer;

    @SerializedName("riderOffer")
    private RiderOffer mRiderOffer;

    @SerializedName("agreedTip")
    private double mAgreedTip;

    public Deal() {
        this(0, new DriverOffer(), new RiderOffer(), 0);
    }

    private Deal(long id, DriverOffer driverOffer, RiderOffer riderOffer, double agreedTip) {
        mId = id;
        mDriverOffer = driverOffer;
        mRiderOffer = riderOffer;
        mAgreedTip = agreedTip;
    }

    @Override public long getId() {
        return mId != null ? mId : 0;
    }

    public void setId(long id) {
        mId = id;
    }

    public DriverOffer getDriverOffer() {
        return mDriverOffer;
    }

    public void setDriverOffer(DriverOffer driverOffer) {
        mDriverOffer = driverOffer;
    }

    public RiderOffer getRiderOffer() {
        return mRiderOffer;
    }

    public void setRiderOffer(RiderOffer riderOffer) {
        mRiderOffer = riderOffer;
    }

    public double getAgreedTip() {
        return mAgreedTip;
    }

    public void setAgreedTip(double agreedTip) {
        mAgreedTip = agreedTip;
    }

    public static final Deal DEFAULT = new Deal(0, DriverOffer.DEFAULT, RiderOffer.DEFAULT, 0);
}
