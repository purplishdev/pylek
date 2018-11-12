package me.michalwozniak.pylek.model;

import lombok.Getter;

public enum Station {

    WOJSKA_POLSKIEGO("ul. Wojska Polskiego 27", 4),
    HRYNIEWICZA("ul. Hryniewicza", 3);

    @Getter
    private String name;

    @Getter
    private int number;

    Station(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return name;
    }


}
