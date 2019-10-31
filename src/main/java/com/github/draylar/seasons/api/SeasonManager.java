package com.github.draylar.seasons.api;

import com.github.draylar.seasons.impl.SeasonProvider;
import net.minecraft.server.world.ServerWorld;

/**
 * Primary API class used by dependents.
 * Provides access to a ServerWorld's day, year, and season.
 */
public class SeasonManager {

    private SeasonManager() {
        // NO-OP
    }

    public static void setDay(ServerWorld world, int day) {
        ((SeasonProvider) world).setCurrentDay(day);
    }

    public static void setYear(ServerWorld world, int year) {
        ((SeasonProvider) world).setCurrentYear(year);
    }

    public static void setSeason(ServerWorld world, Season season) {
        ((SeasonProvider) world).setCurrentSeason(season);
    }

    public static Date getDate(ServerWorld world) {
        SeasonProvider provider = (SeasonProvider) world;
        return new Date(provider.getCurrentDay(), provider.getCurrentYear(), provider.getCurrentSeason());
    }
}
