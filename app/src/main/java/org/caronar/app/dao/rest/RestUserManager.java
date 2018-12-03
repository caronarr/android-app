package org.caronar.app.dao.rest;

import android.net.Uri;
import android.util.ArrayMap;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.caronar.app.BuildConfig;
import org.caronar.app.dao.UserManager;
import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.dao.rest.request.BooleanRequest;
import org.caronar.app.dao.rest.request.ModelRequest;
import org.caronar.app.model.User;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RestUserManager extends UserManager {

    private final RestCollection<User> mCollection;
    private final RequestQueue mRequestQueue;
    private final Uri mUri;

    public RestUserManager(RequestQueue requestQueue, Gson gson) {
        mRequestQueue = requestQueue;
        mUri = Uri.parse(BuildConfig.DATA_URL).buildUpon().appendPath("user").build();
        mCollection = new RestCollection<>
                (requestQueue, gson, mUri, User.class, User.DEFAULT);
    }

    @Override public VolleyCompletableFuture<Boolean> sendPhoneVerificationChallenge(User user) {
        String phoneVerificationChallengeUrl =
                mUri.buildUpon()
                    .appendPath(Long.toString(user.getId()))
                    .appendPath("verify-phone-challenge")
                    .appendPath(user.getPhone())
                    .build().toString();
        VolleyCompletableFuture<Boolean> response = new VolleyCompletableFuture<>(false);
        mRequestQueue.add(new BooleanRequest(
                Request.Method.GET,
                phoneVerificationChallengeUrl,
                /*body*/ null,
                response));
        return response;
    }

    @Override public VolleyCompletableFuture<String> solvePhoneVerificationChallenge(User user, String code) {
        String phoneVerificationSolveUrl =
                mUri.buildUpon()
                        .appendPath(Long.toString(user.getId()))
                        .appendPath("verify-phone-solve")
                        .appendPath(user.getPhone())
                        .build().toString();
        VolleyCompletableFuture<String> response = new VolleyCompletableFuture<>("");
        StringRequest req = new StringRequest(
                Request.Method.POST,
                phoneVerificationSolveUrl,
                response, response) {
            @Override public byte[] getBody() {
                return code.getBytes();
            }
        };
        mRequestQueue.add(req);

        return response;
    }

    @Override public VolleyCompletableFuture<User> findByPhone(String phone) {
        Map<String, String> filter = new ArrayMap<>();
        filter.put("phone", phone);
        return mCollection.filterOne(filter);
    }

    @Override public User create() {
        return mCollection.create();
    }

    @Override public CompletableFuture<Pair<User, ? extends Throwable>> getById(long id) {
        return mCollection.getById(id);
    }

    @Override public CompletableFuture<Pair<User, ? extends Throwable>> save(User model) {
        return mCollection.save(model);
    }

    @Override public CompletableFuture<Pair<User, ? extends Throwable>> delete(User model) {
        return mCollection.delete(model);
    }
}
