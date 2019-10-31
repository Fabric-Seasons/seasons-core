package com.github.draylar.seasons.config;

import com.github.draylar.seasons.SeasonsCore;
import me.sargunvohra.mcmods.autoconfig1.ConfigData;
import me.sargunvohra.mcmods.autoconfig1.annotation.Config;

@Config(name = SeasonsCore.MODID)
public class SeasonsConfig implements ConfigData {
    private int daysPerMonth = 15;

    public int getDaysPerMonth() {
        return daysPerMonth;
    }
}
