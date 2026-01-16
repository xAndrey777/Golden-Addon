package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.Vec3d;

public class FlightPlus extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder().name("speed").defaultValue(0.5).build());

    public FlightPlus() {
        super(Categories.Movement, "fly-plus", "Vuelo mejorado.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null) return;
        mc.player.getAbilities().flying = false;
        double y = 0;
        if (mc.options.jumpKey.isPressed()) y = speed.get();
        else if (mc.options.sneakKey.isPressed()) y = -speed.get();
        if (mc.player.age % 20 == 0) y -= 0.04; 
        mc.player.setVelocity(mc.player.getVelocity().x, y, mc.player.getVelocity().z);
    }
}
