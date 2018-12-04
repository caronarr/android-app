package org.caronar.app.dao.rest;

import android.net.Uri;
import android.util.Pair;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.BuildConfig;
import org.caronar.app.dao.DealManager;
import org.caronar.app.model.Deal;

import java.util.concurrent.CompletableFuture;

public class RestDealManager extends DealManager {

    private final RestCollection<Deal> mCollection;

    public RestDealManager(RequestQueue requestQueue, Gson gson) {
        Uri mManagedUri = Uri.parse(BuildConfig.DATA_URL).buildUpon().appendPath("deal").build();
        mCollection = new RestCollection<>
                (requestQueue, gson, mManagedUri, Deal.class, Deal.DEFAULT);
    }

    @Override public Deal create() {
        return mCollection.create();
    }

    @Override public CompletableFuture<Pair<Deal, ? extends Throwable>> getById(long id) {
        return mCollection.getById(id);
    }

    @Override public CompletableFuture<Pair<Deal, ? extends Throwable>> save(Deal model) {
        return mCollection.save(model);
    }

    @Override public CompletableFuture<Pair<Deal, ? extends Throwable>> delete(Deal model) {
        return mCollection.delete(model);
    }
}
