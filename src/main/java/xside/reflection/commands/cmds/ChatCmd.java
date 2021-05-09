package xside.reflection.commands.cmds;

import net.minecraftforge.fml.client.FMLClientHandler;
import xside.reflection.commands.Command;
import xside.reflection.utils.ChatUtil;

@Command.Declaration(name = "Chat", syntax = "chat clear", alias = {"chat"})
public class ChatCmd extends Command{
	
	@Override
	public void onCommand(String command, String[] args) {
		if(args[0].equalsIgnoreCase("clear")){
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().clearChatMessages(true);
		}
		else {
			sendSyntaxError(this);
		}
	}
 
}
