package me.michalwozniak.pylek.ui;

import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import me.michalwozniak.pylek.api.AirApi;
import me.michalwozniak.pylek.base.BasePresenter;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.db.AppDatabase;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Station;

@RequiredArgsConstructor
public class AirScreenPresenter extends BasePresenter<AirScreen.View> implements AirScreen.Presenter {

    private final AirApi airApi;

    private final Schedulers schedulers;

    private final AppDatabase database;

    public void fetchAirData(Station station) {

        mView.showProgress(true);

        Single<Float> pm10 = airApi.getPM10(station)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui());

        Single<Float> pm25 = airApi.getPM25(station)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui());

        Single<Float> temperature = airApi.getTemperature()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui());

        Single<Float> pressure = airApi.getPressure()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui());

        mCompositeDisposable.add(
                Single.zip(pm10, pm25, temperature, pressure, Single.just(station), AirData::new)
                        .doFinally(() -> mView.showProgress(false))
                        .subscribe(airData -> {
                            mView.showAirData(airData);
                            saveAirData(airData);
                        }, this::onError)
        );
    }

    private void saveAirData(AirData airData) {
        mCompositeDisposable.add(
            Single.just(database)
                    .subscribeOn(schedulers.io())
                    .doOnError(this::onError)
                    .subscribe(db -> db.airDataDao().insertAll(airData))
        );
    }

    private void onError(Throwable error) {
        mView.showError(error.getLocalizedMessage());
    }
}
