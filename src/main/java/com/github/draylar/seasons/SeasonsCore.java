package com.github.draylar.seasons;

import com.github.draylar.seasons.api.SeasonUtils;
import com.github.draylar.seasons.command.SeasonCommand;
import com.github.draylar.seasons.config.SeasonsConfig;
import com.github.draylar.seasons.event.DateIncrementCallback;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeasonsCore implements ModInitializer
{
	public static final String MODID = "seasons-core";
	private static final Logger LOGGER = LogManager.getLogger("Seasons: Core");
	public static final SeasonsConfig CONFIG = AutoConfig.register(SeasonsConfig.class, GsonConfigSerializer::new).getConfig();
	public static final Identifier DATE_UPDATE_PACKET = new Identifier(MODID, "date_update");

	// TODO: SEND ON WORLD JOIN

	@Override
	public void onInitialize() {
		CommandRegistry.INSTANCE.register(false, SeasonCommand::register);

		if(FabricLoader.getInstance().isDevelopmentEnvironment()) {
			DateIncrementCallback.EVENT.register((serverWorld, previousDate, newDate) -> {
				LOGGER.info("[Seasons: Core] [DEV ENV]: " + SeasonUtils.getFormattedDateUpdateMessage(newDate));
			});
		}
	}
}
