package me.michalwozniak.pylek.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DaggerActivity;
import lombok.NonNull;
import me.michalwozniak.pylek.R;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Chart;
import me.michalwozniak.pylek.model.Station;

public class ChartScreenActivity extends DaggerActivity implements ChartScreen.View {

    @Inject
    ChartScreen.Presenter mPresenter;

    @BindView(R.id.chart)
    LineChart mChart;

    Chart mChartType;

    Station mStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        mPresenter.subscribe(this);

        mChart.getDescription().setEnabled(false);
        mChart.getXAxis().setEnabled(false);

        parseIntent(getIntent());
    }

    private void parseIntent(@NonNull Intent intent) {
        mStation = (Station) intent.getSerializableExtra(ChartScreen.EXTRA_STATION);
        mChartType = (Chart) intent.getSerializableExtra(ChartScreen.EXTRA_CHART);
        mPresenter.fetchChartData(mStation);
    }

    @Override
    protected void onDestroy() {
        mPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showChartData(List<AirData> airData) {
        List<Entry> entries = new ArrayList<>(airData.size());

        for (AirData data : airData) {
            float x = data.getDate().getTime();
            float y;
            switch (mChartType) {
                case PM10:
                    y = data.getPm10();
                    break;

                case PM25:
                    y = data.getPm25();
                    break;

                case PRESSURE:
                    y = data.getPressure();
                    break;
                case TEMPERATURE:
                    y = data.getTemperature();
                    break;

                default:
                    throw new IllegalArgumentException("Unknown chart type");
            }
            entries.add(new Entry(x, y));
        }

        LineDataSet entriesDataSet = new LineDataSet(entries, mChartType.toString());
        entriesDataSet.setDrawFilled(true);
        entriesDataSet.setFillColor(Color.CYAN);
        entriesDataSet.setColor(Color.CYAN);
        entriesDataSet.setMode(LineDataSet.Mode.LINEAR);

        mChart.setData(new LineData(entriesDataSet));
        mChart.invalidate();
    }
}
