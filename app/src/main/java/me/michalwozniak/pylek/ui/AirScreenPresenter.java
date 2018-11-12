package me.michalwozniak.pylek.ui;

import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import me.michalwozniak.pylek.api.AirApi;
import me.michalwozniak.pylek.base.BasePresenter;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.model.Station;

@RequiredArgsConstructor
public class AirScreenPresenter extends BasePresenter<AirScreen.View> implements AirScreen.Presenter {

    private final AirApi airApi;

    private final Schedulers schedulers;

    public void fetchData(Station station) {

        mView.showProgress(true);

        Single<Double> pm10 = airApi.getPM10(station)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        Single<Double> pm25 = airApi.getPM25(station)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        Single<Double> temperature = airApi.getTemperature()
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        Single<Double> pressure = airApi.getPressure()
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        mCompositeDisposable.add(
                Single.zip(pm10, pm25, temperature, pressure, (v1, v2, v3, v4) -> new Double[] { v1, v2, v3, v4 })
                        .doFinally(() -> mView.showProgress(false))
                        .subscribe(values -> {
                            mView.setPM10(values[0]);
                            mView.setPM25(values[1]);
                            mView.setTemperature(values[2]);
                            mView.setPressure(values[3]);
                        })
        );
    }

    private void onError(Throwable error) {

    }
}
