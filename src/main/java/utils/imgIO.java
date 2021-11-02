package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// not opencv
public class imgIO {

    public static byte[] getSubImage(final BufferedImage wholeImage, int xInImg, int yInImg, int width, int height) throws IOException {
        BufferedImage subImage = wholeImage.getSubimage(xInImg, yInImg, width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(subImage, "png", baos);
        return baos.toByteArray();
    }
}
