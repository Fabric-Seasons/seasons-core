package com.github.draylar.seasons.api;

import net.minecraft.client.resource.language.I18n;

public class SeasonUtils {

    private SeasonUtils() {
        // NO-OP
    }

    public static String getFormattedDateStatusMessage(Date date) {
        return I18n.translate("seasonscore.date_status",
                date.getDay(),
                SeasonUtils.getIntSuffix(date.getDay()),
                date.getSeason().getFormattedName(),
                date.getYear() + 1,
                SeasonUtils.getIntSuffix(date.getYear() + 1));
    }

    public static String getFormattedDateUpdateMessage(Date date) {
        return I18n.translate("seasonscore.date_update",
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
