package org.caronar.app.dao.rest;

import android.net.Uri;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.dao.BaseManager;
import org.caronar.app.dao.rest.future.CompletableFutureModel;
import org.caronar.app.dao.rest.request.ModelRequest;
import org.caronar.app.model.BaseModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

class RestCollection<Model extends BaseModel> implements BaseManager<Model> {
    final RequestQueue mRequestQueue;
    final Gson mGson;
    final Uri mUri;
    private final Class<? extends Model> mModelClass;
    final Model mDefaultModel;

    public RestCollection(RequestQueue requestQueue, Gson gson, Uri collectionUri, Class<? extends Model> modelClass) {
        mRequestQueue = requestQueue;
        mGson = gson;
        mUri = collectionUri;
        mModelClass = modelClass;

        try {
            Field DEFAULT = mModelClass.getField("DEFAULT");
            mDefaultModel = (Model) DEFAULT.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            mDefaultModel = null;
        }
    }

    @Override public Model create() {
        try {
            return mModelClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            return mDefaultModel;
        }
    }

    @Override public CompletableFuture<Pair<Model, Boolean>> getById(long id) {
        CompletableFutureModel<Model> response = new CompletableFutureModel<>(mDefaultModel);
        mRequestQueue.add(new ModelRequest<Model>(
                mGson,
                mModelClass,
                Request.Method.GET,
                Uri.withAppendedPath(mUri, Long.toString(id)).toString(),
                /*body*/ null,
                response,
                response
        ));
        return response;
    }

    @Override public CompletableFuture<Pair<Model, Boolean>> save(Model model) {
        CompletableFutureModel<Model> response = new CompletableFutureModel<>(mDefaultModel);
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
                response,
                response
        ));
        return response;
    }

    @Override public CompletableFuture<Pair<Model, Boolean>> delete(Model model) {
        CompletableFutureModel<Model> response = new CompletableFutureModel<>(mDefaultModel);
        if (model.getId() == 0) {
            response.complete(new Pair<>(mDefaultModel, false));
            return response;
        }
        mRequestQueue.add(new ModelRequest<Model>(
                mGson,
                mModelClass,
                Request.Method.DELETE,
                Uri.withAppendedPath(mUri, Long.toString(model.getId())).toString(),
                mGson.toJson(model),
                response,
                response
        ));
        return response;
    }
}
