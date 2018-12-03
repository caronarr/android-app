package org.caronar.app.dao;

import android.content.Context;
import android.util.Pair;

import org.caronar.app.Caronar;
import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.RiderOffer;
import org.caronar.app.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class DriverOfferManager implements BaseManager<DriverOffer> {
    public abstract CompletableFuture<Pair<List<DriverOffer>, ? extends Throwable>> listBestOffers
            (RiderOffer riderOffer, double acceptableDistance, long acceptableDelay_sec);
    public abstract CompletableFuture<Pair<Boolean, ? extends Throwable>> broadcastOffer(DriverOffer offer);
    public abstract CompletableFuture<Pair<List<DriverOffer>, ? extends Throwable>> listOffersByDriver(User driver);

    public static DriverOfferManager getInstance(Context context) {
        return ((Caronar) context.getApplicationContext())
                .getSingletons().getDriverOfferManager();
    }
}
