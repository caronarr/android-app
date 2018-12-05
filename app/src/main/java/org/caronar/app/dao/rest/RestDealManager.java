package org.caronar.app.dao.rest;

import android.content.Context;
import android.util.Pair;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.dao.DealManager;
import org.caronar.app.model.Deal;

import java.util.concurrent.CompletableFuture;

public class RestDealManager extends DealManager {

    private final RestCollection<Deal> mCollection;

    public RestDealManager(Context context, RequestQueue requestQueue, Gson gson) {
        mCollection = new RestCollection<>
                (context, requestQueue, gson, "deals", Deal.class, Deal.DEFAULT);
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
