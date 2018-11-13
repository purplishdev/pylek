package me.michalwozniak.pylek.ui;

import me.michalwozniak.pylek.base.SubscribablePresenter;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Station;

public interface AirScreen {
    interface View {
        void setAirData(AirData data);
        void showProgress(boolean show);
        void showError(String message);
    }
    interface Presenter extends SubscribablePresenter<AirScreen.View> {
        void fetchAirData(Station station);
    }
}
