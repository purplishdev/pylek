package me.michalwozniak.pylek.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import me.michalwozniak.pylek.PylekApp;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuilder.class,
                RepositoryModule.class,
                AppModule.class
        }
)
public interface AppComponent extends AndroidInjector<PylekApp> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<PylekApp> { }
}
