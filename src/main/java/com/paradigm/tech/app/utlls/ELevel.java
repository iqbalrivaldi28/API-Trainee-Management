package com.paradigm.tech.app.utlls;

import lombok.Getter;

@Getter
public enum ELevel {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");

    private final String displayName;

    ELevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static ELevel fromString(String level) {
        for (ELevel eLevel : ELevel.values()) {
            if (eLevel.displayName.equalsIgnoreCase(level)) {
                return eLevel;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + level);
    }
}