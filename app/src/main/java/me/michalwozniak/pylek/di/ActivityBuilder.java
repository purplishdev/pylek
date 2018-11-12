package me.michalwozniak.pylek.di;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import me.michalwozniak.pylek.ui.AirScreenActivity;
import me.michalwozniak.pylek.ui.AirScreenModule;

@Module(includes = AndroidInjectionModule.class)
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = AirScreenModule.class)
    abstract public AirScreenActivity bindAirActivity();
}
