package org.caronar.app.dao.rest;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import org.caronar.app.model.User;

final class ManagedUser extends ManagedModel implements User, User.Editor {

    @SerializedName("name")
    private String mName = User.DEFAULT_NAME;

    @SerializedName("phone")
    private String mPhone = User.DEFAULT_PHONE;

    @SerializedName("photo")
    private String mPhoto = null;

    @Override public String getName() {
        return mName;
    }

    @Override public String getPhone() {
        return mPhone;
    }

    @Override public Uri getPhoto() {
        return mPhoto == null ? Uri.EMPTY : Uri.parse(mPhoto);
    }

    @Override public User.Editor edit() {
        return this;
    }

    @Override public User.Editor setName(String name) {
        mName = name != null ? name : User.DEFAULT_NAME;
        return this;
    }

    @Override public User.Editor setPhone(String phone) {
        mPhone = phone != null ? phone : User.DEFAULT_PHONE;
        return this;
    }

    @Override public User.Editor setPhoto(Uri photo) {
        return setPhoto(photo == null || photo == User.DEFAULT_PHOTO ? null : photo.toString());
    }

    @Override public User.Editor setPhoto(String photo) {
        mPhoto = photo;
        return this;
    }

    @Override public User finalizeEdition() {
        return this;
    }

    void assign(User other) {
        if (other == null)
            other = User.DEFAULT;
        if (other == this)
            return;
        mId = other.getId();
        setName(other.getName());
        setPhone(other.getPhone());
        setPhoto(other.getPhoto());
    }
}
