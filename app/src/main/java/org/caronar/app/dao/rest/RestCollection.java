package org.caronar.app.dao.rest;

import android.net.Uri;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.dao.BaseManager;
import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.dao.rest.request.ModelRequest;
import org.caronar.app.model.BaseModel;

import java.lang.reflect.InvocationTargetException;

class RestCollection<Model extends BaseModel> implements BaseManager<Model> {
    final RequestQueue mRequestQueue;
    final Gson mGson;
    final Uri mUri;
    final Class<Model> mModelClass;
    final Model mDefaultModel;

    public RestCollection(RequestQueue requestQueue, Gson gson, Uri collectionUri, Class<Model> modelClass, Model defaultModel) {
        mRequestQueue = requestQueue;
        mGson = gson;
        mUri = collectionUri;
        mModelClass = modelClass;
        mDefaultModel = defaultModel;
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
        mRequestQueue.add(new ModelRequest<Model>(
                mGson,
                mModelClass,
                Request.Method.GET,
                Uri.withAppendedPath(mUri, Long.toString(id)).toString(),
                /*body*/ null,
                response
        ));
        return response;
    }

    @Override public VolleyCompletableFuture<Model> save(Model model) {
        VolleyCompletableFuture<Model> response = new VolleyCompletableFuture<>(mDefaultModel);
        int method = model.getId() == 0 ? Request.Method.POST : Request.Method.PUT;
        Uri.Builder requestBuilder = mUri.buildUpon();
        if (model.getId() > 0)
            requestBuilder.appendPath(Long.toString(model.getId()));
        String uri = requestBuilder.build().toString();
        mRequestQueue.add(new ModelRequest<Model>(
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
        mRequestQueue.add(new ModelRequest<Model>(
                mGson,
                mModelClass,
                Request.Method.DELETE,
                Uri.withAppendedPath(mUri, Long.toString(model.getId())).toString(),
                mGson.toJson(model),
                response
        ));
        return response;
    }
}
