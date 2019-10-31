package com.github.draylar.seasons.mixin;

import com.github.draylar.seasons.SeasonsCore;
import com.github.draylar.seasons.api.Date;
import com.github.draylar.seasons.api.Season;
import com.github.draylar.seasons.api.SeasonManager;
import com.github.draylar.seasons.event.DateIncrementCallback;
import com.github.draylar.seasons.impl.SeasonProvider;
import com.github.draylar.seasons.impl.SeasonState;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executor;
import java.util.function.BiFunction;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements SeasonProvider {

    protected ServerWorldMixin(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> biFunction, Profiler profiler, boolean b) {
        super(levelProperties, dimensionType, biFunction, profiler, b);
    }

    @Shadow public abstract PersistentStateManager getPersistentStateManager();

    private SeasonState seasonState;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(MinecraftServer minecraftServer, Executor executor, WorldSaveHandler worldSaveHandler, LevelProperties levelProperties, DimensionType dimensionType, Profiler profiler, WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        seasonState = this.getPersistentStateManager().getOrCreate(SeasonState::new, "season");
    }

    @Override
    protected void tickTime() {
        super.tickTime();

        if(isFinalTickOfDay()) {
            Date currentDate = new Date(seasonState.getDay(), seasonState.getYear(), seasonState.getSeason());

            if (wasFinalDayOfMonth(seasonState.getDay())) {
                updateSeason();
            }

            incrementCurrentDay();
            invokeDayIncrementCallback(currentDate);
            saveSeasonState();
        }
    }

    @Unique
    private void invokeDayIncrementCallback(Date currentDate) {
        // trigger date increment event
        DateIncrementCallback.EVENT.invoker().dayIncrement((
                        ServerWorld) (Object) this,
                currentDate,
                new Date(seasonState.getDay(), seasonState.getYear(), seasonState.getSeason())
        );
    }

    @Unique
    private void saveSeasonState() {
        seasonState.markDirty();
        getPersistentStateManager().save();
        updateClients();
    }

    @Unique
    private void updateClients() {
        ServerWorld serverWorld = (ServerWorld) (Object) this;

        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeCompoundTag(SeasonManager.getDate(serverWorld).toTag());
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(SeasonsCore.DATE_UPDATE_PACKET, byteBuf);

        serverWorld.getPlayers().forEach(player -> ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packet));
    }

    @Unique
    private void incrementCurrentDay() {
        seasonState.setDay(seasonState.getDay() + 1);
    }

    @Unique
    private void updateSeason() {
        Season currentSeason = seasonState.getSeason();

        if (currentSeason == Season.WINTER) {
            seasonState.setYear(seasonState.getYear() + 1);
        }

        seasonState.setSeason(Season.next(currentSeason));
        seasonState.setDay(0);
    }

    @Unique
    private boolean wasFinalDayOfMonth(int day) {
        return day >= SeasonsCore.CONFIG.getDaysPerMonth();
    }

    @Unique
    private boolean isFinalTickOfDay() {
        return properties.getTimeOfDay() % 24000 == 0;
    }

    @Override
    @Unique
    public int getCurrentDay() {
        return seasonState.getDay();
    }

    @Override
    @Unique
    public int getCurrentYear() {
        return seasonState.getYear();
    }

    @Override
    @Unique
    public Season getCurrentSeason() {
        return seasonState.getSeason();
    }

    @Override
    @Unique
    public void setCurrentDay(int day) {
        seasonState.setDay(day);
        saveSeasonState();
    }

    @Override
    @Unique
    public void setCurrentYear(int year) {
        seasonState.setYear(year);
        saveSeasonState();
    }

    @Override
    @Unique
    public void setCurrentSeason(Season season) {
        seasonState.setSeason(season);
        saveSeasonState();
    }
}
