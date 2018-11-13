package me.michalwozniak.pylek.ui;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import dagger.android.DaggerActivity;
import lombok.NonNull;
import me.michalwozniak.pylek.ArcProgressEx;
import me.michalwozniak.pylek.R;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Station;

public class AirScreenActivity extends DaggerActivity implements AirScreen.View {

    @Inject
    AirScreen.Presenter mPresenter;

    @BindView(R.id.station_select)
    Spinner mSpinner;

    @BindView(R.id.temp)
    ArcProgressEx temperatureProgress;

    @BindView(R.id.pm10)
    ArcProgressEx pm10Progress;

    @BindView(R.id.pm25)
    ArcProgressEx pm25Progress;

    @BindView(R.id.pressure)
    ArcProgressEx pressureProgress;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.lastUpdateLabel)
    TextView lastUpdate;

    @BindView(R.id.chart)
    LineChart mChart;

    private final static ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    private final static DateFormat dateFormatter = SimpleDateFormat.getDateTimeInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        ButterKnife.bind(this);
        mPresenter.subscribe(this);

        mSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_item, Station.values()));

        mChart.getDescription().setEnabled(false);
        mChart.getXAxis().setEnabled(false);
    }

    @OnItemSelected(R.id.station_select)
    public void onStationSelect(int position) {
        Station station = Station.values()[position];
        mPresenter.fetchAirData(station);
        mPresenter.fetchAirDataChart(station);
    }

    @Override
    public void showAirData(@NonNull AirData airData) {
        setProgress(pm10Progress, airData.getPm10());
        setProgress(pm25Progress, airData.getPm25());
        setProgress(temperatureProgress, airData.getTemperature());
        setProgress(pressureProgress, airData.getPressure());
        lastUpdate.setText("Ostatnia aktualizacja: " + dateFormatter.format(airData.getDate()));
    }

    @Override
    public void showAirDataOnChart(List<AirData> airData) {
        List<Entry> pm10Entries = new ArrayList<>(airData.size());
        List<Entry> pm25Entries = new ArrayList<>(airData.size());

        for (AirData data : airData) {
            pm10Entries.add(new Entry(data.getDate().getTime(), (int)data.getPm10()));
            pm25Entries.add(new Entry(data.getDate().getTime(), (int)data.getPm25()));
        }

        // PM10 Dataset
        LineDataSet pm10DataSet = new LineDataSet(pm10Entries, "PM10");
        pm10DataSet.setDrawFilled(true);
        pm10DataSet.setFillColor(Color.GREEN);
        pm10DataSet.setColor(Color.GREEN);
        pm10DataSet.setMode(LineDataSet.Mode.LINEAR);

        // PM2.5 Dataset
        LineDataSet pm25DataSet = new LineDataSet(pm25Entries, "PM2.5");
        pm25DataSet.setDrawFilled(true);
        pm25DataSet.setFillColor(Color.CYAN);
        pm25DataSet.setColor(Color.CYAN);
        pm25DataSet.setMode(LineDataSet.Mode.LINEAR);

        mChart.setData(new LineData(pm10DataSet, pm25DataSet));
        mChart.invalidate();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    private void setProgress(ArcProgressEx progress, float value) {
        progress.setProgress(Math.round(value));

        int range = progress.getMax() - progress.getMin();
        int percentage = Math.round((value - progress.getMin()) / range * 100);
        int color = getProgressColor(percentage);
        progress.setFinishedStrokeColor(color);
    }

    private int getProgressColor(int percentage) {
        Integer[] colors = { 0xffa9d70b, 0xfff9c802, 0xffff0000 };
        float stepPercentage = 100 / colors.length;
        float fraction = percentage / 100f;

        if (percentage == 0) {
            return colors[0];
        }

        for (int i = 0; i < colors.length; i++) {
            if (percentage <= stepPercentage * i) {
                return (int)mArgbEvaluator.evaluate(fraction, colors[i - 1], colors[i]);
            }
        }
        return colors[colors.length-1];
    }
}
