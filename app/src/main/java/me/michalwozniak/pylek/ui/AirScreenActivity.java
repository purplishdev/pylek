package me.michalwozniak.pylek.ui;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import dagger.android.support.DaggerAppCompatActivity;
import lombok.NonNull;
import me.michalwozniak.pylek.ArcProgressEx;
import me.michalwozniak.pylek.R;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Chart;
import me.michalwozniak.pylek.model.Station;

public class AirScreenActivity extends DaggerAppCompatActivity implements AirScreen.View {

    @Inject
    AirScreen.Presenter mPresenter;

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.stationSelect)
    Spinner mSpinner;

    @BindView(R.id.temperatureProgress)
    ArcProgressEx mTemperatureProgress;

    @BindView(R.id.pm10Progress)
    ArcProgressEx mPM10Progress;

    @BindView(R.id.pm25Progress)
    ArcProgressEx mPM25Progress;

    @BindView(R.id.pressureProgress)
    ArcProgressEx mPressureProgress;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.lastUpdateLabel)
    TextView mLastUpdate;

    Station mCurrentStation;

    private final static ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    private final static DateFormat dateFormatter = SimpleDateFormat.getDateTimeInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        ButterKnife.bind(this);
        mPresenter.subscribe(this);

        mSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_item, Station.values()));

        mRefreshLayout.setOnRefreshListener(() -> mPresenter.fetchAirData(mCurrentStation));
    }

    @OnClick({R.id.pm10Progress, R.id.pm25Progress, R.id.pressureProgress, R.id.temperatureProgress})
    public void onProgressClick(View view) {
        Chart chart;
        switch (view.getId()) {
            case R.id.pm10Progress:
                chart = Chart.PM10;
                break;

            case R.id.pm25Progress:
                chart = Chart.PM25;
                break;

            case R.id.pressureProgress:
                chart = Chart.PRESSURE;
                break;

            case R.id.temperatureProgress:
                chart = Chart.TEMPERATURE;
                break;

            default:
                throw new IllegalArgumentException("Unknown view type in progress click");
        }

        Intent intent = new Intent(this, ChartScreenActivity.class);
        intent.putExtra(ChartScreen.EXTRA_STATION, mCurrentStation);
        intent.putExtra(ChartScreen.EXTRA_CHART, chart);
        startActivity(intent);
    }


    @OnItemSelected(R.id.stationSelect)
    public void onStationSelect(int position) {
        mCurrentStation = Station.values()[position];
        mPresenter.fetchAirData(mCurrentStation);
    }

    @Override
    public void showAirData(@NonNull AirData airData) {
        setProgress(mPM10Progress, airData.getPm10());
        setProgress(mPM25Progress, airData.getPm25());
        setProgress(mTemperatureProgress, airData.getTemperature());
        setProgress(mPressureProgress, airData.getPressure());
        mLastUpdate.setText("Ostatnia aktualizacja: " + dateFormatter.format(airData.getDate()));
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress(boolean show) {
        mProgressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        mRefreshLayout.setRefreshing(false);
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
