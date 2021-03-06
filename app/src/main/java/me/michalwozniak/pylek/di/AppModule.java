package me.michalwozniak.pylek.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.michalwozniak.pylek.PylekApp;
import me.michalwozniak.pylek.api.InfluxRetrofitApi;
import me.michalwozniak.pylek.base.AppSchedulers;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.db.AppDatabase;
import me.michalwozniak.pylek.json.InfluxResponseDeserializer;
import me.michalwozniak.pylek.model.InfluxResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {

    @Binds
    abstract public Context bindContext(PylekApp application);

    @Singleton
    @Provides
    public static AppDatabase provideAppDatabase(PylekApp application) {
        return Room.databaseBuilder(application, AppDatabase.class, "pylek-db")
                .build();
    }

    @Singleton
    @Provides
    public static Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(InfluxResponse.class, new InfluxResponseDeserializer())
                .create();
    }

    @Singleton
    @Provides
    public static Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://94.72.84.210:9862/api/datasources/proxy/")
                .build();
    }

    @Singleton
    @Provides
    public static InfluxRetrofitApi provideInfluxRetrofitApi(Retrofit retrofit) {
        return retrofit.create(InfluxRetrofitApi.class);
    }

    @Singleton
    @Provides
    public static Schedulers provideSchedulers() {
        return new AppSchedulers();
    }
}
