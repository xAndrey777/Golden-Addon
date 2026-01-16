public class AutoEZ extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Creamos la lista de mensajes configurable
    private final Setting<List<String>> messages = sgGeneral.add(new StringListSetting.Builder()
        .name("mensajes")
        .description("Mensajes que se enviarán al matar a alguien.")
        .defaultValue(List.of(
            "¡EZ {player}! Meteor Addon on top.",
            "Llora un poco más, {player}."
        ))
        .build()
    );

    public AutoEZ() {
        super(Categories.Misc, "auto-ez", "Envía un mensaje cuando matas a alguien.");
    }

    @EventHandler
    private void onAttack(AttackEntityEvent event) {
        if (event.entity instanceof PlayerEntity target) {
            if (target.isDead() || target.getHealth() <= 0) {
                // Obtenemos la lista actual de los settings
                List<String> msgList = messages.get();
                if (msgList.isEmpty()) return;

                String randomMsg = msgList.get(new Random().nextInt(msgList.size()));
                String finalMsg = randomMsg.replace("{player}", target.getEntityName());
                
                mc.player.networkHandler.sendChatMessage(finalMsg);
            }
        }
    }
}
