package com.github.draylar.seasons.client;

import com.github.draylar.seasons.api.Date;
import net.minecraft.client.world.ClientWorld;

import java.util.HashMap;

public class ClientWorldDateManager {

    private ClientWorldDateManager() {
        // NO-OP
    }

    private static HashMap<ClientWorld, Date> knownDates = new HashMap<>();

    public static void update(ClientWorld world, Date date) {
        knownDates.put(world, date);
    }

    public static Date getDate(ClientWorld world) {
        return knownDates.getOrDefault(world, null);
    }
}
