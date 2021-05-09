package xside.reflection.utils.notifications;
import java.awt.Color;

import net.minecraft.client.gui.ScaledResolution;
import xside.reflection.Main;
import net.minecraft.client.Minecraft;

import static org.lwjgl.opengl.GL11.*;

public class NotificationUtil {
	private final ScaledResolution resolution;
	private double x, y, oldX, oldY, width, stayBar;
	private boolean done;
	private int stayTime;
	private final String text;
	private final NotificationType type;
	private long time;
	
	public NotificationUtil(final NotificationType type, final String text, final int stayTime) {
		super();
		this.resolution = new ScaledResolution(Minecraft.getMinecraft());
		this.x = (resolution.getScaledWidth() - 2);
		this.y = (resolution.getScaledHeight() - 2);
		this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth(text) + 8;

		this.text = text;
		this.stayTime = stayTime;
		this.stayBar = stayTime;
		this.type = type;
		this.done = false;
		this.time = 0;
	}
	
	public void draw(final float prevY) {
		if (this.done) {
			this.oldX = this.x;
			++this.x;
			++this.y;
		}
		
		if (!this.getMinX() && !this.done) {
			this.reset();
			this.oldX = this.x;
			--this.x;
		} else if (this.hasTimePassed(this.stayTime)) {
			this.done = true;
		}
		
		if (this.x < resolution.getScaledWidth() - 2 - this.width) {
			this.oldX = this.x;
			++this.x;
		}
		if (this.y != prevY) {
			if (this.y > prevY) {
				this.oldY = this.y;
				--this.y;
			} else if (this.y < prevY) {
				this.oldY = this.y;
				++this.y;
			}
		}
		
		if (this.getMinX() && !this.done)
			this.stayBar = (float) (this.stayTime - this.getTime());
		
		final double finishedX = this.oldX + (this.x - this.oldX);
		final double finishedY = this.oldY + (this.y - this.oldY);
		
		int color;
		if (type == NotificationType.INFO)			color = new Color(41, 46, 52, 220).getRGB();
		else if (type == NotificationType.WARNING) 	color = new Color(248, 252, 0, 255).getRGB();
		else										color = new Color(255, 0, 0, 255).getRGB();

		glPushMatrix();
		drawRect(GL_LINE_LOOP, finishedX, finishedY, (finishedX + this.width), (finishedY + 16.0f), color);
		drawRect(GL_QUADS, finishedX, finishedY, (finishedX + this.width), (finishedY + 16.0f), new Color(51, 56, 62, 200).brighter().getRGB());
		drawRect(GL_QUADS, finishedX + 0.5, finishedY + 0.5, (double)(finishedX + 3.0f), finishedY + 15.5, color);
		Minecraft.getMinecraft().fontRenderer.drawString(this.text, (int)finishedX + 5, (int)finishedY + 5, new Color(124, 208, 0).brighter().getRGB(), true);

		if (!this.getMinX() && !this.done) {
			drawRect(GL_QUADS, finishedX + 1, finishedY + 15.0f, this.width, 1.0f, color);
		} else {
			drawRect(GL_QUADS, finishedX + 1, finishedY + 14.5, ((this.width - 1.0f) / this.stayTime * this.stayBar), 1.0f, color);
		}
		glPopMatrix();

		if (this.deleteMinX()) {
			Main.instance.notificationManager.getNotifications().remove(this);
		}
	}

	private final void drawRect(final int mode, double left, double top, double right, double bottom, final int color) {
		if (left < right) {
			double i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			double j = top;
			top = bottom;
			bottom = j;
		}

		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_LINE_SMOOTH);
		glPushMatrix();

		glColor4f((color >> 16 & 255) / 255.0f, (color >> 8 & 255) / 255.0f, (color & 255) / 255.0f, (color >> 24 & 255) / 255.0f);
		if (mode != GL_QUADS) glLineWidth(2.f);

		glBegin(mode);
		glVertex2d(left, top);
		glVertex2d(left, bottom);
		glVertex2d(right, bottom);
		glVertex2d(right, top);
		glEnd();

		glPopMatrix();
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glDisable(GL_LINE_SMOOTH);
	}

	public boolean getMinX() {
		return this.x <= resolution.getScaledWidth() - 2 - this.width;
	}
	
	public boolean deleteMinX() {
		return this.x >= resolution.getScaledWidth() - 2 && this.done;
	}
	
	public long getTime() {
		return System.nanoTime() / 1000000L - this.time;
	}
	
	public boolean hasTimePassed(final long time) {
		return this.getTime() >= time;
	}
	
	public void reset() {
		this.time = (System.nanoTime() / 1000000L);
	}
}
