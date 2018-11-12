package me.michalwozniak.pylek.di;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import me.michalwozniak.pylek.PylekApp;
import me.michalwozniak.pylek.api.InfluxRetrofitApi;
import me.michalwozniak.pylek.base.Schedulers;
import me.michalwozniak.pylek.base.SimpleSchedulers;
import me.michalwozniak.pylek.json.InfluxRecordDeserializer;
import me.michalwozniak.pylek.model.InfluxResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {

    @Binds
    abstract public Context bindContext(PylekApp application);

    @Provides
    public static Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(InfluxResponse.class, new InfluxRecordDeserializer())
                .create();
    }

    @Provides
    public static Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://94.72.84.210:9862/api/datasources/proxy/")
                .build();
    }

    @Provides
    public static InfluxRetrofitApi provideInfluxRetrofitApi(Retrofit retrofit) {
        return retrofit.create(InfluxRetrofitApi.class);
    }

    @Provides
    public static Schedulers provideSchedulers() {
        return new SimpleSchedulers();
    }
}
