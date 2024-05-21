package com.paradigm.tech.app.utlls;

import lombok.Getter;

@Getter
public enum EBatch {
    OF_14("Offline 14"),
    OF_15("Offline 15"),
    OF_16("Offline 16"),
    ON_14("Online 14"),
    ON_15("Online 15"),
    ON_16("Online 16");

    private final String description;

    EBatch(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
