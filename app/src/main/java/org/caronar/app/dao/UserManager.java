package org.caronar.app.dao;

import android.content.Context;
import android.util.Pair;

import org.caronar.app.Caronar;
import org.caronar.app.model.User;

import java.util.concurrent.CompletableFuture;

public abstract class UserManager implements BaseManager<User> {
    public abstract CompletableFuture<Pair<Boolean, ? extends Throwable>> sendPhoneVerificationChallenge(User user);
    public abstract CompletableFuture<Pair<String, ? extends Throwable>> solvePhoneVerificationChallenge(User user, String code);
    public abstract CompletableFuture<Pair<User, ? extends Throwable>> findByPhone(String phone);

    public static UserManager getInstance(Context context) {
        return ((Caronar) context.getApplicationContext())
                .getSingletons().getUserManager();
    }
}
