package org.caronar.app.dao.rest;

import android.net.Uri;
import android.util.ArrayMap;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.BuildConfig;
import org.caronar.app.dao.LocationManager;
import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.dao.rest.future.VolleyCompletableFutureList;
import org.caronar.app.dao.rest.request.ModelRequest;
import org.caronar.app.location.GeoCoordinate;
import org.caronar.app.model.Location;
import org.caronar.app.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RestLocationManager extends LocationManager {

    private final RestCollection<Location> mCollection;

    public RestLocationManager(RequestQueue requestQueue, Gson gson) {
        Uri mManagedUri = Uri.parse(BuildConfig.DATA_URL).buildUpon().appendPath("location").build();
        mCollection = new RestCollection<>
                (requestQueue, gson, mManagedUri, Location.class, Location.DEFAULT);
    }

    @Override public VolleyCompletableFutureList<Location> searchNearby(GeoCoordinate coordinate,
                                                                        double acceptableDistance) {
        Map<String, String> filter = new ArrayMap<>();
        filter.put("latitude", Double.toString(coordinate.getLatitude()));
        filter.put("longitude", Double.toString(coordinate.getLongitude()));
        filter.put("acceptable-distance", Double.toString(acceptableDistance));
        return mCollection.filter(filter);
    }

    @Override public VolleyCompletableFuture<Location> findByName(String name) {
        Map<String, String> filter = new ArrayMap<>();
        filter.put("name", name);
        return mCollection.filterOne(filter);
    }

    @Override public Location create() {
        return mCollection.create();
    }

    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> getById(long id) {
        return mCollection.getById(id);
    }

    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> save(Location model) {
        return mCollection.save(model);
    }

    @Override public CompletableFuture<Pair<Location, ? extends Throwable>> delete(Location model) {
        return mCollection.delete(model);
    }
}
