package me.michalwozniak.pylek.ui;

import java.util.List;

import me.michalwozniak.pylek.base.SubscribablePresenter;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Chart;
import me.michalwozniak.pylek.model.Station;

public interface ChartScreen {

    String EXTRA_STATION = "station";
    String EXTRA_CHART = "chart";

    interface View {
        void showChartData(List<AirData> data);
        void showError(String message);
    }
    interface Presenter extends SubscribablePresenter<ChartScreen.View> {
        void fetchChartData(Station station);
    }
}
