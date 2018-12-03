package org.caronar.app.dao;

import android.util.Pair;

import org.caronar.app.model.BaseModel;

import java.util.concurrent.CompletableFuture;

public interface BaseManager<Model extends BaseModel> {
    Model create();
    CompletableFuture<Pair<Model, ? extends Throwable>> getById(long id);
    CompletableFuture<Pair<Model, ? extends Throwable>> save(Model model);
    CompletableFuture<Pair<Model, ? extends Throwable>> delete(Model model);
}
