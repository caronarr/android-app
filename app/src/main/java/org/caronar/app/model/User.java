package org.caronar.app.model;

import com.google.gson.annotations.SerializedName;

public final class User implements BaseModel {

    @SerializedName("id")
    private long mId = 0;

    @SerializedName("name")
    private String mName = DEFAULT_NAME;

    @SerializedName("phone")
    private String mPhone = DEFAULT_PHONE;

    @SerializedName("photo")
    private String mPhoto = DEFAULT_PHOTO;

    @Override public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public static final User DEFAULT = new User();

    public static final String DEFAULT_NAME = "user_without_name";
    public static final String DEFAULT_PHONE = "user_without_phone";
    public static final String DEFAULT_PHOTO = "default_photo";
}
