package com.github.draylar.seasons.event;

import com.github.draylar.seasons.api.Date;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.world.ServerWorld;

/**
 * Callback for when the time hits 24000 and the day is updated.
 * Called after year & season changes occur.
 * An update from the 15th day of Winter in the 2nd year would return as the 1st day of Spring in the 3rd year in the callback's {@link Date} object.
 */
public interface DateIncrementCallback {

    Event<DateIncrementCallback> EVENT = EventFactory.createArrayBacked(DateIncrementCallback.class,
            listeners -> (world, previousDate, newDate) -> {
                for(DateIncrementCallback event : listeners) {
                    event.dayIncrement(world, previousDate, newDate);
                }
            }
    );

    void dayIncrement(ServerWorld world, Date previousDate, Date newDate);
}
