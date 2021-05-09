package xside.reflection.modules.render;

import xside.reflection.Main;
import xside.reflection.gui.clickgui.Setting;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;

import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {
    public ClickGui(){
        super("ClickGui", "Allows you to enable and disable modules", Category.Render);
        Main.instance.settingsManager.rSetting(new Setting("Background",this,true));
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable(){
        mc.displayGuiScreen(Main.instance.clickGui);
        this.setToggled(false);
    }
}
