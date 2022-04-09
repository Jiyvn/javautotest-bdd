package utils.image;


import auto.Directory;
import org.opencv.core.*;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.xfeatures2d.SURF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// for opencv
public class imgCV {
    public static Logger log = LoggerFactory.getLogger(imgCV.class);

    static {
//        nu.pattern.OpenCV.loadShared(); // not supported jdk >=12
//        nu.pattern.OpenCV.loadLocally();  // use .dylib of openpnp
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // failed to load
//        System.load(Directory.PROJ_DIR+"/lib/lib"+Core.NATIVE_LIBRARY_NAME+".dylib");
        loadOpenCV();
    }

    public static void loadOpenCV(){
        String opencvLib = System.getProperty("OPENCV_LIB");
        if (opencvLib==null) {
            try{
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            }catch (java.lang.UnsatisfiedLinkError e) {
                log.info("failed to load Core.NATIVE_LIBRARY_NAME: "+Core.NATIVE_LIBRARY_NAME);
                log.debug("stack trace: "+Arrays.toString(e.getStackTrace()));
                String os = System.getProperty("os.name");
                String libName = os.startsWith("Mac") ? "lib" + Core.NATIVE_LIBRARY_NAME + ".dylib"
                        : os.startsWith("Windows") ? Core.NATIVE_LIBRARY_NAME + ".dll"
                        : "lib" + Core.NATIVE_LIBRARY_NAME + ".so";
                opencvLib = Directory.PROJ_DIR + "/lib/" + libName;
                log.info("try to load: "+opencvLib);
            }
        }
        System.load(opencvLib);
    }

    public static Mat bufferedImageToMat(BufferedImage bufferedImage, String formatName) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        ImageIO.write(bufferedImage, formatName, byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);
    }

