package xside.reflection.modules.movement;

import org.lwjgl.input.Keyboard;

import xside.reflection.gui.clickgui.ClickGuiManager;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class InvMove extends Module {
	
	private boolean ableToMove;
	
    public InvMove(){
        super("InvMove", "Allows you to move when GUI is open", Category.Movement);
    }
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e){
		if (!(mc.currentScreen instanceof GuiContainer) && !(mc.currentScreen instanceof ClickGuiManager)) {
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
			return;
		}
		
		double speed = 0.05;
		
		if(!mc.player.onGround)
			speed /= 4.0;
		
		handleJump();
		handleForward(speed);
		
		if(!mc.player.onGround)
			speed /= 2.0;
		
		handleBack(speed);
		handleLeft(speed);
		handleRight(speed);
		handleF5();
    }
    
	void moveForward(double speed) {
        float direction = getDirection();
        mc.player.motionX -= (double)(MathHelper.sin(direction) * speed);
        mc.player.motionZ += (double)(MathHelper.cos(direction) * speed);
	}
	
	void moveBack(double speed) {
        float direction = getDirection();
        mc.player.motionX += (double)(MathHelper.sin(direction) * speed);
        mc.player.motionZ -= (double)(MathHelper.cos(direction) * speed);
	}
	
	void moveLeft(double speed) {
        float direction = getDirection();
        mc.player.motionZ += (double)(MathHelper.sin(direction) * speed);
        mc.player.motionX += (double)(MathHelper.cos(direction) * speed);
	}
	
	void moveRight(double speed) {
        float direction = getDirection();
        mc.player.motionZ -= (double)(MathHelper.sin(direction) * speed);
        mc.player.motionX -= (double)(MathHelper.cos(direction) * speed);
	}
	
	
	void handleForward(double speed) {
		if(!Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) 
			return;
		moveForward(speed);
	}
	
	void handleBack(double speed) {
		if(!Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) 
			return;
		moveBack(speed);
	}
	
	void handleLeft(double speed) {
		if(!Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) 
			return;
		moveLeft(speed);
	}
	
	void handleRight(double speed) {
		if(!Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) 
			return;
		moveRight(speed);
	}

	
	void handleF5() {
		if(!Keyboard.isKeyDown(mc.gameSettings.keyBindTogglePerspective.getKeyCode())) 
			return;
		if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 0)
		{
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
		}
		else if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 1)
		{
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 2;
		}
		else if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2)
		{
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 3;
		}
		else if(Minecraft.getMinecraft().gameSettings.thirdPersonView == 3)
		{
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
		}
	}
	
	void handleJump() {
		if(mc.player.onGround && 
    			Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode())) 
    		mc.player.jump();
	}
	
	public static float getDirection() {
		float var1 = mc.player.rotationYaw;
		if (mc.player.moveForward < 0.0F) {
			var1 += 180.0F;
		}
		float forward = 1.0F;
		if (mc.player.moveForward < 0.0F) {
			forward = -0.5F;
		} else if (mc.player.moveForward > 0.0F) {
			forward = 0.5F;
		}
		if (mc.player.moveStrafing > 0.0F) {
			var1 -= 90.0F * forward;
		}
		if (mc.player.moveStrafing < 0.0F) {
			var1 += 90.0F * forward;
		}
		var1 *= 0.017453292F;
		return var1;
	}
}
