package org.caronar.app.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void getId() {
        Gson gson = new GsonBuilder().create();
        String s = gson.toJson(new User());
        assertEquals("{\"name\":\"" + User.DEFAULT_NAME + "\",\"phone\":\"" + User.DEFAULT_PHONE + "\",\"photo\":\"" + User.DEFAULT_PHOTO + "\"}", s);
    }
}