package me.michalwozniak.pylek.ui;

import dagger.Module;
import dagger.Provides;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.db.AppDatabase;

@Module
public class ChartScreenModule {

    @Provides
    public ChartScreen.Presenter provideChartScreenPresenter(Schedulers schedulers, AppDatabase database) {
        return new ChartScreenPresenter(schedulers, database);
    }
}
