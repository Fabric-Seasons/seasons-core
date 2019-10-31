package com.github.draylar.seasons.api;

import net.minecraft.nbt.CompoundTag;

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

    public Date(CompoundTag tag) {
        fromTag(tag);
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

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();

        tag.putInt("day", day);
        tag.putInt("year", year);
        tag.putString("season", season.toString());

        return tag;
    }

    public void fromTag(CompoundTag tag) {
        this.day = tag.getInt("day");
        this.year = tag.getInt("year");
        this.season = Season.valueOf(tag.getString("season"));
    }
}
