package xside.reflection.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import xside.reflection.modules.misc.DiscordRpc;
import xside.reflection.modules.movement.AntiWeb;
import xside.reflection.modules.movement.FastLadder;
import xside.reflection.modules.movement.Fly;
import xside.reflection.modules.movement.InvMove;
import xside.reflection.modules.movement.Parkour;
import xside.reflection.modules.render.Hud;
import xside.reflection.modules.movement.Sprint;
import xside.reflection.modules.player.AntiAFK;
import xside.reflection.modules.player.FastBreak;
import xside.reflection.modules.render.ClickGui;
import xside.reflection.modules.render.EnemyInfo;
import xside.reflection.modules.render.Fullbright;

public class ModuleManager {

    public ArrayList<Module> modules;

    public ModuleManager(){
        (modules = new ArrayList<Module>()).clear();
        this.modules.add(new ClickGui()); //sub sub components, custom font, animations
        this.modules.add(new Hud());
        this.modules.add(new Sprint());
        this.modules.add(new InvMove()); //F5
        this.modules.add(new DiscordRpc());
        this.modules.add(new Fly());
        this.modules.add(new Fullbright());
        this.modules.add(new FastLadder());
        this.modules.add(new AntiAFK());
        this.modules.add(new AntiWeb());
        this.modules.add(new FastBreak()); //fix
        this.modules.add(new Parkour());
        this.modules.add(new EnemyInfo());
    }
    public Module getModule(String name){
        Module mod;
        for(Module m : this.modules){
            if(m.getName().equalsIgnoreCase(name)){
                return m;
            }
        }
        return null;
    }
    public ArrayList<Module> getModuleList(){
        return this.modules;
    }
    public ArrayList<Module> getModulesInCategory(Category c){
        ArrayList<Module> mods = new ArrayList<Module>();
        for(Module m : this.modules){
            if(m.getCategory().name().equalsIgnoreCase(c.name())){
                mods.add(m);
            }
        }
        return mods;
    }
    
}
