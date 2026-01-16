package com.example.addon.modules;

import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.Direction;

public class InstaminePlus extends Module {
    public InstaminePlus() {
        super(Categories.World, "instamine-plus", "Pica bloques instantáneamente.");
    }

    @EventHandler
    private void onStartBreaking(StartBreakingBlockEvent event) {
        if (event.blockPos == null || mc.player == null) return;

        Direction dir = Direction.UP;

        // Usamos la referencia completa para evitar errores de importación
        mc.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket(
            net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, 
            event.blockPos, 
            dir
        ));
        
        mc.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket(
            net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, 
            event.blockPos, 
            dir
        ));

        event.cancel();
    }
}
