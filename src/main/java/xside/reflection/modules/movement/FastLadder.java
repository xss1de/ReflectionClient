package xside.reflection.modules.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xside.reflection.Main;
import xside.reflection.gui.clickgui.Setting;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;

public class FastLadder extends Module{
	public FastLadder() {
		super("FastLadder", "Allows you to climb up ladders faster", Category.Player);
		Main.instance.settingsManager.rSetting(new Setting("Speed", this, 0.3, 0.0, 0.8, false));
	}
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent e){
    	if(mc.world == null) return;
		if(!mc.player.isOnLadder() || mc.player.moveForward == 0 && mc.player.moveStrafing == 0) return;
		mc.player.motionY = Main.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
    }
}
