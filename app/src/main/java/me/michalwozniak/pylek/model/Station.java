package me.michalwozniak.pylek.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Station {

    HRYNIEWICZA("ul. Hryniewicza", 3),
    WOJSKA_POLSKIEGO("ul. Wojska Polskiego 27", 4);

    private String name;

    private int number;

    @Override
    public String toString() {
        return name;
    }
}
