package xside.reflection.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.network.Packet;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import xside.reflection.Main;

public class Module {
    protected static Minecraft mc = Minecraft.getMinecraft();
    protected static FontRenderer fr = mc.fontRenderer;
    private String name, description;
    private int key;
    private Category category;
    private boolean toggled;
    public boolean visible = true;
    
    public Module(String name, String description, Category category) {
        super();
        this.name = name;
        this.description = description;
        this.key = 0;
        this.category = category;
        this.toggled = false;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
		if (Main.instance.saveLoad != null) {
			Main.instance.saveLoad.save();
		}
    }
    public void delKey() {
        this.key = 0;
    }
    public boolean isToggled() {
        return toggled;
    }
    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if(this.toggled){
            this.onEnable();
        }
        else{
            this.onDisable();
        }
		if (Main.instance.saveLoad != null) {
			Main.instance.saveLoad.save();
		}
    }
    public void toggle(){
        this.toggled = !this.toggled;
        if(this.toggled){
            this.onEnable();
        }
        else{
            this.onDisable();
        }
		if (Main.instance.saveLoad != null) {
			Main.instance.saveLoad.save();
		}
    }
    public void onEnable(){
        MinecraftForge.EVENT_BUS.register(this);
    }
    public void onDisable(){
        MinecraftForge.EVENT_BUS.unregister(this);
    }
    public String getName() {
        return this.name;
    }
    public Category getCategory() {
        return category;
    }
}
