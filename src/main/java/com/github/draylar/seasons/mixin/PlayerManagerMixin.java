package com.github.draylar.seasons.mixin;

import com.github.draylar.seasons.SeasonsCore;
import com.github.draylar.seasons.api.SeasonManager;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    
    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    private void onPlayerConnect(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo ci) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeCompoundTag(SeasonManager.getDate(player.getServerWorld()).toTag());
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(SeasonsCore.DATE_UPDATE_PACKET, byteBuf);

        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packet);
    }
}
