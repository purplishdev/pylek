package me.michalwozniak.pylek.base;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<T> implements SubscribablePresenter<T> {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected T mView;

    public void subscribe(T view) {
        mView = view;
    }

    public void unsubscribe() {
        mView = null;
        mCompositeDisposable.dispose();
    }
}
