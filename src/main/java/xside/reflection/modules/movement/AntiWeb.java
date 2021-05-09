package xside.reflection.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import xside.reflection.utils.MappingUtil;
import java.lang.reflect.Field;

public class AntiWeb extends Module{
	public AntiWeb() {
		super("AntiWeb", "Does not change walking speed in web", Category.Movement);
	}
	
    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent e){
		try {
			Field isInWeb = Entity.class.getDeclaredField(MappingUtil.isInWeb);
			isInWeb.setAccessible(true);
			isInWeb.setBoolean(mc.player, false);
		} catch (Exception ex) {
			this.setToggled(false);
		}
	}
}
