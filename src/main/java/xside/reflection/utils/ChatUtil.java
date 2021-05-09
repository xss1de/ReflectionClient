package xside.reflection.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import xside.reflection.Main;

public class ChatUtil {
	
    public static String CHATPREFIX = "§a≺Ｒ≻ §r";
    public static String ERRCHATPREFIX = "§c≺Ｒ≻ §r";
    
	public static void sendChatMessage(String text) {
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(CHATPREFIX+text));
	}
	public static void sendErrorChatMessage(String text) {
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(ERRCHATPREFIX+"§c"+text));
	}
}
