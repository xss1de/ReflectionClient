package xside.reflection.gui.clickgui.component.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import xside.reflection.Main;
import xside.reflection.gui.clickgui.Setting;
import xside.reflection.gui.clickgui.component.Component;
import xside.reflection.gui.clickgui.component.Frame;
import xside.reflection.gui.clickgui.component.components.sub.Checkbox;
import xside.reflection.gui.clickgui.component.components.sub.InfoButton;
import xside.reflection.gui.clickgui.component.components.sub.Keybind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import xside.reflection.gui.clickgui.component.components.sub.ModeButton;
import xside.reflection.gui.clickgui.component.components.sub.Slider;
import xside.reflection.gui.clickgui.component.components.sub.VisibleButton;
import xside.reflection.modules.Module;
import xside.reflection.modules.render.Hud;

public class Button extends Component {

	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	public int height;
	public FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.height = 12;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		int opY = offset + 12;
		if(Main.instance.settingsManager.getSettingsByMod(mod) != null) {
			for(Setting s : Main.instance.settingsManager.getSettingsByMod(mod)){
				if(s.isCombo()){
					this.subcomponents.add(new ModeButton(s, this, mod, opY));
					opY += 12;
				}
				if(s.isSlider()){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s.isCheck()){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));
		this.subcomponents.add(new VisibleButton(this, mod, opY));
		this.subcomponents.add(new InfoButton(this, mod, opY));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponent() {
		/*if(isHovered)
		{
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			Gui.drawRect(0, sr.getScaledHeight()-14, sr.getScaledWidth(),sr.getScaledHeight(), 0x4F000000);
			fr.drawStringWithShadow(this.mod.getDescription()+".", 3, sr.getScaledHeight()-12, -1);
		}*/
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? 0xFF222222 : 0xFF111111);
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.mod.getName(), (parent.getX() + 5), (parent.getY() + offset + 2), this.mod.isToggled() ? 0x00FF02DE : 0xFFFFFF); //0x999999
		if(this.subcomponents.size() > 3) {
			Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.open ? "§7▼" : "§7▶", (parent.getX()+parent.getWidth()-10), (parent.getY() + offset + 2), -1);
		}
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				//Gui.drawRect(parent.getX() + 0, parent.getY() + this.offset + 12, parent.getX() + 1, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), ClickGuiManager.color);
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
