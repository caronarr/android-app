package org.caronar.app;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.VisibleForTesting;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.caronar.app.dao.DealManager;
import org.caronar.app.dao.DriverOfferManager;
import org.caronar.app.dao.LocationManager;
import org.caronar.app.dao.RiderOfferManager;
import org.caronar.app.dao.UserManager;
import org.caronar.app.dao.rest.RestDealManager;
import org.caronar.app.dao.rest.RestDriverOfferManager;
import org.caronar.app.dao.rest.RestLocationManager;
import org.caronar.app.dao.rest.RestRiderOfferManager;
import org.caronar.app.dao.rest.RestUserManager;
import org.caronar.app.network.HttpClient;

public final class Caronar extends Application {
    private Singletons mSingletons;
    private Uri mBaseRestUri = Uri.parse(BuildConfig.DATA_URL);

    public Singletons getSingletons() {
        return mSingletons;
    }

    @VisibleForTesting public void setSingletons(Singletons singletons) {
        mSingletons = singletons;
    }

    @Override public void onCreate() {
        super.onCreate();
        RequestQueue requestQueue = HttpClient.getInstance(this).getRequestQueue();
        Gson gson = new GsonBuilder().create();
        Context context = this;
        mSingletons = new Singletons() {
            final LocationManager mLocationManager = new RestLocationManager(context, requestQueue, gson);
            final UserManager mUserManager = new RestUserManager(context, requestQueue, gson);
            final DealManager mDealManager = new RestDealManager(context, requestQueue, gson);
            final RiderOfferManager mRiderOfferManager = new RestRiderOfferManager(context, requestQueue, gson);
            final DriverOfferManager mDriverOfferManager = new RestDriverOfferManager(context, requestQueue, gson);

            @Override public LocationManager getLocationManager() {
                return mLocationManager;
            }

            @Override public UserManager getUserManager() {
                return mUserManager;
            }

            @Override public DealManager getDealManager() {
                return mDealManager;
            }

            @Override public RiderOfferManager getRiderOfferManager() {
                return mRiderOfferManager;
            }

            @Override public DriverOfferManager getDriverOfferManager() {
                return mDriverOfferManager;
            }
        };
    }

    public Uri getBaseRestUri() {
        return mBaseRestUri;
    }

    @VisibleForTesting
    public void setBaseRestUri(Uri baseRestUri) {
        mBaseRestUri = baseRestUri;
    }

    public interface Singletons {
        LocationManager getLocationManager();
        UserManager getUserManager();
        DealManager getDealManager();
        RiderOfferManager getRiderOfferManager();
        DriverOfferManager getDriverOfferManager();
    }
}
