package me.michalwozniak.pylek.base;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AppSchedulers implements Schedulers {

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler io() {
        return io.reactivex.schedulers.Schedulers.io();
    }

    @Override
    public Scheduler computation() {
        return io.reactivex.schedulers.Schedulers.computation();
    }
}
