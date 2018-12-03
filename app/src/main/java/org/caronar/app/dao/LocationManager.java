package org.caronar.app.dao;

import android.content.Context;
import android.util.Pair;

import org.caronar.app.Caronar;
import org.caronar.app.location.GeoCoordinate;
import org.caronar.app.model.Location;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class LocationManager implements BaseManager<Location> {
    public abstract CompletableFuture<Pair<List<Location>, ? extends Throwable>> searchNearby
            (GeoCoordinate coordinate, double acceptableDistance);
    public abstract CompletableFuture<Pair<Location, ? extends Throwable>> findByName(String name);

    public static LocationManager getInstance(Context context) {
        return ((Caronar)context.getApplicationContext())
                .getSingletons().getLocationManager();
    }
}
