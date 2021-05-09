package xside.reflection.commands;

import java.util.ArrayList;

import xside.reflection.commands.cmds.ChatCmd;
import xside.reflection.commands.cmds.DiscordCmd;
import xside.reflection.utils.ChatUtil;

public class CommandManager {
    private static String commandPrefix = ".";
    public static final ArrayList<Command> commands = new ArrayList<>();
    
    public void init() {
		addCommand(new DiscordCmd());
		addCommand(new ChatCmd());
	}
	
	public static void addCommand(Command command) {
        commands.add(command);
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static String getCommandPrefix() {
        return commandPrefix;
    }

    public static void setCommandPrefix(String prefix) {
        commandPrefix = prefix;
    }

    public static boolean isValidCommand = false;

    public static void callCommand(String input) {
        String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String command1 = split[0];
        String args = input.substring(command1.length()).trim();

        isValidCommand = false;

        commands.forEach(command -> {
            for (String string : command.getAlias()) {
                if (string.equalsIgnoreCase(command1)) {
                    isValidCommand = true;
                    try {
                        command.onCommand(args, args.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                    } catch (Exception e) {
                    	ChatUtil.sendChatMessage(command.getSyntax());
                    }
                }
            }
        });

        if (!isValidCommand) {
        	ChatUtil.sendErrorChatMessage("Command not found!");
        }
    }
}
