package org.caronar.app.model;

import org.caronar.app.ride.Schedule;

import java.util.Date;

public interface DriverOffer extends BaseModel, Schedule {
    @Override long getId();
    @Override Location getOrigin();
    @Override Location getDestination();
    @Override Date getScheduledTime();
    double getRequestedTip();
    User getDriver();

    Editor edit();

    interface Editor {
        Editor setOrigin(Location origin);
        Editor setDestination(Location destination);
        Editor setScheduledTime(Date scheduledTime);
        Editor setRequestedTip(double requestedTip);
        Editor setDriver(User driver);
        DriverOffer finalizeEdition();

        class Default implements Editor {

            private final DriverOffer mDriverOffer;

            Default(DriverOffer driverOffer) {
                mDriverOffer = driverOffer;
            }

            @Override public Editor setOrigin(Location origin) {
                return this;
            }

            @Override public Editor setDestination(Location destination) {
                return this;
            }

            @Override public Editor setScheduledTime(Date scheduledTime) {
                return this;
            }

            @Override public Editor setRequestedTip(double requestedTip) {
                return this;
            }

            @Override public Editor setDriver(User driver) {
                return this;
            }

            @Override public DriverOffer finalizeEdition() {
                return mDriverOffer;
            }
        }
    }

    DriverOffer DEFAULT = new DriverOffer() {
        @Override public long getId() {
            return 0;
        }

        @Override public Location getOrigin() {
            return Location.DEFAULT;
        }

        @Override public Location getDestination() {
            return Location.DEFAULT;
        }

        @Override public Date getScheduledTime() {
            return Schedule.DEFAULT_SCHEDULED_TIME;
        }

        @Override public double getRequestedTip() {
            return 0;
        }

        @Override public User getDriver() {
            return User.DEFAULT;
        }

        @Override public Editor edit() {
            return new Editor.Default(this);
        }
    };
}
