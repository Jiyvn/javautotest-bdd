package utils.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.nio.file.Files;

// not opencv
public class imgIO {

    public static BufferedImage imageToBufferedImage(Image image){
//        if (image instanceof BufferedImage) {
//            return (BufferedImage) image;
//        }
        BufferedImage bi = new BufferedImage
                (image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        return bi;

    }

    public static Image bufferedImageToImage(BufferedImage bufferedImage) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bufferedImageToBytes(bufferedImage)));
    }

    public static BufferedImage fileToBufferedImage(String imagePath) throws IOException {
        return ImageIO.read(new File(imagePath));
    }

    public static BufferedImage fileToBufferedImage(File imageFile) throws IOException {
        return ImageIO.read(imageFile);
    }

    public static void bufferedImageToFile(BufferedImage bufferedImage, File output) throws IOException {
        ImageIO.write(bufferedImage, "png", output);
    }

    public static byte[] bufferedImageToBytes(BufferedImage bufferImage, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferImage, formatName, baos);
//        baos.flush();
        return baos.toByteArray();
    }

    public static byte[] bufferedImageToBytes(BufferedImage bufferImage) {
        return ((DataBufferByte) bufferImage.getRaster().getDataBuffer()).getData();
    }

    public static BufferedImage bytesToBufferedImage(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    public static byte[] fileToBytes(File imageFile, String formatName) throws IOException {
//        return readImageToBytes(readFileToImage(imageFile), "png");
        return bufferedImageToBytes(fileToBufferedImage(imageFile), formatName);
    }

    public static byte[] fileToBytes(File imageFile) throws IOException {
        return Files.readAllBytes(imageFile.toPath());
    }

    public static void bytesToFile(byte[] bytes, File output) throws IOException {
        bufferedImageToFile(bytesToBufferedImage(bytes), output);
    }

    public static void bytesToFileViaStream(byte[] bytes, File output) throws IOException {
        if (!new File(output.getParent()).exists()) {
            new File(output.getParent()).mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(bytes);
        }
    }
    public static File createTempPng(byte[] bytes, String fileName) throws IOException {
        return createTempFile(bytes, fileName, ".png");
    }

    public static File createTempFile(byte[] bytes, String fileName, String fileType) throws IOException {
        File tmpFIle = File.createTempFile(fileName, fileType);
        FileOutputStream fos = new FileOutputStream(tmpFIle);
        fos.write(bytes);
        fos.close();
        return tmpFIle;
    }

    public static byte[] getSubImage(final BufferedImage wholeImage, Rectangle rect) throws IOException {
        BufferedImage subImage = wholeImage.getSubimage(rect.x, rect.y, rect.width, rect.height);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(subImage, "png", baos);
//        return baos.toByteArray();
        return bufferedImageToBytes(subImage, "png");
    }
    public static byte[] getSubImage(final BufferedImage wholeImage, int xInImg, int yInImg, int width, int height) throws IOException {
        BufferedImage subImage = wholeImage.getSubimage(xInImg, yInImg, width, height);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(subImage, "png", baos);
//        return baos.toByteArray();
        return bufferedImageToBytes(subImage, "png");
    }
}
