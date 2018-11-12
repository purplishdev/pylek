package me.michalwozniak.pylek.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.michalwozniak.pylek.api.AirApi;
import me.michalwozniak.pylek.api.AirRepository;
import me.michalwozniak.pylek.api.InfluxRetrofitApi;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public AirApi provideAirApi(InfluxRetrofitApi retrofit) {
        return new AirRepository(retrofit);
    }
}
