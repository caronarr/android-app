package org.caronar.app.dao.rest;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.model.BaseModel;

abstract class ManagedModel implements BaseModel {
    @SerializedName("id")
    protected long mId;

    @Override public long getId() {
        return mId;
    }

}
