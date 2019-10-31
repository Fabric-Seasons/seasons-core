package com.github.draylar.seasons.impl;

import com.github.draylar.seasons.api.Season;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.PersistentState;

public class SeasonState extends PersistentState {
    private static final String SEASON_KEY = "season";
    private static final String DAY_KEY = "day";
    private static final String YEAR_KEY = "year";

    private Season season = Season.SPRING;
    private int day = 0;
    private int year = 0;

    public SeasonState() {
        super(SEASON_KEY);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        day = tag.getInt(DAY_KEY);
        year = tag.getInt(YEAR_KEY);
        season = Season.valueOf(tag.getString(SEASON_KEY));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt(DAY_KEY, day);
        tag.putInt(YEAR_KEY, year);
        tag.putString(SEASON_KEY, season.toString());

        return tag;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
