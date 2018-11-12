package me.michalwozniak.pylek.base;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SimpleSchedulers implements Schedulers {

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler background() {
        return io.reactivex.schedulers.Schedulers.io();
    }
}
