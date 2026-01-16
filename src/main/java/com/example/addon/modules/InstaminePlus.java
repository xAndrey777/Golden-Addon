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
        // Usamos la posición del bloque que el evento ya nos da
        if (event.blockPos == null || mc.player == null) return;

        Direction dir = Direction.UP;

        // Enviamos los paquetes directamente al networkHandler
        mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(
            PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, 
            event.blockPos, 
            dir
        ));
        
        mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(
            PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, 
            event.blockPos, 
            dir
        ));

        // Cancelamos el evento original para que no haya delay
        event.cancel();
    }
}
