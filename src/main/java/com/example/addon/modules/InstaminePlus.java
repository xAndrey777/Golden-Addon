public class InstaminePlus extends Module {
    public InstaminePlus() {
        super(Categories.World, "instamine-plus", "Pica bloques instantáneamente con optimización de red.");
    }

    @EventHandler
    private void onStartBreaking(StartBreakingBlockEvent event) {
        Direction dir = Direction.UP;
        // Enviar paquete de inicio y fin en el mismo tick para "engañar" al servidor
        mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, event.blockPos, dir));
        mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, event.blockPos, dir));
        event.cancel(); // Detenemos la animación normal de picado
    }
}
