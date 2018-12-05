package org.caronar.app.dao.rest;

import android.content.Context;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Pair;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.caronar.app.Caronar;
import org.caronar.app.dao.UserManager;
import org.caronar.app.dao.rest.future.VolleyCompletableFuture;
import org.caronar.app.dao.rest.request.BooleanRequest;
import org.caronar.app.model.User;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RestUserManager extends UserManager {

    private final RestCollection<User> mCollection;
    private final RequestQueue mRequestQueue;
    private final String mCollectionName = "users";
    private Uri mUri;
    private final Context mContext;

    public RestUserManager(Context context, RequestQueue requestQueue, Gson gson) {
        mContext = context;
        mRequestQueue = requestQueue;
        mCollection = new RestCollection<>
                (context, requestQueue, gson, mCollectionName, User.class, User.DEFAULT);
    }

    @Override public VolleyCompletableFuture<Boolean> sendPhoneVerificationChallenge(User user) {
        String phoneVerificationChallengeUrl =
                getUri().buildUpon()
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
                getUri().buildUpon()
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

    @Override public CompletableFuture<Pair<User, ? extends Throwable>> findByPhone(String phone) {
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

    private Uri getUri() {
        if (mUri == null)
            mUri = ((Caronar) mContext.getApplicationContext()).getBaseRestUri()
                    .buildUpon().appendEncodedPath(mCollectionName).build();
        return mUri;
    }
}
