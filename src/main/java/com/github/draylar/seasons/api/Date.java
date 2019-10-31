package com.github.draylar.seasons.api;

/**
 * Represents a Date in Minecraft time.
 * By default, a season lasts 15 days, which ends up as exactly 5 hours of real life time.
 * A year consists of the 4 standard seasons; a full year is 20 hours of real life time by default;
 */
public class Date {
    private int day;
    private int year;
    private Season season;

    public Date(int day, int year, Season season) {
        this.day = day;
        this.year = year;
        this.season = season;
    }

    int getDay() {
        return day;
    }

    int getYear() {
        return year;
    }

    Season getSeason() {
        return season;
    }
}
