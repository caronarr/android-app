package org.caronar.app.dao;

import android.content.Context;
import android.util.Pair;

import org.caronar.app.Caronar;
import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.RiderOffer;
import org.caronar.app.model.User;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class RiderOfferManager implements BaseManager<RiderOffer> {
    public abstract CompletableFuture<Pair<List<RiderOffer>, ? extends Throwable>> listBestOffers
            (DriverOffer driverOffer, double acceptableDistance, Duration acceptableDelay);
    public abstract CompletableFuture<Pair<Boolean, ? extends Throwable>> broadcastOffer(RiderOffer offer);
    public abstract CompletableFuture<Pair<List<RiderOffer>, ? extends Throwable>> listOffersByRider(User rider);

    public static RiderOfferManager getInstance(Context context) {
        return ((Caronar) context.getApplicationContext())
                .getSingletons().getRiderOfferManager();
    }
}
