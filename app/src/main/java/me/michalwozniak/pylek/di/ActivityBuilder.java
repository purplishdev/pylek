package me.michalwozniak.pylek.di;

import dagger.Module;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import me.michalwozniak.pylek.ui.AirScreenActivity;
import me.michalwozniak.pylek.ui.AirScreenModule;
import me.michalwozniak.pylek.ui.ChartScreenActivity;
import me.michalwozniak.pylek.ui.ChartScreenModule;

@Module(includes = AndroidInjectionModule.class)
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = AirScreenModule.class)
    abstract public AirScreenActivity bindAirActivity();

    @ContributesAndroidInjector(modules = ChartScreenModule.class)
    abstract public ChartScreenActivity bindChartActivity();
}
