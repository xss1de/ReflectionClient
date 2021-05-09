package xside.reflection.utils;

import java.awt.*;

public class ColorUtil{
	
    /*public static Color rainbow() {
        long offset = 999999999999L;
        float fade = 1.0f;
        float hue = (float) (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color c = new Color((int) color);
        return new Color((float) c.getRed() / 255.0f * fade, (float) c.getGreen() / 255.0f * fade, (float) c.getBlue() / 255.0f * fade, (float) c.getAlpha() / 255.0f);
    }*/
    public static int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
    public static int rainbow(int delay) {
    	double rState = Math.ceil((System.currentTimeMillis() + delay)/20.0);
    	rState %= 360;
    	return Color.getHSBColor((float) (rState / 360.0f), 0.6f, 1f).getRGB();
    }
}
