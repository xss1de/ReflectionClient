package xside.reflection.modules.render;

import xside.reflection.modules.Category;
import xside.reflection.modules.Module;

public class Fullbright extends Module {
	
    public Fullbright(){
        super("Fullbright", "Makes maximum lighting", Category.Render);
    }
    @Override
    public void onEnable(){
        super.onEnable();
        mc.gameSettings.gammaSetting = 100;
    }
    
    @Override
    public void onDisable(){
        super.onDisable();
        mc.gameSettings.gammaSetting = 1.0f;
    }
}
