package org.caronar.app.dao.rest;

import android.content.Context;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.Caronar;
import org.caronar.app.dao.RiderOfferManager;
import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.dao.rest.request.BooleanRequest;
import org.caronar.app.model.DriverOffer;
import org.caronar.app.model.RiderOffer;
import org.caronar.app.model.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RestRiderOfferManager extends RiderOfferManager {
    private final RestCollection<RiderOffer> mCollection;
    private final RequestQueue mRequestQueue;
    private final String mCollectionName = "rider_offers";
    private Uri mUri;
    private final Context mContext;

    public RestRiderOfferManager(Context context, RequestQueue requestQueue, Gson gson) {
        mContext = context;
        mRequestQueue = requestQueue;
        mCollection = new RestCollection<>
                (context, requestQueue, gson, mCollectionName, RiderOffer.class, RiderOffer.DEFAULT);
    }

    @Override
    public CompletableFuture<Pair<List<RiderOffer>, ? extends Throwable>> listBestOffers(
            DriverOffer driverOffer, double acceptableDistance, long acceptableDelay_sec) {
        //TODO: filter by contacts
        Map<String, String> filter = new ArrayMap<>();
        filter.put("origin-latitude", Double.toString(driverOffer.getOrigin().getLatitude()));
        filter.put("origin-longitude", Double.toString(driverOffer.getOrigin().getLongitude()));
        filter.put("destination-latitude", Double.toString(driverOffer.getDestination().getLatitude()));
        filter.put("destination-longitude", Double.toString(driverOffer.getDestination().getLongitude()));
        filter.put("origin-time", Long.toString(driverOffer.getScheduledTime().getTime()));
        filter.put("acceptable-delay", Long.toString(acceptableDelay_sec));

        return mCollection.filter(filter);
    }

    @Override
    public CompletableFuture<Pair<Boolean, ? extends Throwable>> broadcastOffer(RiderOffer offer) {
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
    public CompletableFuture<Pair<List<RiderOffer>, ? extends Throwable>> listOffersByRider(User rider) {
        Map<String, String> filter = new ArrayMap<>();
        filter.put("driver", Long.toString(rider.getId()));
        return mCollection.filter(filter);
    }

    @Override
    public RiderOffer create() {
        return mCollection.create();
    }

    @Override
    public CompletableFuture<Pair<RiderOffer, ? extends Throwable>> getById(long id) {
        return mCollection.getById(id);
    }

    @Override
    public CompletableFuture<Pair<RiderOffer, ? extends Throwable>> save(RiderOffer model) {
        return mCollection.save(model);
    }

    @Override
    public CompletableFuture<Pair<RiderOffer, ? extends Throwable>> delete(RiderOffer model) {
        return mCollection.delete(model);
    }

    private Uri getUri() {
        if (mUri == null)
            mUri = ((Caronar) mContext.getApplicationContext()).getBaseRestUri()
                    .buildUpon().appendEncodedPath(mCollectionName).build();
        return mUri;
    }
}
