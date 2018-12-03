package org.caronar.app;

import android.app.Application;

import org.caronar.app.dao.DealManager;
import org.caronar.app.dao.DriverOfferManager;
import org.caronar.app.dao.LocationManager;
import org.caronar.app.dao.RiderOfferManager;
import org.caronar.app.dao.UserManager;

public final class Caronar extends Application {
    private Singletons mSingletons;
    public Singletons getSingletons() {
        return mSingletons;
    }

    public interface Singletons {
        LocationManager getLocationManager();
        UserManager getUserManager();
        DealManager getDealManager();
        RiderOfferManager getRiderOfferManager();
        DriverOfferManager getDriverOfferManager();
    }
}
