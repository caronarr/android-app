package org.caronar.app.dao.rest;

import android.net.Uri;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.caronar.app.BuildConfig;
import org.caronar.app.dao.UserManager;
import org.caronar.app.dao.rest.future.CompletableFutureBoolean;
import org.caronar.app.dao.rest.request.BooleanRequest;
import org.caronar.app.model.User;

import java.util.concurrent.CompletableFuture;

public class RestUserManager extends UserManager {

    private final RestCollection<User> mCollection;

    public RestUserManager(RequestQueue requestQueue, Gson gson) {
        Uri mManagedUri = Uri.parse(BuildConfig.DATA_URL).buildUpon().appendPath("user").build();
        mCollection = new RestCollection<>
                (requestQueue, gson, mManagedUri, ManagedUser.class);
    }

    @Override public CompletableFuture<Boolean> sendPhoneVerificationChallenge(User user) {
        String phoneVerificationUrl =
                Uri.withAppendedPath(mCollection.mUri, "verify-phone").toString();
        CompletableFutureBoolean response = new CompletableFutureBoolean();
        mCollection.mRequestQueue.add(new BooleanRequest(
                Request.Method.POST,
                phoneVerificationUrl,
                mCollection.mGson.toJson(user),
                response,
                response));
        return response;
    }

    @Override public CompletableFuture<Pair<String, Boolean>> solvePhoneVerificationChallenge(User user, String code) {
        return null;
    }

    @Override public CompletableFuture<Pair<User, Boolean>> findByPhone(String phone) {
        return null;
    }

    @Override public User create() {
        return null;
    }

    @Override public CompletableFuture<Pair<User, Boolean>> getById(long id) {
        return null;
    }

    @Override public CompletableFuture<Pair<User, Boolean>> save(User model) {
        return null;
    }

    @Override public CompletableFuture<Pair<User, Boolean>> delete(User model) {
        return null;
    }
}
