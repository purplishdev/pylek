package me.michalwozniak.pylek.ui;

import io.reactivex.Single;
import lombok.RequiredArgsConstructor;
import me.michalwozniak.pylek.api.AirApi;
import me.michalwozniak.pylek.base.BasePresenter;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Station;

@RequiredArgsConstructor
public class AirScreenPresenter extends BasePresenter<AirScreen.View> implements AirScreen.Presenter {

    private final AirApi airApi;

    private final Schedulers schedulers;

    public void fetchAirData(Station station) {

        mView.showProgress(true);

        Single<Float> pm10 = airApi.getPM10(station)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        Single<Float> pm25 = airApi.getPM25(station)
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        Single<Float> temperature = airApi.getTemperature()
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        Single<Float> pressure = airApi.getPressure()
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.ui());

        mCompositeDisposable.add(
                Single.zip(pm10, pm25, temperature, pressure, AirData::new)
                        .doFinally(() -> mView.showProgress(false))
                        .subscribe(mView::setAirData, this::onError)
        );
    }

    private void onError(Throwable error) {
        mView.showError("Nie udało się pobrać danych z tej stacji, sprawdź połączenie z internetem i spróbuj ponownie");
    }
}
