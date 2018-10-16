package me.michalwozniak.pylek.model;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import lombok.Data;

@Data
public class InfluxResults implements Iterable<InfluxData> {

    private List<InfluxSeries> series;

    @NonNull
    @Override
    public Iterator<InfluxData> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super InfluxData> action) {

    }

    @Override
    public Spliterator<InfluxData> spliterator() {
        return null;
    }
}
