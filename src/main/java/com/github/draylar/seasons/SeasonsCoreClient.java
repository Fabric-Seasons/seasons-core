package com.github.draylar.seasons;

import com.github.draylar.seasons.api.Date;
import com.github.draylar.seasons.client.ClientWorldDateManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.CompoundTag;

public class SeasonsCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerSeasonUpdatePacketHandler();
    }

    private void registerSeasonUpdatePacketHandler() {
        ClientSidePacketRegistry.INSTANCE.register(
                SeasonsCore.DATE_UPDATE_PACKET,
                (context, packet) -> {
                    CompoundTag tag = packet.readCompoundTag();
                    Date date = new Date(tag);
                    ClientWorldDateManager.update(MinecraftClient.getInstance().world, date);
                }
        );
    }
}
