package org.caronar.app.dao.rest;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.Caronar;
import org.caronar.app.dao.BaseManager;
import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.dao.rest.future.VolleyCompletableFutureList;
import org.caronar.app.dao.rest.request.ListRequest;
import org.caronar.app.dao.rest.request.ModelRequest;
import org.caronar.app.model.BaseModel;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class RestCollection<Model extends BaseModel> implements BaseManager<Model> {
    private final Context mContext;
    private final RequestQueue mRequestQueue;
    private final Gson mGson;
    private final String mCollectionName;
    private Uri mUri;
    private final Class<Model> mModelClass;
    private final Model mDefaultModel;

    RestCollection(Context context, RequestQueue requestQueue, Gson gson, String collectionName, Class<Model> modelClass, Model defaultModel) {
        mContext = context;
        mRequestQueue = requestQueue;
        mGson = gson;
        mModelClass = modelClass;
        mDefaultModel = defaultModel;
        mCollectionName = collectionName;
    }

    @Override public Model create() {
        try {
            return mModelClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return mDefaultModel;
        }
    }

    @Override public VolleyCompletableFuture<Model> getById(long id) {
        VolleyCompletableFuture<Model> response = new VolleyCompletableFuture<>(mDefaultModel);
        mRequestQueue.add(new ModelRequest<>(
                mGson,
                mModelClass,
                Request.Method.GET,
                Uri.withAppendedPath(getUri(), Long.toString(id)).toString(),
                /*body*/ null,
                response
        ));
        return response;
    }

    @Override public VolleyCompletableFuture<Model> save(Model model) {
        VolleyCompletableFuture<Model> response = new VolleyCompletableFuture<>(mDefaultModel);
        int method = model.getId() == 0 ? Request.Method.POST : Request.Method.PUT;
        Uri.Builder requestBuilder = getUri().buildUpon();
        if (model.getId() > 0)
            requestBuilder.appendPath(Long.toString(model.getId()));
        String uri = requestBuilder.build().toString();
        mRequestQueue.add(new ModelRequest<>(
                mGson,
                mModelClass,
                method,
                uri,
                mGson.toJson(model),
                response
        ));
        return response;
    }

    @Override public VolleyCompletableFuture<Model> delete(Model model) {
        VolleyCompletableFuture<Model> response = new VolleyCompletableFuture<>(mDefaultModel);
        if (model.getId() == 0) {
            response.complete(new Pair<>(mDefaultModel, new Exception("model not found")));
            return response;
        }
        mRequestQueue.add(new ModelRequest<>(
                mGson,
                mModelClass,
                Request.Method.DELETE,
                Uri.withAppendedPath(getUri(), Long.toString(model.getId())).toString(),
                mGson.toJson(model),
                response
        ));
        return response;
    }

    CompletableFuture<Pair<Model, ? extends Throwable>> filterOne(Map<String, String> filter) {
        Uri.Builder builder = getUri().buildUpon();

        for (Map.Entry<String, String> entry : filter.entrySet())
            builder.appendQueryParameter(entry.getKey(), entry.getValue());

        VolleyCompletableFutureList<Model> response = new VolleyCompletableFutureList<>(mDefaultModel);
        mRequestQueue.add(new ListRequest<>(
                mGson,
                mModelClass,
                Request.Method.GET,
                builder.build().toString(),
                null,
                response
        ));
        return response.thenApply(resp -> {
            Model m = mDefaultModel;
            if (resp.second != null)
                return new Pair<>(m, resp.second);
            if (resp.first.size() < 1)
                return new Pair<>(m, new ArrayIndexOutOfBoundsException("Empty list"));
            m = resp.first.get(0);
            return new Pair<>(m, null);
        });
    }

    VolleyCompletableFutureList<Model> filter(Map<String, String> filter) {
        Uri.Builder builder = getUri().buildUpon();

        for (Map.Entry<String, String> entry : filter.entrySet())
            builder.appendQueryParameter(entry.getKey(), entry.getValue());

        VolleyCompletableFutureList<Model> response = new VolleyCompletableFutureList<>(mDefaultModel);
        mRequestQueue.add(new ListRequest<>(
                mGson,
                mModelClass,
                Request.Method.GET,
                builder.build().toString(),
                null,
                response
        ));
        return response;
    }

    private Uri getUri() {
        if (mUri == null)
            mUri = ((Caronar) mContext.getApplicationContext()).getBaseRestUri()
                    .buildUpon().appendEncodedPath(mCollectionName).build();
        return mUri;
    }
}
