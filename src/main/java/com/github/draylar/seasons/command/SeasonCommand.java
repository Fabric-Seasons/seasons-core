package com.github.draylar.seasons.command;

import com.github.draylar.seasons.api.Season;
import com.github.draylar.seasons.api.SeasonManager;
import com.github.draylar.seasons.api.SeasonUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class SeasonCommand {

    private SeasonCommand() {
        // NO-OP
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("season")
                .then(CommandManager.literal("day")
                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .then(CommandManager.argument("day", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int day = IntegerArgumentType.getInteger(context, "day");
                                    ServerWorld serverWorld = context.getSource().getWorld();
                                    SeasonManager.setDay(serverWorld, day);

                                    return 1;
                                }))

                ).then(CommandManager.literal("year")
                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .then(CommandManager.argument("year", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int year = IntegerArgumentType.getInteger(context, "year");
                                    ServerWorld serverWorld = context.getSource().getWorld();
                                    SeasonManager.setYear(serverWorld, year);

                                    return 1;
                                }))
                ).then(CommandManager.literal("season")
                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .then(CommandManager.argument("season", StringArgumentType.string())
                                .executes(context -> {
                                    String season = StringArgumentType.getString(context, "season");
                                    ServerWorld serverWorld = context.getSource().getWorld();
                                    SeasonManager.setSeason(serverWorld, Season.valueOf(season));

                                    return 1;
                                })
                        )
                ).then(CommandManager.literal("poll")
                        .executes(context -> {
                            ServerWorld serverWorld = context.getSource().getWorld();
                            ServerPlayerEntity player = context.getSource().getPlayer();

                            String updateMessage = SeasonUtils.getFormattedDateStatusMessage(SeasonManager.getDate(serverWorld));
                            String prefix = String.format("[%s] ", I18n.translate("seasonscore.title"));

                            player.sendMessage(new LiteralText(
                                    new LiteralText(prefix).formatted(Formatting.GOLD).asFormattedString()
                                            + new LiteralText(updateMessage).asFormattedString()
                            ));

                            return 1;
                        })
                )
        );
    }
}
