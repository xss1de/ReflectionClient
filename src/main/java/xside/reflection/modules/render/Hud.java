package xside.reflection.modules.render;

//import im.xside.inspire.gui.clickgui.Setting;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xside.reflection.Main;
import xside.reflection.gui.clickgui.Setting;
import xside.reflection.utils.ColorUtil;

import static net.minecraft.client.gui.Gui.drawRect;

import java.awt.Color;
import java.awt.Font;
import java.io.Console;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import ibxm.Player;

public class Hud extends Module {
	
	private static Integer transparencyKey;
	private static int mainColor;
	
    public Hud() {
        super("Hud", "In-game display", Category.Render);
        Main.instance.settingsManager.rSetting(new Setting("Arraylist",this,true));
        Main.instance.settingsManager.rSetting(new Setting("Coords",this,true));
        Main.instance.settingsManager.rSetting(new Setting("FPS",this,true));
        Main.instance.settingsManager.rSetting(new Setting("License",this,true));
        Main.instance.settingsManager.rSetting(new Setting("Background",this, 8, 1, 9,true));
        ArrayList<String> modes = new ArrayList<String>();
        modes.add("Rainbow");
        modes.add("Custom");
        Main.instance.settingsManager.rSetting(new Setting("Color",this,modes, "Color"));
        ArrayList<String> borders = new ArrayList<String>();
        borders.add("Left");
        borders.add("Right");
        borders.add("None");
        Main.instance.settingsManager.rSetting(new Setting("Borders",this,borders, "Borders"));
    }
	boolean upCoord;
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe){
        if(!egoe.getType().equals(egoe.getType().TEXT)){
            return;
        }
        setTKey();
        mainColor = 0xFFFF00FF;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if(Main.instance.settingsManager.getSettingByName(this, "Arraylist").getValBoolean()){
            int y = 0;
            final int[] counter = {1};
            for(Module mod : Main.instance.moduleManager.getModuleList()){
                if(mod.isToggled() && mod.visible){
                    /*drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-fr.getStringWidth(mod.getName())-4, y+fr.FONT_HEIGHT+2, transparencyKey);
                    fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 2, y+1, ColorUtil.rainbow(counter[0]*300));
                    drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-1, y+12, ColorUtil.rainbow(counter[0]*300));*/
                    drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-fr.getStringWidth(mod.getName())-3, y+fr.FONT_HEIGHT+2, transparencyKey);
                    if(Main.instance.settingsManager.getSettingByName(this, "Color").getValString() == "Rainbow") {
                    	if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Left") {
                    		drawRect(sr.getScaledWidth()-fr.getStringWidth(mod.getName())-3,y, sr.getScaledWidth()-fr.getStringWidth(mod.getName())-2, y+11, ColorUtil.rainbow(counter[0]*300));
                    		fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()), y+1, ColorUtil.rainbow(counter[0]*300));
                    	}
                    	if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Right") {
                    		drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-1, y+11, ColorUtil.rainbow(counter[0]*300));
                    		fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName())-1, y+1, ColorUtil.rainbow(counter[0]*300));
                    	}
                    	if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "None") {
                    		fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName())-1, y+1, ColorUtil.rainbow(counter[0]*300));
                    	}
                    }else {
                    	if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Left") {
                    		drawRect(sr.getScaledWidth()-fr.getStringWidth(mod.getName())-3,y, sr.getScaledWidth()-fr.getStringWidth(mod.getName())-2, y+11, mainColor);
                    		fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()), y+1, mainColor);
                    	}
                    	if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Right") {
                    		drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-1, y+11, mainColor);
                    		fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName())-1, y+1, mainColor);
                    	}
                    	if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "None") {
                    		fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName())-1, y+1, mainColor);
                    	}
                    }
                    y += fr.FONT_HEIGHT+2;
                    counter[0]++;
                }
            }
        }
        //fr.drawStringWithShadow(Main.NAME,2,2, ColorUtil.rainbow(300));
        if(Main.instance.settingsManager.getSettingByName(this, "Color").getValString() == "Rainbow") {
	        drawRect(0, 0, fr.getStringWidth(Main.NAME+ " §fv"+Main.VERSION)+4, fr.FONT_HEIGHT+1, transparencyKey);
	        if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Right")
	        	drawRect(1, 0, 0, fr.FONT_HEIGHT+1, ColorUtil.rainbow(300));
	        else if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Left")
	        	drawRect(fr.getStringWidth(Main.NAME+ " §fv"+Main.VERSION)+4, 0, fr.getStringWidth(Main.NAME+ " §fv"+Main.VERSION)+5, fr.FONT_HEIGHT+1, ColorUtil.rainbow(300));
	        fr.drawStringWithShadow(Main.NAME+ " §fv"+Main.VERSION, 3, 1, ColorUtil.rainbow(300));
        }
        else {
	        drawRect(0, 0, fr.getStringWidth(Main.NAME+ " §fv"+Main.VERSION)+4, fr.FONT_HEIGHT+1, transparencyKey);
	        if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Right")
	        	drawRect(1, 0, 0, fr.FONT_HEIGHT+1, mainColor);
	        else if(Main.instance.settingsManager.getSettingByName(this, "Borders").getValString() == "Left")
	        	drawRect(fr.getStringWidth(Main.NAME+ " §fv"+Main.VERSION)+4, 0, fr.getStringWidth(Main.NAME+ " §fv"+Main.VERSION)+5, fr.FONT_HEIGHT+1, mainColor);
	        fr.drawStringWithShadow(Main.NAME+ " §fv"+Main.VERSION, 3, 1, mainColor);
        }
		int y = fr.FONT_HEIGHT+2;
		if(Main.instance.settingsManager.getSettingByName(this, "License").getValBoolean()) {
	        //drawRect(0, 10, fr.getStringWidth(String.valueOf(mc.getDebugFPS())+" FPS")+3, fr.FONT_HEIGHT+11, transparencyKey);
            if(Main.instance.settingsManager.getSettingByName(this, "Color").getValString() == "Rainbow")
            	fr.drawStringWithShadow("License: §f" + Main.LicenseOwner,3,y, ColorUtil.rainbow(2*300));
            else {
            	fr.drawStringWithShadow("License: §f" + Main.LicenseOwner,3,y, mainColor);
            }
		}
		if(Main.instance.settingsManager.getSettingByName(this, "FPS").getValBoolean()) {
	        //drawRect(0, 10, fr.getStringWidth(String.valueOf(mc.getDebugFPS())+" FPS")+3, fr.FONT_HEIGHT+11, transparencyKey);§
			if(Main.instance.settingsManager.getSettingByName(this, "License").getValBoolean())
			{
	            if(Main.instance.settingsManager.getSettingByName(this, "Color").getValString() == "Rainbow")
	            	fr.drawStringWithShadow("FPS: §f" + String.valueOf(mc.getDebugFPS()),3,y+fr.FONT_HEIGHT+1, ColorUtil.rainbow(3*300));
	            else
	            	fr.drawStringWithShadow("FPS: §f" + String.valueOf(mc.getDebugFPS()),3,y+fr.FONT_HEIGHT+1, mainColor);
			}
			else if(!Main.instance.settingsManager.getSettingByName(this, "License").getValBoolean())
			{
				if(Main.instance.settingsManager.getSettingByName(this, "Color").getValString() == "Rainbow")
					fr.drawStringWithShadow("FPS: §f" + String.valueOf(mc.getDebugFPS()),3,y, ColorUtil.rainbow(2*300));
				else
					fr.drawStringWithShadow("FPS: §f" + String.valueOf(mc.getDebugFPS()),3,y, mainColor);
			}
		}
        if(Main.instance.settingsManager.getSettingByName(this, "Coords").getValBoolean()) {
        	DecimalFormat decimalFormat = new DecimalFormat("###.#");
            int yCoord = (int) ((sr.getScaledHeight() - fr.FONT_HEIGHT -2));
            if(!upCoord) {
            	yCoord = (int) ((sr.getScaledHeight() - fr.FONT_HEIGHT -2));
            } else if (upCoord){
            	yCoord = (int) ((sr.getScaledHeight() - fr.FONT_HEIGHT -15));
            }
            if(Main.instance.settingsManager.getSettingByName(this, "Color").getValString() == "Rainbow")
            	fr.drawStringWithShadow("XYZ: ", 2, yCoord, ColorUtil.rainbow(3*300));
            else {
            	fr.drawStringWithShadow("XYZ: ", 2, yCoord, mainColor);
            }
        	fr.drawStringWithShadow(decimalFormat.format(Minecraft.getMinecraft().player.posX) + ", " +decimalFormat.format(Minecraft.getMinecraft().player.posY) + ", " + decimalFormat.format(Minecraft.getMinecraft().player.posZ), fr.getStringWidth("XYZ: ")+1, yCoord, -1);
        }
    }
    
    @SubscribeEvent
    public void onChatGui(GuiOpenEvent event) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (event.getGui() instanceof GuiChat) {
        	upCoord = true;
        }
        else {
        	upCoord = false;
        }
    }
    
    public void setTKey()
    {
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 1)
    		transparencyKey = 0x1F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 2)
    		transparencyKey = 0x2F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 3)
    		transparencyKey = 0x3F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 4)
    		transparencyKey = 0x4F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 5)
    		transparencyKey = 0x5F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 6)
    		transparencyKey = 0x6F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 7)
    		transparencyKey = 0x7F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 8)
    		transparencyKey = 0x8F000000;
    	if(Main.instance.settingsManager.getSettingByName(this, "Background").getValDouble() == 9)
    		transparencyKey = 0x9F000000;
    }
}
/*package xside.reflection.modules.render;

//import im.xside.inspire.gui.clickgui.Setting;§
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xside.reflection.Main;
import xside.reflection.gui.clickgui.Setting;
import xside.reflection.utils.ColorUtil;

import static net.minecraft.client.gui.Gui.drawRect;

import java.awt.Color;

public class Hud extends Module {
    public Hud() {
        super("Hud", "In-game display", Category.Render);
        
        //Main.instance.settingsManager.rSetting(new Setting("Arrayldsfist",this,ColorUtil.rainbow(300)));
        Main.instance.settingsManager.rSetting(new Setting("Arraylist",this,true));
        Main.instance.settingsManager.rSetting(new Setting("Coordinates",this,true));
        Main.instance.settingsManager.rSetting(new Setting("Version",this,true));
        Main.instance.settingsManager.rSetting(new Setting("Rainbow Arraylist",this,true));
        Main.instance.settingsManager.rSetting(new Setting("Rainbow Logo",this,true));
        Main.instance.settingsManager.rSetting(new Setting("Rainbow Coordinates",this,false));
        Main.instance.settingsManager.rSetting(new Setting("Rainbow Version",this,false));
    }
    FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe){
        if(!egoe.getType().equals(egoe.getType().CROSSHAIRS)){
            return;
        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if(Main.instance.settingsManager.getSettingByName(this, "Arraylist").getValBoolean()){
            int y = 0;
            final int[] counter = {1};
            for(Module mod : Main.instance.moduleManager.getModuleList()){
                if(mod.isToggled() && mod.visible){
                    drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-fr.getStringWidth(mod.getName())-4, y+12, 0x8F000000);
                    if(Main.instance.settingsManager.getSettingByName(this, "Rainbow Arraylist").getValBoolean())
                        drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-1, y+12, ColorUtil.rainbow(counter[0]*300));
                    else if (!Main.instance.settingsManager.getSettingByName(this, "Rainbow Arraylist").getValBoolean()) //
                        drawRect(sr.getScaledWidth(),y, sr.getScaledWidth()-1, y+12, Color.white.getRGB());
                    if(Main.instance.settingsManager.getSettingByName(this, "Rainbow Arraylist").getValBoolean())
                        fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - fr.getStringWidth(mod.getName()) - 2, y+1, ColorUtil.rainbow(counter[0]*300));
                    else if(!Main.instance.settingsManager.getSettingByName(this, "Rainbow Arraylist").getValBoolean())
                        fr.drawStringWithShadow(mod.getName(),sr.getScaledWidth()-fr.getStringWidth(mod.getName())-2,y+1,0xFFFFFF);
                    y += fr.FONT_HEIGHT+3;
                    counter[0]++;
                }
            }
        }
        drawRect(0, 0, fr.getStringWidth(Main.NAME)+3, fr.FONT_HEIGHT+3, 0x8F000000);
        if(!Main.instance.settingsManager.getSettingByName(this, "Rainbow Logo").getValBoolean())
            fr.drawStringWithShadow(Main.NAME,2,2,0xFFFFFF);
        else if(Main.instance.settingsManager.getSettingByName(this, "Rainbow Logo").getValBoolean())
            fr.drawStringWithShadow(Main.NAME,2,2, ColorUtil.rainbow(300));
        if(Main.instance.settingsManager.getSettingByName(this, "Version").getValBoolean()) {
        	drawRect(0, fr.FONT_HEIGHT+3, fr.getStringWidth(Main.VERSION)+3, fr.FONT_HEIGHT+13, 0x8F000000);
        	if(!Main.instance.settingsManager.getSettingByName(this, "Rainbow Version").getValBoolean())
        		fr.drawStringWithShadow(Main.VERSION,2,12,0xFFFFFF);
        	else if(Main.instance.settingsManager.getSettingByName(this, "Rainbow Version").getValBoolean())
        		fr.drawStringWithShadow(Main.VERSION,2,12,ColorUtil.rainbow(300));
        }
        if(Main.instance.settingsManager.getSettingByName(this, "Coordinates").getValBoolean()) {
            String coord = "x:" + Math.round(Minecraft.getMinecraft().player.posX) + " y:" + Math.round(Minecraft.getMinecraft().player.posY) + " z:" + Math.round(Minecraft.getMinecraft().player.posZ);
            drawRect(0,sr.getScaledHeight(),fr.getStringWidth(coord)+3,sr.getScaledHeight()-fr.FONT_HEIGHT -5, 0x8F000000);
            if (Main.instance.settingsManager.getSettingByName(this, "Rainbow Coordinates").getValBoolean())
                fr.drawStringWithShadow(coord, 2, sr.getScaledHeight() - fr.FONT_HEIGHT -2, ColorUtil.rainbow(300));
            else if (!Main.instance.settingsManager.getSettingByName(this, "Rainbow Coordinates").getValBoolean())
                fr.drawStringWithShadow(coord, 2, sr.getScaledHeight() - fr.FONT_HEIGHT -2, 0xFFFFFF);
        }
    }
}

*/