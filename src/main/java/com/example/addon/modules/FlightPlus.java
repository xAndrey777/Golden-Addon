public class FlightPlus extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Ajuste de velocidad (Decimal)
    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("velocidad")
        .description("Qué tan rápido vuelas.")
        .defaultValue(0.5)
        .min(0.1)
        .max(5.0)
        .build()
    );

    // Ajuste de Anti-Kick (On/Off)
    private final Setting<Boolean> antiKick = sgGeneral.add(new BoolSetting.Builder()
        .name("anti-kick")
        .description("Baja un poco cada segundo para evitar que el server te eche.")
        .defaultValue(true)
        .build()
    );

    public FlightPlus() {
        super(Categories.Movement, "fly-plus", "Vuelo personalizable.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        mc.player.getAbilities().flying = false;
        mc.player.setVelocity(0, 0, 0);

        double y = 0;

        if (mc.options.jumpKey.isPressed()) y = speed.get();
        if (mc.options.sneakKey.isPressed()) y = -speed.get();
        
        // Aplicar Anti-Kick solo si está activado
        if (antiKick.get() && mc.player.age % 20 == 0) {
            y -= 0.05;
        }

        mc.player.setVelocity(0, y, 0);
        
        // Movimiento horizontal (opcional para completar el fly)
        Vec3d move = mc.player.getRotationVector().multiply(speed.get());
        // Aquí podrías añadir lógica de movimiento X y Z
    }
}
