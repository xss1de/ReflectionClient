package xside.reflection.gui.clickgui;

import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.sun.jna.platform.unix.X11.Font;

import javafx.scene.control.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import xside.reflection.Main;
import xside.reflection.gui.clickgui.component.Component;
import xside.reflection.gui.clickgui.component.Frame;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import xside.reflection.modules.render.ClickGui;

public class ClickGuiManager extends GuiScreen {

	public static ArrayList<Frame> frames;
	public static int color;
	private ResourceLocation clickGuiLogo = new ResourceLocation(Main.MODID, "textures/cl.png");
	private ResourceLocation newYearClickGuiLogo = new ResourceLocation(Main.MODID, "textures/newyearcl.png");
	private ArrayList<Effect> effectList = new ArrayList<Effect>();
	
	public ClickGuiManager() {
		this.frames = new ArrayList<Frame>();
		int frameX = 2;
		for(Category category : Category.values()) {
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth();
		}
		
		//31 12
		if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 31 && Calendar.getInstance().get(Calendar.MONTH) == 11)
		{
	        Random random = new Random();
	        for (int i = 0; i < 100; ++i)
	        {
	            for (int y = 0; y < 3; ++y)
	            {
	            	Effect snow = new Effect(25 * i, y * -50, random.nextInt(3) + 1, random.nextInt(2)+1);
	            	effectList.add(snow);
	            }
	        }
		}
	}
	
	@Override
	public void initGui() {
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if(Main.instance.settingsManager.getSettingByName(Main.instance.moduleManager.getModule("ClickGui"), "Background").getValBoolean())
			drawDefaultBackground();
		final ScaledResolution res = new ScaledResolution(mc);
		if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 31 && Calendar.getInstance().get(Calendar.MONTH) == 11)
		{
			//System.out.println(Calendar.getInstance().get(Calendar.MONTH));
			clickGuiLogo = newYearClickGuiLogo;
	        if (!effectList.isEmpty())
	        {
	        	effectList.forEach(snow -> snow.Update(res));
	        }
		}
		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		ScaledResolution sr = new ScaledResolution(mc);
		mc.fontRenderer.drawStringWithShadow("Press \"R\" to restore the position of frames", (sr.getScaledWidth()-mc.fontRenderer.getStringWidth("Press \"R\" to restore the position of frames"))*2+mc.fontRenderer.getStringWidth("Press \"R\" to restore the position of frames")-5, (sr.getScaledHeight()-mc.fontRenderer.FONT_HEIGHT-1)*2, -1);
		mc.renderEngine.bindTexture(clickGuiLogo);
		GL11.glPopMatrix();
		drawScaledCustomSizeModalRect((this.width-150)/2, -6, 0, 0, 256, 256, 150, 78, 256, 256);
		for(Frame frame : frames) {
			frame.renderFrame(this.fontRenderer);
			frame.updatePosition(mouseX, mouseY);
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		//if()
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 19) {
			this.frames = new ArrayList<Frame>();
			int frameX = 2;
			for(Category category : Category.values()) {
				Frame frame = new Frame(category);
				ScaledResolution sr = new ScaledResolution(mc);
				if(sr.getScaledWidth() < frame.getWidth()*7)
				{
					
				}
				else{
					frame.setX(frameX);
					frames.add(frame);
					frameX += frame.getWidth();
				}
			}
        }
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}
