package org.caronar.app.dao.rest.future;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureBoolean extends CompletableFuture<Boolean> implements Response.Listener<Boolean>, Response.ErrorListener {
    @Override public void onErrorResponse(VolleyError error) {
        complete(false);
    }

    @Override public void onResponse(Boolean response) {
        complete(response);
    }
}
