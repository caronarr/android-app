package org.caronar.app.model;

public interface Deal extends BaseModel{
    @Override long getId();
    DriverOffer getDriverOffer();
    RiderOffer getRiderOffer();
    double getAgreedTip();

    Editor edit();

    interface Editor {
        Editor setAgreedTip(double tip);
        Editor setDriverOffer(DriverOffer driverOffer);
        Editor setRiderOffer(RiderOffer riderOffer);
        Deal finalizeEdition();

        class Default implements Editor {

            private final Deal mDeal;

            public Default(Deal deal) {
                mDeal = deal;
            }

            @Override public Editor setAgreedTip(double tip) {
                return this;
            }

            @Override public Editor setDriverOffer(DriverOffer driverOffer) {
                return this;
            }

            @Override public Editor setRiderOffer(RiderOffer riderOffer) {
                return this;
            }

            @Override public Deal finalizeEdition() {
                return mDeal;
            }
        }
    }

    Deal DEFAULT = new Deal() {
        @Override public long getId() {
            return 0;
        }

        @Override public double getAgreedTip() {
            return 0;
        }

        @Override public DriverOffer getDriverOffer() {
            return DriverOffer.DEFAULT;
        }

        @Override public RiderOffer getRiderOffer() {
            return RiderOffer.DEFAULT;
        }

        @Override public Editor edit() {
            return new Editor.Default(this);
        }
    };
}
