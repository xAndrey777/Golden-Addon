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
    private final Setting<List<String>> messages = sgGeneral.add(new StringListSetting.Builder()
        .name("mensajes")
        .defaultValue(List.of("EZ {player}"))
        .build());

    public AutoEZ() {
        super(Categories.Misc, "auto-ez", "Mensaje al matar.");
    }

    @EventHandler
    private void onAttack(AttackEntityEvent event) {
        if (event.entity instanceof PlayerEntity target && (target.isDead() || target.getHealth() <= 0)) {
            String randomMsg = messages.get().get(new Random().nextInt(messages.get().size()));
            mc.player.networkHandler.sendChatMessage(randomMsg.replace("{player}", target.getEntityLabel().getString()));
        }
    }
}
