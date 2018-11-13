package me.michalwozniak.pylek.ui;

import dagger.Module;
import dagger.Provides;
import me.michalwozniak.pylek.api.AirApi;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.db.AppDatabase;

@Module
public class AirScreenModule {

    @Provides
    public AirScreen.Presenter provideAirScreenPresenter(AirApi airApi, Schedulers schedulers, AppDatabase database) {
        return new AirScreenPresenter(airApi, schedulers, database);
    }
}
