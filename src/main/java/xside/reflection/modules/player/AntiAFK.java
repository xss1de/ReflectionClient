package xside.reflection.modules.player;

import xside.reflection.Main;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import xside.reflection.modules.movement.Fly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiAFK extends Module {

    public AntiAFK(){
        super("AntiAFK", "Jupms automatically", Category.Player);
    }

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
        if(Minecraft.getMinecraft().player != null && !Minecraft.getMinecraft().player.capabilities.isFlying && !Main.instance.moduleManager.getModule("Fly").isToggled()){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
        }
    }

    @Override
    public void onDisable(){
        super.onDisable();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
    }
}
