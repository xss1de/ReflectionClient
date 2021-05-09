package xside.reflection.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import java.lang.reflect.Field;

public class PlayerUtil {
	public static float getCurBlockDamageMP() {
		float getCurBlockDamageMP = 0;
		try {
			Field field = PlayerControllerMP.class.getDeclaredField(MappingUtil.curBlockDamageMP);
			field.setAccessible(true);
			getCurBlockDamageMP =  field.getFloat(Minecraft.getMinecraft().playerController);	
		} catch (Exception ex) {}
		return getCurBlockDamageMP;
	}
	public static void setBlockHitDelay(final int blockHitDelay) {
    	try {
    		Field field = PlayerControllerMP.class.getDeclaredField(MappingUtil.blockHitDelay);
        	field.setAccessible(true);
        	field.setInt(Minecraft.getMinecraft().playerController, blockHitDelay);
    	} catch (Exception ex) {}
    }
}
