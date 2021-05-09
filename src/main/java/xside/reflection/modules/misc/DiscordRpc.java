package xside.reflection.modules.misc;

import xside.reflection.Main;
import xside.reflection.gui.clickgui.Setting;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRpc extends Module {
	
	private boolean running = true;
	private static long created = 0;
	
    public DiscordRpc(){
        super("DiscordRPC", "Discord game activity", Category.Misc);
    }
    
    public void start() {
    	
        this.created = System.currentTimeMillis();
        
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			
			@Override
			public void apply(DiscordUser user) {
				System.out.println(Main.NAME + " | DiscordRPC ready. User: " +user.username + "#" + user.discriminator);
			}
		}).build();
        
        DiscordRPC.discordInitialize("825924068250877962", handlers, true);
        update("Utility mod for 1.12.2","Created by xside");
        new Thread("Discord RPC callback") {
        	@Override
        	public void run() {
        		while(running) {
        			DiscordRPC.discordRunCallbacks();
        		}
        	}
        }.start();
    }
    
    @Override
    public void onDisable(){
    	 shutdown();
    }
    
    @Override
    public void onEnable(){
    	start();
    }
    
    public void shutdown() {
    	running = false;
    	DiscordRPC.discordShutdown();
    }
    
    public static void update(String firstLine, String secondLine) {
    	DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
    	b.setBigImage("large", "");
    	b.setDetails(firstLine);
    	b.setStartTimestamps(created);
    	DiscordRPC.discordUpdatePresence(b.build());
    }
}
