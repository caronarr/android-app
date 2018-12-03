package org.caronar.app.dao.rest;

import android.util.Pair;

import org.caronar.app.dao.LocationManager;
import org.caronar.app.location.GeoCoordinate;
import org.caronar.app.model.Location;

import java.util.concurrent.CompletableFuture;

public class RestLocationManager extends LocationManager {
    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> searchNearby(GeoCoordinate coordinate, double acceptableDistance) {
        return null;
    }

    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> findByName(String name) {
        return null;
    }

    @Override public Location create() {
        return null;
    }

    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> getById(long id) {
        return null;
    }

    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> save(Location model) {
        return null;
    }

    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> delete(Location model) {
        return null;
    }
}
