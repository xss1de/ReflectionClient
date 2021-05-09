package xside.reflection.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen; //Minecraft.getMinecraft().objectMouseOver
import net.minecraft.client.gui.GuiScreenAddServer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;

public class NicknameGui extends GuiScreen{
	private final GuiScreen parentScreen;
    private GuiTextField nameField;
    
    public NicknameGui()
    {
        this.parentScreen = new GuiMultiplayer(new GuiMainMenu());
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Username Changer", this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, "Nickname:", this.width / 2 - 100, this.width / 2 - 180, 10526880);
        this.nameField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    @Override
    public void updateScreen()
    {
        this.nameField.updateCursorCounter();
        super.updateScreen();
    }
    @Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Done"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Cancel"));
        this.nameField = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, this.height / 4 + 72, 200, 20);
        this.nameField.setFocused(true);
        this.nameField.setText(mc.getSession().getUsername());
        super.initGui();
    }
    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }
    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1)
        {
            mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
        }
        else if (button.id == 0)
        {
        	
        }
    }
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.nameField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == 1)
        {
        	this.actionPerformed(this.buttonList.get(1));
        }
        
        if (keyCode == 15)
        {
            this.nameField.setFocused(!this.nameField.isFocused());
        }

        if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
  
}
