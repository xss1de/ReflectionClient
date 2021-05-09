package xside.reflection.modules.render;

import static net.minecraft.client.gui.Gui.drawRect;

import java.awt.Color;
import java.io.Console;
import java.text.DecimalFormat;
import java.util.Collection;

import org.lwjgl.opengl.GL11;

import com.sun.jna.platform.unix.X11.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xside.reflection.Main;
import xside.reflection.gui.clickgui.Setting;
import xside.reflection.modules.Category;
import xside.reflection.modules.Module;

public class EnemyInfo extends Module {
	
	private ResourceLocation hpbar = new ResourceLocation(Main.MODID, "textures/healthbar.png");
	private boolean show;
	private String enemyNickname;
	private double enemyHp;
	private double enemyDistance;
	private Entity entity;
	private EntityPlayer entityPlayer;
	private int hpbarwidth;
	
    public EnemyInfo() {
        super("EnemyInfo", "Displays information about the enemy", Category.Render);
    }
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e){
    	RayTraceResult objectMouseOver = mc.objectMouseOver;
    	if (objectMouseOver != null)
    	{
    	    if (objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY)
    	    {
    	    	entity = objectMouseOver.entityHit;
    	    	if(entity instanceof EntityPlayer)
    	    	{
    	    		entityPlayer = (EntityPlayer) entity;
        	    	enemyNickname = entityPlayer.getName();
        	    	enemyHp = entityPlayer.getHealth();
        	    	enemyDistance = entityPlayer.getDistance(mc.player);
        	    	show = true;
    	    	}
    	    }
    	    else {
    	    	show = false;
    	    }
    	}
	    else {
	    	show = false;
	    }
    }
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent egoe){
        if(!egoe.getType().equals(egoe.getType().TEXT)){
            return;
        }
        if(show && mc.world != null && mc.player != null) {
        	DecimalFormat decimalFormat = new DecimalFormat("###.#");
            FontRenderer fr = mc.fontRenderer;
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            drawRect(sr.getScaledWidth()/2+120, sr.getScaledHeight()/2+25, sr.getScaledWidth()/2+20,  sr.getScaledHeight()/2+90, 0x4F000000);
            //drawRect(sr.getScaledWidth()/2+120, sr.getScaledHeight()/2+83, sr.getScaledWidth()/2+20,  sr.getScaledHeight()/2+85, Color.GREEN.getRGB());
            fr.drawString(enemyNickname, sr.getScaledWidth()/2+60, sr.getScaledHeight()/2+30, -1);
    		GL11.glPushMatrix();
    		GL11.glScalef(0.5f,0.5f, 0.5f);
    		fr.drawString("HP: " + Math.round(enemyHp), (sr.getScaledWidth()/2+60)*2, (sr.getScaledHeight()/2+40)*2, -1);
    		fr.drawString("Distanse: " + decimalFormat.format(enemyDistance), (sr.getScaledWidth()/2+60)*2, (sr.getScaledHeight()/2+45)*2, -1);
    		GL11.glPopMatrix();
            drawEntityOnScreen(sr.getScaledWidth()/2+40, sr.getScaledHeight()/2+80, 25, 0, 0, entityPlayer);
    		mc.renderEngine.bindTexture(hpbar);
    		Gui.drawScaledCustomSizeModalRect(sr.getScaledWidth()/2+20, sr.getScaledHeight()/2+89, 0, 0, 256, 256,sr.getScaledWidth()/2-sr.getScaledWidth()/2+100, 1, 256, 256);
        }
        //System.out.println(sr.getScaledHeight()/2+82);
    }
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        if(mc.world != null && mc.player != null) {
	        GlStateManager.enableColorMaterial();
	        GlStateManager.pushMatrix();
	        GlStateManager.translate((float)posX, (float)posY, 50.0F);
	        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
	        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
	        float f = ent.renderYawOffset;
	        float f1 = ent.rotationYaw;
	        float f2 = ent.rotationPitch;
	        float f3 = ent.prevRotationYawHead;
	        float f4 = ent.rotationYawHead;
	        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
	        RenderHelper.enableStandardItemLighting();
	        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
	        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
	        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
	        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
	        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
	        ent.rotationYawHead = ent.rotationYaw;
	        ent.prevRotationYawHead = ent.rotationYaw;
	        GlStateManager.translate(0.0F, 0.0F, 0.0F);
	        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
	        rendermanager.setPlayerViewY(180.0F);
	        rendermanager.setRenderShadow(false);
	        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
	        rendermanager.setRenderShadow(true);
	        ent.renderYawOffset = f;
	        ent.rotationYaw = f1;
	        ent.rotationPitch = f2;
	        ent.prevRotationYawHead = f3;
	        ent.rotationYawHead = f4;
	        GlStateManager.popMatrix();
	        RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.disableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        }
    }
}
