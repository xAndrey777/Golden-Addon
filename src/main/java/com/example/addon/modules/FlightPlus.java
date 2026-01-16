public class FlightPlus extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder().name("speed").defaultValue(0.5).build());

    public FlightPlus() {
        super(Categories.Movement, "fly-plus", "Vuelo con bypass de gravedad.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        mc.player.getAbilities().flying = false;
        mc.player.setVelocity(0, 0, 0);

        Vec3d velocity = mc.player.getRotationVector();
        double x = 0, y = 0, z = 0;

        if (mc.options.jumpKey.isPressed()) y = speed.get();
        if (mc.options.sneakKey.isPressed()) y = -speed.get();
        
        // Pequeño descenso para evitar el kick por volar
        if (mc.player.age % 20 == 0) y -= 0.04; 

        mc.player.setVelocity(mc.player.getVelocity().add(0, y, 0));
    }
}