//    public static Mat bufferedImageToMat(BufferedImage bufferedImage, String formatName) throws IOException {
//        return Imgcodecs.imdecode(new MatOfByte(imgIO.bufferedImageToBytes(bufferedImage, formatName)), Imgcodecs.IMREAD_UNCHANGED);
//    }

    public static Mat bufferedImageToMat(BufferedImage bufferedImage, int cvType) {
//        Mat matrix = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        Mat matrix = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), cvType);
        byte[] data = imgIO.bufferedImageToBytes(bufferedImage);
        matrix.put(0, 0, data);
        return matrix;
    }

    public static Mat bufferedImage2Mat(BufferedImage bufferedImage) {
        bufferedImage = to3ByteBGRImage(bufferedImage);
        Mat matrix = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        byte[] data = imgIO.bufferedImageToBytes(bufferedImage);
        matrix.put(0, 0, data);
        return matrix;
    }

    public static BufferedImage to3ByteBGRImage(BufferedImage bufferedImage) {
        BufferedImage convertedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        convertedImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
        return convertedImage;
    }

    public static BufferedImage matTobufferedImage(Mat matrix, String formatName)throws IOException {
        return imgIO.bytesToBufferedImage(matToBytes(matrix, formatName));
    }

    public static Image matTobufferedImage(Mat matrix){
        return HighGui.toBufferedImage(matrix);
    }

    public static byte[] matToBytes(Mat matrix, String formatName){
        MatOfByte mob = new MatOfByte();
//        Imgcodecs.imencode(".jpg", matrix, mob);
        Imgcodecs.imencode(formatName, matrix, mob);
        return mob.toArray();
    }

    public static BufferedImage getSubImage(Mat matrix, int x, int y, int width, int height) throws IOException {
        Rect rect = new Rect(x, y, width, height);
        // generate matrix of the interested region
        Mat subImg = new Mat(matrix, rect);
//        Imgcodecs.imwrite("subimage.jpg", subImg);
        return matTobufferedImage(subImg, "png");
    }

    public static Mat resizeTo(Mat image, int width, int height){
        Mat scale = new Mat();
        Size size = new Size(width,height);
        int interpolation = Imgproc.INTER_CUBIC;
//        int interpolation = Imgproc.INTER_AREA;
        Imgproc.resize(image, scale, size, 0, 0, interpolation);
        return scale;
    }

    public static List<Rect> getTextContours(Mat img,
                                             double ellipseWidth, double ellipseHeight,
                                             double rectWidth, double rectHeight,
                                             int minWidth, int minHeight){
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_RGB2GRAY);

        Mat gradient = new Mat();
        Mat morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(ellipseWidth,ellipseHeight));
        Imgproc.morphologyEx(gray, gradient, Imgproc.MORPH_GRADIENT , morphKernel);

        // binarize
        Mat opening = new Mat();
        Imgproc.threshold(gradient, opening, 0.0, 255.0, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
        morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(rectWidth,rectHeight));

        // horizontally oriented regions
        Mat closing = new Mat();
        Imgproc.morphologyEx(opening, closing, Imgproc.MORPH_CLOSE , morphKernel);

        // find contours
        List<MatOfPoint> contours = new ArrayList<>();
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
            if(r > .45 && (rect.height > minHeight && rect.width > minWidth)) {
                contoursRect.add(rect);
            }
        }
        return contoursRect;
    }

    public static List<Rect> getTextContours(File imageFile, String output, double ellipseWidth, double ellipseHeight,
                                             double rectWidth, double rectHeight,
                                             int minWidth, int minHeight){
        // read file
        Mat img = Imgcodecs.imread(imageFile.getAbsolutePath());
        List<Rect> contours = getTextContours(
                img,
                ellipseWidth, ellipseHeight,
                rectWidth, rectHeight,
                minWidth, minHeight
        );
        // output contours to file
        drawContoursRect(img, output, contours);
        return contours;
    }

    public static List<Rect> getTextContours(File imageFile, String output, int minWidth, int minHeight){
        // with fixed contour recognition algorithm parameters
        return getTextContours(
                imageFile, output,
                3, 3,
                1.5, 2,
                minWidth, minHeight
        );
    }

    public static List<Rect> getTextContours(File imageFile, String output){
        // with fixed contour result criteria parameters
        return getTextContours(imageFile, output, 5, 15);
    }

    public static List<Rect> getTextContours(File imageFile){
        String output = Directory.PROJ_DIR + "img/" + imageFile.getName().split("\\.")[imageFile.getName().split("\\.").length-2]+"withTextRect.png";
        return getTextContours(imageFile, output);
    }

    public static List<Rect> getOneTextContourOfEachRow(List<Rect> contours){
        List<Rect> crs = new ArrayList<>();
        for (Rect rect : contours) {
            if (crs.stream().noneMatch(cr -> (cr.y <= rect.y && rect.y <= cr.y + cr.height)
                    || (rect.y <= cr.y && cr.y <= rect.y + rect.height))) {
                crs.add(rect);
            }
        }
        return crs;
    }

    public static void drawContoursRect(File imageFile, List<Rect> contours){
        String output = Directory.PROJ_DIR + "img/" + imageFile.getName().split("\\.")[imageFile.getName().split("\\.").length-2]+"withContours.png";
        Mat img = Imgcodecs.imread(imageFile.getAbsolutePath());
        drawContoursRect(img, output, contours);
    }

    public static void drawContoursRect(Mat img, String output, List<Rect> contours){
        for (Rect rect: contours){
            Imgproc.rectangle(img, new org.opencv.core.Point(rect.x,rect.y), new org.opencv.core.Point(rect.br().x-1, rect.br().y-1), new Scalar(0, 255, 0));
            Imgcodecs.imwrite(output,img);
        }
    }

    public static boolean findSubImage(File subFile, File actualFile) {

        boolean found = false;
        Mat objectImage = Imgcodecs.imread(subFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
        Mat sceneImage = Imgcodecs.imread(actualFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);

        double hessianThreshold = 400;
        int nOctaves = 4, nOctaveLayers = 3;
        boolean extended = false, upright = true;
        SURF featureDetector = SURF.create(hessianThreshold, nOctaves, nOctaveLayers, extended, upright);

        MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
        featureDetector.detect(objectImage, objectKeyPoints);
        Mat objectDescriptors = new Mat();
        featureDetector.compute(objectImage, objectKeyPoints, objectDescriptors);


        Mat outputImage = new Mat(objectImage.rows(), objectImage.cols(), Imgcodecs.IMREAD_GRAYSCALE);
        Scalar newKeypointColor = new Scalar(255, 0, 0);
//        log.info(objectKeyPoints.toList().toString());
        Features2d.drawKeypoints(objectImage, objectKeyPoints, outputImage, newKeypointColor, 0);

        MatOfKeyPoint sceneKeyPoints = new MatOfKeyPoint();
        featureDetector.detect(sceneImage, sceneKeyPoints);
        Mat sceneDescriptors = new Mat();
        featureDetector.compute(sceneImage, sceneKeyPoints, sceneDescriptors);

        List<MatOfDMatch> matches = new LinkedList<MatOfDMatch>();
        DescriptorMatcher descriptorMatcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
        descriptorMatcher.knnMatch(objectDescriptors, sceneDescriptors, matches, 2);

        LinkedList<DMatch> goodMatchesList = new LinkedList<DMatch>();

        float nndrRatio = 0.7f;

        for (MatOfDMatch matofDMatch : matches) {
            DMatch[] dmatcharray = matofDMatch.toArray();
            DMatch m1 = dmatcharray[0];
            DMatch m2 = dmatcharray[1];
            if (m1.distance <= m2.distance * nndrRatio) {
                goodMatchesList.addLast(m1);
            }
        }
        if (goodMatchesList.size() >= 7) {
            found = true;
        }
        log.info("subimage found: "+found);

        return found;
    }

    // not ready
    public static boolean imageMatch(File subFile, File actualFile){
        boolean found = false;
        Mat srcImage = Imgcodecs.imread(actualFile.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
        Mat templ = Imgcodecs.imread(subFile.getAbsolutePath(), Imgcodecs.IMREAD_COLOR);
//        Mat srcImage = Imgcodecs.imread(actualFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
//        Mat templ = Imgcodecs.imread(subFile.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
        Mat result = new Mat();

        int result_cols = srcImage.cols() - templ.cols() + 1;
        int result_rows = srcImage.rows() - templ.rows() + 1;
        result.create(result_rows, result_cols, CvType.CV_32FC1);
//        int method=Imgproc.TM_CCOEFF_NORMED;
        int method=Imgproc.TM_SQDIFF_NORMED;
        Imgproc.matchTemplate(srcImage, templ, result, method);

        log.info(String.valueOf(result.elemSize()));

        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        org.opencv.core.Point matchLoc;
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        if (method == Imgproc.TM_SQDIFF || method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
            found = mmr.minVal<0.000001; // not sure
        } else {
            matchLoc = mmr.maxLoc;
            found = mmr.maxVal>0.8;  // not sure
        }
        log.info(String.valueOf(mmr.minVal));
        log.info(String.valueOf(mmr.maxVal));
        log.info(String.valueOf(result.total()));
        log.info(String.valueOf(result.empty()));
        Imgproc.rectangle(srcImage, matchLoc, new org.opencv.core.Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()),
                new Scalar(0, 255, 0), 1, 8, 0);
        Imgproc.rectangle(result, matchLoc, new org.opencv.core.Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()),
                new Scalar(0, 255, 0), 1, 8, 0);
        log.info(String.valueOf(result.isContinuous()));
        Imgcodecs.imwrite("testTemplate.png", srcImage);
        return found;
    }
}
