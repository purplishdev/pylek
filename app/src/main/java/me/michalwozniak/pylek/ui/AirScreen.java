package me.michalwozniak.pylek.ui;

import java.util.List;

import me.michalwozniak.pylek.base.SubscribablePresenter;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Station;

public interface AirScreen {
    interface View {
        void showAirData(AirData data);
        void showAirDataOnChart(List<AirData> data);
        void showProgress(boolean show);
        void showError(String message);
    }
    interface Presenter extends SubscribablePresenter<AirScreen.View> {
        void fetchAirData(Station station);
        void fetchAirDataChart(Station station);
    }
}
