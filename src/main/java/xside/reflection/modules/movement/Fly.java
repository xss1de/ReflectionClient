package xside.reflection.modules.movement;

import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fly extends Module {
	
    public Fly(){
        super("Fly", "Allows you to fly in survival mode", Category.Movement);
    }
    
    @Override
    public void onDisable(){
        super.onDisable();
    }
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e){
    	
    }
}
