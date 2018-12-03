package org.caronar.app.dao.rest.request;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.caronar.app.dao.rest.future.VolleyCompletableFutureList;
import org.caronar.app.model.BaseModel;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class ListRequest<GsonModel extends BaseModel> extends JsonRequest<List<GsonModel>> {

    private final Gson mGson;
    private final Type mClass;

    public ListRequest(Gson gson,
                       Class<GsonModel> clazz,
                       int method,
                       String url,
                       @Nullable String requestBody,
                       VolleyCompletableFutureList<GsonModel> futureModelList) {
        super(method, url, requestBody, futureModelList, futureModelList);
        mGson = gson;
        mClass = new TypeToken<Collection<GsonModel>>() {}.getType();
    }

    @Override protected Response<List<GsonModel>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(mGson.fromJson(json, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonParseException e) {
            return Response.error(new VolleyError(e));
        }
    }
}
