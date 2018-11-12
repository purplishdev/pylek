package me.michalwozniak.pylek.base;

import io.reactivex.Scheduler;

public interface Schedulers {

    Scheduler ui();
    Scheduler background();
}
