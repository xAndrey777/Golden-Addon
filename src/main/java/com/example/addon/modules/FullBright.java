public class FullBrightImproved extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("El método para iluminar.")
        .defaultValue(Mode.Gamma)
        .build()
    );

    public enum Mode { Gamma, potion }

    public FullBrightImproved() {
        super(Categories.Render, "full-bright-plus", "Ilumina todo el mundo.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mode.get() == Mode.Gamma) {
            mc.options.getGamma().setValue(100.0);
        } else {
            mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000, 0));
        }
    }

    @Override
    public void onDeactivate() {
        mc.options.getGamma().setValue(1.0);
        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }
}
