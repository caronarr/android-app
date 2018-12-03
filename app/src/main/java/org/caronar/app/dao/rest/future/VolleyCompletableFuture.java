package org.caronar.app.dao.rest.future;

import android.util.Pair;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.concurrent.CompletableFuture;

public class VolleyCompletableFuture<T> extends CompletableFuture<Pair<T, ? extends Throwable>> implements Response.ErrorListener, Response.Listener<T> {
    private final T mDefaultValue;

    public VolleyCompletableFuture(T defaultValue) {
        mDefaultValue = defaultValue;
    }

    @Override public void onErrorResponse(VolleyError error) {
        complete(new Pair<>(mDefaultValue, error));
    }

    @Override public void onResponse(T response) {
        complete(new Pair<>(response == null ? mDefaultValue : response, null));
    }
}
