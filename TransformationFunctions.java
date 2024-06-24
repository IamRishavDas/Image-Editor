import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class TransformationFunctions {

    public static final int maxRGB = 255;
    public static final int minRGB = 0;

    private static int[] checkBoundary(int r, int g, int b) {
        if (r < 0) r = minRGB; if (r > 255) r = maxRGB;
        if (g < 0) g = minRGB; if (g > 255) g = maxRGB;
        if (b < 0) b = minRGB; if (b > 255) b = maxRGB;
        return new int[] { r, g, b };
    }

    public static void clearTheWindow(BufferedImage image) {
        if (image == null)
            return;
        EditorFrame.removePreviousImageFromPanel();
    }

    static int[][] imageArray;

    public static void storeCurrentImageState(BufferedImage image) {
        if (image == null)
            return;
        imageArray = new int[image.getWidth()][image.getHeight()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                imageArray[x][y] = image.getRGB(x, y);
            }
        }
        System.out.println("The current state is stored!");
        System.out.println("Image Width: " + image.getWidth() + " Image Height: " + image.getHeight());
        System.out.println("Array Width: " + imageArray.length + " Array Height: " + imageArray[0].length);
    }

    public static void recoverToPreviousCurrentState(BufferedImage image) {
        if (imageArray == null) {
            return;
        }
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                image.setRGB(x, y, imageArray[x][y]);
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void grayScaleFilter(BufferedImage image) {
        if (image == null)
            return;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color gray = new Color(avg, avg, avg);
                image.setRGB(x, y, gray.getRGB());
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void negativeFilter(BufferedImage image) {
        if (image == null)
            return;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int nR = 255 - color.getRed();
                int nG = 255 - color.getGreen();
                int nB = 255 - color.getBlue();
                int[] vals = checkBoundary(nR, nG, nB);
                nR = vals[0];
                nG = vals[1];
                nB = vals[2];
                Color negaiveColor = new Color(nR, nG, nB);
                image.setRGB(x, y, negaiveColor.getRGB());
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void logTransformation(BufferedImage image, float scalingFactor, float upscalingFactor) {
        if (image == null)
            return;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int logR = (int) (scalingFactor * Math.log1p(color.getRed() + 1) + upscalingFactor);
                int logG = (int) (scalingFactor * Math.log1p(color.getGreen() + 1) + upscalingFactor);
                int logB = (int) (scalingFactor * Math.log1p(color.getBlue() + 1) + upscalingFactor);
                int[] vals = checkBoundary(logR, logG, logB);
                logR = vals[0];
                logG = vals[1];
                logB = vals[2];
                image.setRGB(x, y, new Color(logR, logG, logB).getRGB());
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void powerLawTransformation(BufferedImage image, float scalingFactor, float gamma) {
        if (image == null)
            return;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int powR = (int) (scalingFactor * Math.pow(color.getRed(), gamma));
                int powG = (int) (scalingFactor * Math.pow(color.getGreen(), gamma));
                int powB = (int) (scalingFactor * Math.pow(color.getBlue(), gamma));
                int[] vals = checkBoundary(powR, powG, powB);
                powR = vals[0];
                powG = vals[1];
                powB = vals[2];
                if (powR > 255 || powG > 255 || powB > 255)
                    System.out.println(powR + ": " + powG + ": " + powB);
                image.setRGB(x, y, new Color(powR, powG, powB).getRGB());
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void exponentialTransformation(BufferedImage image, float scalingFactor) {
        if (image == null)
            return;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int expR = (int) (scalingFactor * Math.exp(color.getRed()));
                int expG = (int) (scalingFactor * Math.exp(color.getGreen()));
                int expB = (int) (scalingFactor * Math.exp(color.getBlue()));
                int[] vals = checkBoundary(expR, expG, expB);
                expR = vals[0];
                expG = vals[1];
                expB = vals[2];
                image.setRGB(x, y, new Color(expR, expG, expB).getRGB());
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void rotateTheImage(BufferedImage image, double angleInDegree) {
        if (image == null) {
            JOptionPane.showMessageDialog(null, "The canvas is empty!");
            return;
        }

        image = createRotatedImage(image, angleInDegree);
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    private static BufferedImage createRotatedImage(BufferedImage originalImage, double angleInDegree) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, originalImage.getType());

        Graphics2D g = rotatedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.rotate(Math.toRadians(angleInDegree), width / 2, height / 2);
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        return rotatedImage;
    }

    private static boolean isCloseColor(Color color, Color colorToRemove) {
        int[] removeRGB = new int[3];
        removeRGB[0] = colorToRemove.getRed();
        removeRGB[1] = colorToRemove.getGreen();
        removeRGB[2] = colorToRemove.getBlue();

        int max = 0, maxIndex = 0;
        for(int i = 0; i<removeRGB.length; i++){
            if(max < removeRGB[i]){
                max = removeRGB[i];
                maxIndex = i;
            }
        }

        // spatial domain for closer range for finding closer tone alpha 
        var closerToneRed = new int[]{10, 20, 30};
        var closerToneGreen = new int[]{30, 10, 30};
        var closerToneBlue = new int[]{30, 40, 15};

        // var closerToneRed = new int[]{50, 50, 50};
        // var closerToneGreen = new int[]{50, 50, 50};
        // var closerToneBlue = new int[]{50, 50, 50};

        int[] colorRGB = new int[3];
        colorRGB[0] = color.getRed();
        colorRGB[1] = color.getGreen();
        colorRGB[2] = color.getBlue();

        if(maxIndex == 0){
            if((colorRGB[0] >= removeRGB[0] - closerToneRed[0]) && (colorRGB[0] <= removeRGB[0] + closerToneRed[0])) return true;
            if((colorRGB[1] >= removeRGB[1] - closerToneRed[1]) && (colorRGB[1] <= removeRGB[1] + closerToneRed[1])) return true;
            if((colorRGB[2] >= removeRGB[2] - closerToneRed[2]) && (colorRGB[2] <= removeRGB[2] + closerToneRed[2])) return true;
        } else if(maxIndex == 1){
            if((colorRGB[0] >= removeRGB[0] - closerToneGreen[0]) && (colorRGB[0] <= removeRGB[0] + closerToneGreen[0])) return true;
            if((colorRGB[1] >= removeRGB[1] - closerToneGreen[1]) && (colorRGB[1] <= removeRGB[1] + closerToneGreen[1])) return true;
            if((colorRGB[2] >= removeRGB[2] - closerToneGreen[2]) && (colorRGB[2] <= removeRGB[2] + closerToneGreen[2])) return true;
        } else if(maxIndex == 2){
            if((colorRGB[0] >= removeRGB[0] - closerToneBlue[0]) && (colorRGB[0] <= removeRGB[0] + closerToneBlue[0])) return true;
            if((colorRGB[1] >= removeRGB[1] - closerToneBlue[1]) && (colorRGB[1] <= removeRGB[1] + closerToneBlue[1])) return true;
            if((colorRGB[2] >= removeRGB[2] - closerToneBlue[2]) && (colorRGB[2] <= removeRGB[2] + closerToneBlue[2])) return true;
        } else {
            return false;
        }      
        return false;
    }

    public static void replaceTheColor(BufferedImage image, Color colorToRemove, Color colorToPut) {
        if (image == null || colorToPut == null || colorToRemove == null)
            return;
        System.out.println("Color to remove: " + colorToRemove + " Color to put: " + colorToPut);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                if (color.equals(colorToRemove) || isCloseColor(color, colorToRemove)) {
                    image.setRGB(x, y, colorToPut.getRGB());
                }
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

}
