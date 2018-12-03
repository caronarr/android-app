package org.caronar.app.dao;

import android.content.Context;

import org.caronar.app.Caronar;
import org.caronar.app.model.Deal;

public abstract class DealManager implements BaseManager<Deal> {
    public static DealManager getInstance(Context context) {
        return ((Caronar) context.getApplicationContext())
                .getSingletons().getDealManager();
    }
}
