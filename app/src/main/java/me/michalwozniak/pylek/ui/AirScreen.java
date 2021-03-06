package me.michalwozniak.pylek.ui;

import me.michalwozniak.pylek.base.SubscribablePresenter;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Chart;
import me.michalwozniak.pylek.model.Station;

public interface AirScreen {
    interface View {
        void showAirData(AirData data);
        void showProgress(boolean show);
        void showError(String message);
    }
    interface Presenter extends SubscribablePresenter<AirScreen.View> {
        void fetchAirData(Station station);
    }
}
