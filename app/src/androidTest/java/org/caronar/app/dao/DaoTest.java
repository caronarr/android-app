package org.caronar.app.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.caronar.app.Caronar;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DaoTest {

    @Test
    public void singletons() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Caronar.Singletons singletons = ((Caronar) appContext.getApplicationContext()).getSingletons();
        assertEquals(UserManager.getInstance(appContext), singletons.getUserManager());
        assertEquals(LocationManager.getInstance(appContext), singletons.getLocationManager());
        assertEquals(RiderOfferManager.getInstance(appContext), singletons.getRiderOfferManager());
        assertEquals(DriverOfferManager.getInstance(appContext), singletons.getDriverOfferManager());
        assertEquals(DealManager.getInstance(appContext), singletons.getDealManager());
    }
}