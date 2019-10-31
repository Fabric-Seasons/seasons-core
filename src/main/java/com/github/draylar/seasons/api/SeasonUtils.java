package com.github.draylar.seasons.api;

import net.minecraft.text.TranslatableText;

public class SeasonUtils {

    private SeasonUtils() {
        // NO-OP
    }

    public static TranslatableText getFormattedDateStatusMessage(Date date) {
        return new TranslatableText("seasonscore.date_status",
                date.getDay(),
                SeasonUtils.getIntSuffix(date.getDay()),
                date.getSeason().getFormattedName(),
                date.getYear() + 1,
                SeasonUtils.getIntSuffix(date.getYear() + 1));
    }

    public static TranslatableText getFormattedDateUpdateMessage(Date date) {
        return new TranslatableText("seasonscore.date_update",
                date.getDay(),
                SeasonUtils.getIntSuffix(date.getDay()),
                date.getSeason().getFormattedName(),
                date.getYear() + 1,
                SeasonUtils.getIntSuffix(date.getYear() + 1));
    }

    public static String getIntSuffix(int i) {
        if (i >= 11 && i <= 13) {
            return "th";
        }

        switch (i % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}
