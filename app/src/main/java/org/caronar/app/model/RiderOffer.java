package org.caronar.app.model;

import org.caronar.app.ride.Schedule;

import java.util.Date;

public interface RiderOffer extends BaseModel, Schedule {
    @Override long getId();
    @Override Location getOrigin();
    @Override Location getDestination();
    @Override Date getScheduledTime();
    double getOfferedTip();
    User getRider();

    Editor edit();

    interface Editor {
        Editor setOrigin(Location origin);
        Editor setDestination(Location destination);
        Editor setScheduledTime(Date scheduledTime);
        Editor setOfferedTip(double offeredTip);
        Editor setRider(User rider);
        RiderOffer finalizeEdition();

        class Default implements Editor {

            private final RiderOffer mRiderOffer;

            public Default(RiderOffer riderOffer) {
                mRiderOffer = riderOffer;
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

            @Override public Editor setOfferedTip(double offeredTip) {
                return this;
            }

            @Override public Editor setRider(User rider) {
                return this;
            }

            @Override public RiderOffer finalizeEdition() {
                return mRiderOffer;
            }
        }
    }


    RiderOffer DEFAULT = new RiderOffer() {

        @Override public long getId() {
            return 0;
        }

        @Override public Editor edit() {
            return new Editor.Default(this);
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

        @Override public double getOfferedTip() {
            return 0;
        }

        @Override public User getRider() {
            return User.DEFAULT;
        }
    };
}
