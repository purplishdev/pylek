package me.michalwozniak.pylek.ui;

import me.michalwozniak.pylek.base.SubscribablePresenter;
import me.michalwozniak.pylek.model.Station;

public interface AirScreen {
    interface View {
        void setPM10(double value);
        void setPM25(double value);
        void setTemperature(double value);
        void setPressure(double value);
        void showProgress(boolean show);
    }
    interface Presenter extends SubscribablePresenter<AirScreen.View> {
        void fetchData(Station station);
    }
}
