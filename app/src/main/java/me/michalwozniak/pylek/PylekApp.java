package me.michalwozniak.pylek;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import me.michalwozniak.pylek.di.DaggerAppComponent;

public class PylekApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
