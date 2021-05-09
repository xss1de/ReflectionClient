package xside.reflection.utils;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import com.google.common.primitives.UnsignedBytes;

public class TextureUtil {
    /**
     * Converts a BufferedImage into a ByteBuffer based on 32-bit values
     * in RGBA byte order
     *
     * @param image any type of BufferedImage
     * @return a ByteBuffer that contains the data in RGBA byte order
     */
    public static ByteBuffer convertToByteBuffer(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer data = ByteBuffer.allocateDirect(4 * width * height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);
                int r = (argb >> 16) & 0xFF;
                int g = (argb >> 8) & 0xFF;
                int b = argb & 0xFF;
                int a = (argb >> 24) & 0xFF;
                data.put(UnsignedBytes.checkedCast(r));
                data.put(UnsignedBytes.checkedCast(g));
                data.put(UnsignedBytes.checkedCast(b));
                data.put(UnsignedBytes.checkedCast(a));
            }
        }
        data.rewind();
        return data;
    }
}
