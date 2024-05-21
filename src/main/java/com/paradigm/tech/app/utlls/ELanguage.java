package com.paradigm.tech.app.utlls;

import lombok.Getter;

@Getter
public enum ELanguage {
    INDONESIAN("Indonesian"),
    MALAY("Malay"),
    ENGLISH("English"),
    GERMAN("German"),
    MANDARIN("Mandarin"),
    CANTONESE("Cantonese"),
    HOKKIEN("Hokkien"),
    FRENCH("French"),
    SPANISH("Spanish"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    HINDI("Hindi"),
    ARABIC("Arabic"),
    PORTUGUESE("Portuguese"),
    RUSSIAN("Russian"),
    ITALIAN("Italian"),
    DUTCH("Dutch"),
    VIETNAMESE("Vietnamese"),
    THAI("Thai"),
    FILIPINO("Filipino");

    private final String languageName;

    // Constructor
    ELanguage(String languageName) {
        this.languageName = languageName;
    }

    // Override toString method
    @Override
    public String toString() {
        return languageName;
    }
}
