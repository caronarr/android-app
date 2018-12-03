package org.caronar.app.dao.rest.future;

import android.util.Pair;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VolleyCompletableFutureList<Something> extends VolleyCompletableFuture<List<Something>> {

    final Something mDefaultItem;

    public VolleyCompletableFutureList(Something defaultItem) {
        super(null);
        mDefaultItem = defaultItem;
    }

    @Override public void onErrorResponse(VolleyError error) {
        complete(new Pair<>(new ArrayList<>(), error));
    }

    @Override public void onResponse(List<Something> response) {
        if (response == null)
            response = new ArrayList<>();

        response = response.stream()
                .map(model -> model == null ? mDefaultItem : model)
                .collect(Collectors.toList());

        complete(new Pair<>(response, null));
    }
}
