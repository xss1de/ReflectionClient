package xside.reflection.modules.movement;

import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint extends Module {

    public Sprint(){
        super("Sprint", "Sprints automatically", Category.Movement);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e){
        if(Minecraft.getMinecraft().player != null){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }
    }

    @Override
    public void onDisable(){
        super.onDisable();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
    }
}
