package org.caronar.app.dao.rest;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.util.Pair;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.caronar.app.BuildConfig;
import org.caronar.app.Caronar;
import org.caronar.app.model.User;
import org.caronar.app.network.HttpClient;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class RestUserManagerTest {

    static RestUserManager mManager;

    @BeforeClass
    public static void setUp() {
        // Context of the app under test.
        Context ctx = InstrumentationRegistry.getTargetContext();
        RequestQueue queue = HttpClient.getInstance(ctx).getRequestQueue();
        Gson gson = new GsonBuilder().create();
        ((Caronar) ctx.getApplicationContext()).setBaseRestUri(Uri.parse(BuildConfig.TEST_DATA_URL));
        mManager = new RestUserManager(ctx, queue, gson);
    }

    @Test
    public void sendPhoneVerificationChallenge() {
    }

    @Test
    public void solvePhoneVerificationChallenge() {
    }

    @Test
    public void findByPhone() throws ExecutionException, InterruptedException {
        User newUser = mManager.create();
        newUser.setName("David");
        newUser.setPhone("+5544933332222");
        Pair<User, ? extends Throwable> result = mManager.save(newUser).get();
        newUser = result.first;
        result = mManager.findByPhone(newUser.getPhone()).get();
        assertNull(result.second);
        assertEquals(result.first.getId(), newUser.getId());
        assertEquals(result.first.getName(), newUser.getName());
        assertEquals(result.first.getPhone(), newUser.getPhone());
        mManager.delete(newUser).get();
    }

    @Test
    public void create() {
        User newUser = mManager.create();
        assertEquals(newUser.getId(), 0);
    }

    @Test
    public void getById() throws ExecutionException, InterruptedException {
        User newUser = mManager.create();
        newUser.setName("David");
        newUser.setPhone("+5544933332222");
        Pair<User, ? extends Throwable> result = mManager.save(newUser).get();
        newUser = result.first;
        result = mManager.getById(newUser.getId()).get();
        assertNull(result.second);
        assertEquals(result.first.getId(), newUser.getId());
        assertEquals(result.first.getName(), newUser.getName());
        assertEquals(result.first.getPhone(), newUser.getPhone());
        mManager.delete(newUser).get();
    }

    @Test
    public void saveNewUser() throws ExecutionException, InterruptedException {
        User newUser = mManager.create();
        newUser.setName("David");
        newUser.setPhone("+5544933332222");
        Pair<User, ? extends Throwable> result = mManager.save(newUser).get();
        assertNull(result.second);
        assertNotEquals(result.first, 0);
        mManager.delete(result.first).get();
    }

    @Test
    public void delete() throws ExecutionException, InterruptedException {
        User newUser = mManager.create();
        newUser.setName("David");
        newUser.setPhone("+5544933332222");
        Pair<User, ? extends Throwable> result = mManager.save(newUser).get();
        result = mManager.delete(result.first).get();
        assertNull(result.second);
        assertEquals(result.first, User.DEFAULT);
    }
}