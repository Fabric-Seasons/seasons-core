package com.github.draylar.seasons.api;

import net.minecraft.text.TranslatableText;

/**
 * Represents one of the 4 standard seasons (Spring, Summer, Fall, and Winter).
 * Provides utility methods to get translated season names & cycle to the next season.
 * A translated season name is defined in the language file, and the key takes the form of "seasoncore.[season]."
 */
public enum  Season {
    SPRING("seasonscore.spring"),
    SUMMER("seasonscore.summer"),
    FALL("seasonscore.fall"),
    WINTER("seasonscore.winter");

    private String translationKey;

    Season(String translationKey) {
        this.translationKey = translationKey;
    }

    public static Season next(Season season) {
        switch (season) {
            case SPRING:
                return SUMMER;
            case SUMMER:
                return FALL;
            case FALL:
                return WINTER;
            case WINTER:
                return SPRING;
        }

        return season;
    }

    public TranslatableText getFormattedName() {
        return new TranslatableText(translationKey);
    }
}
