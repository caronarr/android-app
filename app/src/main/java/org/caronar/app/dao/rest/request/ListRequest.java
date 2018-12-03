package org.caronar.app.dao.rest.request;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import org.caronar.app.model.BaseModel;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ListRequest<GsonModel extends BaseModel> extends JsonRequest<List<GsonModel>> {

    private final Gson mGson;
    /** A concrete class that extends GsonModel. Used by Gson to instantiate new objects */
    private final Class<? extends GsonModel> mClass;

    public ListRequest(Gson gson,
                       Class<? extends GsonModel> clazz,
                       int method,
                       String url,
                       @Nullable String requestBody,
                       Response.Listener<List<GsonModel>> listener,
                       @Nullable Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        mGson = gson;
        mClass = clazz;
    }

    @Override protected Response<List<GsonModel>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JsonReader reader = new JsonReader(new StringReader(json));
            List<GsonModel> result = new ArrayList<>();
            reader.beginArray();
            while (reader.hasNext())
                result.add(mGson.fromJson(reader, mClass));
            reader.endArray();
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonParseException e) {
            return Response.error(new VolleyError(e));
        } catch (IOException e) {
            return Response.error(new VolleyError(new JsonParseException("Invalid json", e)));
        }
    }
}
