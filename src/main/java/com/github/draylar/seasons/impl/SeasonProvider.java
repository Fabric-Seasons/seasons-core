package com.github.draylar.seasons.impl;

import com.github.draylar.seasons.api.Season;

public interface SeasonProvider {

    int getCurrentDay();
    int getCurrentYear();
    Season getCurrentSeason();

    void setCurrentDay(int day);
    void setCurrentYear(int year);
    void setCurrentSeason(Season season);
}
