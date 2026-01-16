package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.Vec3d;

public class FlightPlus extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Configuraciones
    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("velocidad")
        .description("Velocidad del vuelo.")
        .defaultValue(0.5)
        .min(0.1)
        .build()
    );

    private final Setting<Boolean> antiKick = sgGeneral.add(new BoolSetting.Builder()
        .name("anti-kick")
        .description("Pequeño descenso para evitar el kick del servidor.")
        .defaultValue(true)
        .build()
    );

    public FlightPlus() {
        super(Categories.Movement, "fly-plus", "Vuelo con bypass de gravedad mejorado.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        // Detenemos el vuelo original de Minecraft para controlar nosotros la velocidad
        mc.player.getAbilities().flying = false;
        
        // Ponemos la velocidad actual en 0 para que no caiga
        Vec3d vel = mc.player.getVelocity();
        double y = 0;

        // Controles de subir y bajar
        if (mc.options.jumpKey.isPressed()) y = speed.get();
        else if (mc.options.sneakKey.isPressed()) y = -speed.get();
        
        // Lógica de Anti-Kick: baja un poquito cada 20 ticks (1 segundo)
        if (antiKick.get() && mc.player.age % 20 == 0) {
            y -= 0.05;
        }

        // Aplicamos la nueva velocidad
        mc.player.setVelocity(vel.x, y, vel.z);
    }

    @Override
    public void onDeactivate() {
        // Al apagar el módulo, detenemos al jugador para que no salga disparado
        mc.player.setVelocity(0, 0, 0);
    }
}
