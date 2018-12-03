package org.caronar.app.model;

import android.net.Uri;

public interface User extends BaseModel {

    @Override long getId();
    String getName();
    String getPhone();
    Uri getPhoto();
    Editor edit();

    interface Editor {
        Editor setName(String name);
        Editor setPhone(String phone);
        Editor setPhoto(Uri photo);
        Editor setPhoto(String photo);

        User finalizeEdition();

        class Default implements Editor {
            private final User mUser;

            public Default(User user) {
                mUser = user;
            }

            @Override public Editor setName(String name) {
                return this;
            }

            @Override public Editor setPhone(String phone) {
                return this;
            }

            @Override public Editor setPhoto(Uri photo) {
                return this;
            }

            @Override public Editor setPhoto(String photo) {
                return this;
            }

            @Override public User finalizeEdition() {
                return mUser;
            }
        }
    }

    User DEFAULT = new User() {

        @Override public long getId() {
            return 0;
        }

        @Override public String getName() {
            return DEFAULT_NAME;
        }

        @Override public String getPhone() {
            return DEFAULT_PHONE;
        }

        @Override public Uri getPhoto() {
            return DEFAULT_PHOTO;
        }

        @Override public Editor edit() {
            return new Editor.Default(this);
        }
    };

    String DEFAULT_NAME = "user_without_name";
    String DEFAULT_PHONE = "user_without_phone";
    Uri DEFAULT_PHOTO = Uri.EMPTY;
}
