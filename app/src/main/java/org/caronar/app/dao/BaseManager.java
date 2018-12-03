package org.caronar.app.dao;

import android.util.Pair;

import java.util.concurrent.CompletableFuture;

import org.caronar.app.model.BaseModel;

public interface BaseManager<Model extends BaseModel> {
    Model create();
    CompletableFuture<Pair<Model, Boolean>> getById(long id);
    CompletableFuture<Pair<Model, Boolean>> save(Model model);
    CompletableFuture<Pair<Model, Boolean>> delete(Model model);
}
