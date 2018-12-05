package org.caronar.app.dao.rest;

import android.content.Context;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.Caronar;
import org.caronar.app.dao.DriverOfferManager;
import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.dao.rest.request.BooleanRequest;
import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.RiderOffer;
import org.caronar.app.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RestDriverOfferManager extends DriverOfferManager {


    private final RestCollection<DriverOffer> mCollection;
    private final RequestQueue mRequestQueue;
    private final String mCollectionName = "driver_offers";
    private Uri mUri;
    private final Context mContext;

    public RestDriverOfferManager(Context context, RequestQueue requestQueue, Gson gson) {
        mContext = context.getApplicationContext();
        mRequestQueue = requestQueue;
        mCollection = new RestCollection<>
                (context, requestQueue, gson, mCollectionName, DriverOffer.class, DriverOffer.DEFAULT);
    }

    @Override
    public CompletableFuture<Pair<List<DriverOffer>, ? extends Throwable>> listBestOffers(
            RiderOffer riderOffer, double acceptableDistance, long acceptableDelay_sec) {
        //TODO: filter by contacts
        Map<String, String> filter = new ArrayMap<>();
        filter.put("origin-latitude", Double.toString(riderOffer.getOrigin().getLatitude()));
        filter.put("origin-longitude", Double.toString(riderOffer.getOrigin().getLongitude()));
        filter.put("destination-latitude", Double.toString(riderOffer.getDestination().getLatitude()));
        filter.put("destination-longitude", Double.toString(riderOffer.getDestination().getLongitude()));
        filter.put("origin-time", Long.toString(riderOffer.getScheduledTime().getTime()));
        filter.put("acceptable-delay", Long.toString(acceptableDelay_sec));

        return mCollection.filter(filter);
    }

    @Override
    public CompletableFuture<Pair<Boolean, ? extends Throwable>> broadcastOffer(DriverOffer offer) {
        String phoneVerificationChallengeUrl =
                getUri().buildUpon()
                        .appendPath(Long.toString(offer.getId()))
                        .appendPath("broadcast")
                        .build().toString();
        VolleyCompletableFuture<Boolean> response = new VolleyCompletableFuture<>(false);
        mRequestQueue.add(new BooleanRequest(
                Request.Method.GET,
                phoneVerificationChallengeUrl,
                /*body*/ null,
                response));
        return response;
    }

    @Override
    public CompletableFuture<Pair<List<DriverOffer>, ? extends Throwable>> listOffersByDriver(User driver) {
        Map<String, String> filter = new ArrayMap<>();
        filter.put("driver", Long.toString(driver.getId()));
        return mCollection.filter(filter);
    }

    @Override
    public DriverOffer create() {
        return mCollection.create();
    }

    @Override
    public CompletableFuture<Pair<DriverOffer, ? extends Throwable>> getById(long id) {
        return mCollection.getById(id);
    }

    @Override
    public CompletableFuture<Pair<DriverOffer, ? extends Throwable>> save(DriverOffer model) {
        return mCollection.save(model);
    }

    @Override
    public CompletableFuture<Pair<DriverOffer, ? extends Throwable>> delete(DriverOffer model) {
        return mCollection.delete(model);
    }

    private Uri getUri() {
        if (mUri == null)
            mUri = ((Caronar) mContext.getApplicationContext()).getBaseRestUri()
                    .buildUpon().appendEncodedPath(mCollectionName).build();
        return mUri;
    }
}
