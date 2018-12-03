package org.caronar.app.dao.rest.request;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.caronar.app.dao.rest.future.VolleyCompletableFuture;

public class BooleanRequest extends JsonRequest<Boolean> {

    public BooleanRequest(int method, String url, @Nullable String requestBody, VolleyCompletableFuture<Boolean> futureResponse) {
        super(method, url, requestBody, futureResponse, futureResponse);
    }

    @Override protected Response<Boolean> parseNetworkResponse(NetworkResponse response) {
        return Response.success(true, HttpHeaderParser.parseCacheHeaders(response));
    }
}
