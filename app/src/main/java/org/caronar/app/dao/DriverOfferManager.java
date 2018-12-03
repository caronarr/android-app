package org.caronar.app.dao;

import android.content.Context;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.caronar.app.Caronar;
import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.RiderOffer;
import org.caronar.app.model.User;

public abstract class DriverOfferManager implements BaseManager<DriverOffer> {
    public abstract CompletableFuture<List<DriverOffer>> listBestOffers
            (RiderOffer riderOffer, double acceptableDistance, Duration acceptableDelay);
    public abstract CompletableFuture<Boolean> broadcastOffer(DriverOffer offer);
    public abstract CompletableFuture<List<DriverOffer>> listOffersByDriver(User driver);

    public static DriverOfferManager getInstance(Context context) {
        return ((Caronar) context.getApplicationContext())
                .getSingletons().getDriverOfferManager();
    }
}
