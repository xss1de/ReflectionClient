package xside.reflection.commands.cmds;

import org.lwjgl.input.Keyboard;

import xside.reflection.Main;
import xside.reflection.commands.Command;
import xside.reflection.modules.misc.DiscordRpc;
import xside.reflection.utils.ChatUtil;

@Command.Declaration(name = "Discord", syntax = "discord set [First_line] [Second_line] (use _ for spaces) / reset", alias = {"discord", "ds", "rpc"})
public class DiscordCmd extends Command{

	@Override
	public void onCommand(String command, String[] args) {
		/*if(args[0].equalsIgnoreCase("set")){
			args[2] = args[2].toUpperCase();
			int key = Keyboard.getKeyIndex(args[2]);
			if(Main.instance.moduleManager.getModule("DiscordRPC").isToggled())
			{
				DiscordRpc.update(args[1], args[2]);
				ChatUtil.sendChatMessage("Updated.");
			}
			else {
				ChatUtil.sendErrorChatMessage("Turn on the DiscordRPC module.");
			}
				
		}
		else if(args[0].equalsIgnoreCase("reset")){
			if(Main.instance.moduleManager.getModule("ClickGui").isToggled())
			{
				DiscordRpc.update("Utility mod for 1.12.2","Created by xside");
				ChatUtil.sendChatMessage("Text cleared.");
			}
			else {
				ChatUtil.sendErrorChatMessage("Turn on the DiscordRPC module.");
			}
		}*/
		if(args[0].equalsIgnoreCase("set")){
			ChatUtil.sendErrorChatMessage("Set working!");
				
		}
		else if(args[0].equalsIgnoreCase("reset")){
			ChatUtil.sendErrorChatMessage("Reset working!");
		}
	}
	
}
