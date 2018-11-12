package me.michalwozniak.pylek.base;

public interface SubscribablePresenter<T> {

    void subscribe(T view);
    void unsubscribe();
}
