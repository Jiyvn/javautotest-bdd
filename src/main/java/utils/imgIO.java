package utils;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

// not opencv
public class imgIO {

    public static BufferedImage readFileToImage(String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }

    public static BufferedImage readFileToImage(File imageFile) throws IOException {
        return ImageIO.read(imageFile);
    }

    public static byte[] readImageToBytes(BufferedImage bufferImage, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferImage, formatName, baos);
        return baos.toByteArray();
    }

    public static byte[] readFileToBytes(File imageFile) throws IOException {
//        return Files.readAllBytes(imageFile.toPath());
        return readImageToBytes(readFileToImage(imageFile), "png");
    }

    public static BufferedImage writeBytesToImage(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    public static void writeImageToFile(BufferedImage bufferedImage, File output) throws IOException {
        ImageIO.write(bufferedImage, "png", output);
    }

    public static void writeBytesToFile(byte[] bytes, File output) throws IOException {
        writeImageToFile(writeBytesToImage(bytes), output);
    }

    public static byte[] getSubImage(final BufferedImage wholeImage, Rectangle rect) throws IOException {
        BufferedImage subImage = wholeImage.getSubimage(rect.x, rect.y, rect.width, rect.height);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(subImage, "png", baos);
//        return baos.toByteArray();
        return readImageToBytes(subImage, "png");
    }
    public static byte[] getSubImage(final BufferedImage wholeImage, int xInImg, int yInImg, int width, int height) throws IOException {
        BufferedImage subImage = wholeImage.getSubimage(xInImg, yInImg, width, height);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(subImage, "png", baos);
//        return baos.toByteArray();
        return readImageToBytes(subImage, "png");
    }
}
