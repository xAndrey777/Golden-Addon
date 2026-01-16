package com.example.addon.modules;

import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.Random;

public class AutoEZ extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Configuración de mensajes en el menú
    private final Setting<List<String>> messages = sgGeneral.add(new StringListSetting.Builder()
        .name("mensajes")
        .description("Mensajes aleatorios al matar a alguien.")
        .defaultValue(List.of(
            "¡GG {player}! Saludos desde Golden Addon.",
            "EZ {player}, a dormir.",
            "¿Tan rápido, {player}?"
        ))
        .build()
    );

    public AutoEZ() {
        super(Categories.Misc, "auto-ez", "Envía un mensaje automático cuando matas a un jugador.");
    }

    @EventHandler
    private void onAttack(AttackEntityEvent event) {
        // Verificamos que sea un jugador y que esté muerto o sin vida
        if (event.entity instanceof PlayerEntity target) {
            if (target.isDead() || target.getHealth() <= 0) {
                
                List<String> msgList = messages.get();
                if (msgList.isEmpty()) return;

                // Elegir mensaje al azar
                String randomMsg = msgList.get(new Random().nextInt(msgList.size()));
                
                // Reemplazar {player} por el nombre del objetivo
                String finalMsg = randomMsg.replace("{player}", target.getName().getString());
                
                // Enviar al chat
                mc.player.networkHandler.sendChatMessage(finalMsg);
            }
        }
    }
}
