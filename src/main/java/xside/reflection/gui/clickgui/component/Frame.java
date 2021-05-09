package xside.reflection.gui.clickgui.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import xside.reflection.Main;
import xside.reflection.gui.clickgui.component.components.Button;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import xside.reflection.utils.ColorUtil;

public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	public static int color;
	
	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 0;
		this.y = 60;
		this.dragX = 0;
		this.barHeight = 12;
		this.open = true;
		this.isDragging = false;
		int tY = this.barHeight;
		//Frame.color = ColorUtil.rainbow(300);
		for(Module mod : Main.instance.moduleManager.getModulesInCategory(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void renderFrame(FontRenderer fontRenderer) { //TODO TRANSPERMENT BUTTONS AND GRADIENT IN FRAME 0x9F000000
		//Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, Visual.getIntFromColor(Visual.rainbow().getRed(),Visual.rainbow().getGreen(),Visual.rainbow().getBlue()));
		if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 31 && Calendar.getInstance().get(Calendar.MONTH) == 11)
		{
			Gui.drawRect(this.x, this.y-1, this.x + this.width, this.y, -1);
		}
		else {
			Gui.drawRect(this.x, this.y-1, this.x + this.width, this.y, Color.MAGENTA.getRGB());
		}
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + 12, 0xFF111111);
		//Gui.drawRect(this.x+1, this.y+1, this.x + this.width-1, this.y + 11, 0x9F000000);
		//GuiUtils.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, this.y,-2130706433, 16777215);
		if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 31 && Calendar.getInstance().get(Calendar.MONTH) == 11)
		{
			if(this.category == Category.Combat)
				fontRenderer.drawStringWithShadow("§b⚔ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Player)
				fontRenderer.drawStringWithShadow("§b☠ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Movement)
				fontRenderer.drawStringWithShadow("§b☇ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.World)
				fontRenderer.drawStringWithShadow("§b☀ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Render)
				fontRenderer.drawStringWithShadow("§b❡ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Misc)
				fontRenderer.drawStringWithShadow("§b※ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
		}
		else {
			if(this.category == Category.Combat)
				fontRenderer.drawStringWithShadow("§d⚔ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Player)
				fontRenderer.drawStringWithShadow("§d☠ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Movement)
				fontRenderer.drawStringWithShadow("§d☇ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.World)
				fontRenderer.drawStringWithShadow("§d☀ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Render)
				fontRenderer.drawStringWithShadow("§d❡ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
			if(this.category == Category.Misc)
				fontRenderer.drawStringWithShadow("§d※ §r"+this.category.name(), this.x + 5, this.y + 2, -1);
		}
		//.,fontRenderer.drawStringWithShadow(this.open ? "-" : "+", this.x + 5, this.y + 2, -1);
		/*
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		fontRenderer.drawStringWithShadow(this.category.name(), (this.x) * 2 + 5, (this.y + + 1f) * 2 + 5, 0xFFFFFF);
		fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (this.x + this.width - 12.5f) * 2 + 5, (this.y + 1f) * 2 + 5, -1);
		GL11.glPopMatrix();*/
		if(this.open) {
			if(!this.components.isEmpty()) {
				//Gui.drawRect(this.x, this.y + this.barHeight, this.x + 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());
				//Gui.drawRect(this.x, this.y + this.barHeight + (12 * components.size()), this.x + this.width, this.y + this.barHeight + (12 * components.size()) + 1, new Color(0, 200, 20, 150).getRGB());
				//Gui.drawRect(this.x + this.width, this.y + this.barHeight, this.x + this.width - 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());
				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
}
