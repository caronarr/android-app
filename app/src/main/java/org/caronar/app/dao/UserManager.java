package org.caronar.app.dao;

import android.content.Context;
import android.util.Pair;

import java.util.concurrent.CompletableFuture;

import org.caronar.app.Caronar;
import org.caronar.app.model.User;

public abstract class UserManager implements BaseManager<User> {
    public abstract CompletableFuture<Boolean> sendPhoneVerificationChallenge(User user);
    public abstract CompletableFuture<Pair<String, Boolean>> solvePhoneVerificationChallenge(User user, String code);
    public abstract CompletableFuture<Pair<User, Boolean>> findByPhone(String phone);

    public static UserManager getInstance(Context context) {
        return ((Caronar) context.getApplicationContext())
                .getSingletons().getUserManager();
    }
}
