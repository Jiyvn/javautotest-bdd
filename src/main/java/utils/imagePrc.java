package utils;


import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class imagePrc {

    public static List<Rect> getTextContours(File imageFile){
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        System.load("/path/to/lib"+Core.NATIVE_LIBRARY_NAME+".dylib");
        Mat img = Imgcodecs.imread(imageFile.getAbsolutePath());
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_RGB2GRAY);

        Mat gradient = new Mat();
        Mat morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3,3));
        Imgproc.morphologyEx(gray, gradient, Imgproc.MORPH_GRADIENT , morphKernel);

        // binarize
        Mat opening = new Mat();
        Imgproc.threshold(gradient, opening, 0.0, 255.0, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1.5,2));

        // horizontally oriented regions
        Mat closing = new Mat();
        Imgproc.morphologyEx(opening, closing, Imgproc.MORPH_CLOSE , morphKernel);

        // find contours
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(closing, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE, new org.opencv.core.Point(0, 0));

        List<Rect> contoursRect = new ArrayList<>();
        Mat mask = Mat.zeros(opening.size(), CvType.CV_8UC1);
        // filter contours
        for(int i = 0; i < contours.size(); i++) {
            Rect rect = Imgproc.boundingRect(contours.get(i));
            Mat maskROI = new Mat(mask, rect);
            maskROI.setTo(new Scalar(0, 0, 0));
            // fill the contour
            Imgproc.drawContours(mask, contours, i, new Scalar(255, 255, 255), Core.FILLED);
            // ratio of non-zero pixels in the filled region
            double r = (double)Core.countNonZero(maskROI)/(rect.width*rect.height);
            /*
             * r>.45: assume at least 45% of the area is filled if it contains text
             * rect: constraints on region size
             */
            if(r > .45 && (rect.height > 15 && rect.width > 5)) {
                contoursRect.add(rect);
                Imgproc.rectangle(img, new org.opencv.core.Point(rect.x,rect.y), new org.opencv.core.Point(rect.br().x-1, rect.br().y-1), new Scalar(0, 255, 0));
            }
            String output = imageFile.getName().split("\\.")[imageFile.getName().split("\\.").length-1]+"withTextRect.png";
            Imgcodecs.imwrite(output,img);
        }
        return contoursRect;
    }

    public static List<Rect> getOneTextContourOfEachRow(List<Rect> contours){
        List<Rect> crs = new ArrayList<>();
        for(int i = 0; i < contours.size(); i++) {
            Rect rect = contours.get(i);
            if (crs.stream().noneMatch(cr -> (cr.y - cr.height*1.2 <= rect.y && rect.y <= cr.y + cr.height*1.2)
                    || (rect.y - rect.height*1.2 <= cr.y && cr.y <= rect.y + rect.height*1.2))){
                crs.add(rect);
            }
        }
        return crs;
    }

    public static byte[] getSubImage(final BufferedImage wholeImage, int xInImg, int yInImg, int width, int height) throws IOException {
        BufferedImage subImage = wholeImage.getSubimage(xInImg, yInImg, width, height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(subImage, "png", baos);
        return baos.toByteArray();
    }
}
