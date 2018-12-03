package org.caronar.app.dao.rest.future;

import android.util.Pair;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.caronar.app.model.BaseModel;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureModel<T extends BaseModel> extends CompletableFuture<Pair<T, Boolean>> implements Response.ErrorListener, Response.Listener<T> {

    private final T mDefaultValue;

    public CompletableFutureModel(T defaultValue) {
        mDefaultValue = defaultValue;
    }

    @Override public void onErrorResponse(VolleyError error) {
        complete(new Pair<>(mDefaultValue, false));
    }

    @Override public void onResponse(T response) {
        complete(new Pair<>(response, true));
    }
}
