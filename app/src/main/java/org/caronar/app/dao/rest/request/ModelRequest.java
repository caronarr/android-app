package org.caronar.app.dao.rest.request;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.model.BaseModel;

import java.io.UnsupportedEncodingException;

public class ModelRequest<GsonModel extends BaseModel> extends JsonRequest<GsonModel> {

    private final Gson mGson;
    private final Class<? extends GsonModel> mClass;

    public ModelRequest(Gson gson,
                        Class<? extends GsonModel> clazz,
                        int method, String url,
                        @Nullable String body,
                        VolleyCompletableFuture<GsonModel> futureResponse) {
        super(method, url, body, futureResponse, futureResponse);
        mGson = gson;
        mClass = clazz;
    }

    @Override protected Response<GsonModel> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(mGson.fromJson(json, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonParseException e) {
            return Response.error(new VolleyError(e));
        }
    }
}
