package org.caronar.app.dao;

import android.content.Context;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.caronar.app.Caronar;
import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.RiderOffer;
import org.caronar.app.model.User;

public abstract class RiderOfferManager implements BaseManager<RiderOffer> {
    public abstract CompletableFuture<List<RiderOffer>> listBestOffers
            (DriverOffer driverOffer, double acceptableDistance, Duration acceptableDelay);
    public abstract CompletableFuture<Boolean> broadcastOffer(RiderOffer offer);
    public abstract CompletableFuture<List<RiderOffer>> listOffersByRider(User rider);

    public static RiderOfferManager getInstance(Context context) {
        return ((Caronar) context.getApplicationContext())
                .getSingletons().getRiderOfferManager();
    }
}
