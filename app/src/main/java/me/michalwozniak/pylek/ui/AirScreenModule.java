package me.michalwozniak.pylek.ui;

import dagger.Module;
import dagger.Provides;
import me.michalwozniak.pylek.api.AirApi;
import me.michalwozniak.pylek.base.Schedulers;

@Module
public class AirScreenModule {

    @Provides
    public AirScreen.Presenter providesAirScreenPresenter(AirApi airApi, Schedulers schedulers) {
        return new AirScreenPresenter(airApi, schedulers);
    }
}
