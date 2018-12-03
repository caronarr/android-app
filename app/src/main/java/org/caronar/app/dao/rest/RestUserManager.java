package org.caronar.app.dao.rest;

import android.net.Uri;
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

import java.util.concurrent.CompletableFuture;

public class RestUserManager extends UserManager {

    private final RestCollection<User> mCollection;

    public RestUserManager(RequestQueue requestQueue, Gson gson) {
        Uri mManagedUri = Uri.parse(BuildConfig.DATA_URL).buildUpon().appendPath("user").build();
        mCollection = new RestCollection<User>
                (requestQueue, gson, mManagedUri, User.class, User.DEFAULT);
    }

    @Override public VolleyCompletableFuture<Boolean> sendPhoneVerificationChallenge(User user) {
        String phoneVerificationChallengeUrl =
                mCollection.mUri.buildUpon()
                        .appendPath(Long.toString(user.getId()))
                        .appendPath("verify-phone-challenge")
                        .appendPath(user.getPhone())
                        .build().toString();
        VolleyCompletableFuture<Boolean> response = new VolleyCompletableFuture<>(false);
        mCollection.mRequestQueue.add(new BooleanRequest(
                Request.Method.GET,
                phoneVerificationChallengeUrl,
                /*body*/ null,
                response));
        return response;
    }

    @Override public VolleyCompletableFuture<String> solvePhoneVerificationChallenge(User user, String code) {
        String phoneVerificationSolveUrl =
                mCollection.mUri.buildUpon()
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
        mCollection.mRequestQueue.add(req);

        return response;
    }

    @Override public VolleyCompletableFuture<User> findByPhone(String phone) {
        String getByPhoneUrl =
                mCollection.mUri.buildUpon()
                        .appendQueryParameter("phone", phone)
                        .build().toString();
        VolleyCompletableFuture<User> response = new VolleyCompletableFuture<>(User.DEFAULT);
        mCollection.mRequestQueue.add(new ModelRequest<User>(
                mCollection.mGson,
                mCollection.mModelClass,
                Request.Method.GET,
                getByPhoneUrl,
                null,
                response
        ));
        return response;
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
