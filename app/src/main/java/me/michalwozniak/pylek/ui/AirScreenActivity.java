package me.michalwozniak.pylek.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.github.lzyzsd.circleprogress.ArcProgress;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import dagger.android.DaggerActivity;
import me.michalwozniak.pylek.R;
import me.michalwozniak.pylek.model.Station;

public class AirScreenActivity extends DaggerActivity implements AirScreen.View {

    @Inject
    AirScreen.Presenter mPresenter;

    @BindView(R.id.station_select)
    Spinner mSpinner;

    @BindView(R.id.temp)
    ArcProgress temperatureProgress;

    @BindView(R.id.pm10)
    ArcProgress pm10Progress;

    @BindView(R.id.pm25)
    ArcProgress pm25Progress;

    @BindView(R.id.pressure)
    ArcProgress pressureProgress;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        ButterKnife.bind(this);
        mPresenter.subscribe(this);

        mSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.dropdown_item, Station.values()));
    }

    @OnItemSelected(R.id.station_select)
    public void onStationSelect(int position) {
        mPresenter.fetchData(Station.values()[position]);
    }

    @Override
    public void setPM10(double value) {
        pm10Progress.setProgress((int)value);
    }

    @Override
    public void setPM25(double value) {
        pm25Progress.setProgress((int)value);
    }

    @Override
    public void setTemperature(double value) {
        temperatureProgress.setProgress((int)value);
    }

    @Override
    public void setPressure(double value) {
        pressureProgress.setProgress((int)value);
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }
}
