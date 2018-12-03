package org.caronar.app.dao.rest;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.model.Deal;
import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.RiderOffer;

final class ManagedDeal extends ManagedModel implements Deal, Deal.Editor {
    @SerializedName("driverOffer")
    private ManagedDriverOffer mDriverOffer = new ManagedDriverOffer();

    @SerializedName("riderOffer")
    private ManagedRiderOffer mRiderOffer = new ManagedRiderOffer();

    @SerializedName("valueAgreed")
    private double mAgreedTip = 0;

    @Override public DriverOffer getDriverOffer() {
        return mDriverOffer;
    }

    @Override public RiderOffer getRiderOffer() {
        return mRiderOffer;
    }

    @Override public double getAgreedTip() {
        return mAgreedTip;
    }

    @Override public Editor edit() {
        return this;
    }

    @Override public Editor setAgreedTip(double tip) {
        mAgreedTip = tip;
        return this;
    }

    @Override public Editor setDriverOffer(DriverOffer driverOffer) {
        mDriverOffer.assign(driverOffer);
        return this;
    }

    @Override public Editor setRiderOffer(RiderOffer riderOffer) {
        mRiderOffer.assign(riderOffer);
        return this;
    }

    @Override public Deal finalizeEdition() {
        return this;
    }
}
