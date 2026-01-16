public class AutoEZ extends Module {
    private final List<String> mensajes = List.of(
        "¡EZ {player}! Golden Addon on top.",
        "Imagina morir contra mí, {player}.",
        "Llora un poco más, {player}."
    );

    public AutoEZ() {
        super(Categories.Misc, "auto-ez", "Envía un mensaje cuando matas a alguien.");
    }

    @EventHandler
    private void onAttack(AttackEntityEvent event) {
        if (event.entity instanceof PlayerEntity target) {
            if (target.isDead() || target.getHealth() <= 0) {
                String msg = mensajes.get(new Random().nextInt(mensajes.size()))
                    .replace("{player}", target.getEntityName());
                mc.player.networkHandler.sendChatMessage(msg);
            }
        }
    }
}
