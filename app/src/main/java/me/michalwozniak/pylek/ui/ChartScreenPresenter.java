package me.michalwozniak.pylek.ui;

import lombok.RequiredArgsConstructor;
import me.michalwozniak.pylek.base.BasePresenter;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.db.AppDatabase;
import me.michalwozniak.pylek.model.AirData;
import me.michalwozniak.pylek.model.Station;

@RequiredArgsConstructor
public class ChartScreenPresenter extends BasePresenter<ChartScreen.View> implements ChartScreen.Presenter {

    private final Schedulers schedulers;

    private final AppDatabase database;

    @Override
    public void fetchChartData(Station station) {
        mCompositeDisposable.add(
                database.airDataDao().getAll(station)
                        .subscribeOn(schedulers.computation())
                        .observeOn(schedulers.ui())
                        .toObservable()
                        .flatMapIterable(list -> list)
                        .toSortedList(AirData::compareTo)
                        .subscribe(mView::showChartData, this::onError)
        );
    }

    private void onError(Throwable error) {
        mView.showError(error.getLocalizedMessage());
    }
}
