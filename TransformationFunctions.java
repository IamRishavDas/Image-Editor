import java.awt.Color;
import java.awt.image.BufferedImage;

public class TransformationFunctions {

    public static final int maxRGB = 255;
    public static final int minRGB = 0;

    private static int[] checkBoundary(int r, int g, int b){
        if(r < 0) r = minRGB;
        if(g < 0) g = minRGB;
        if(b < 0) b = minRGB;
        if(r > 255) r = maxRGB;
        if(g > 255) g = maxRGB;
        if(b > 255) b = maxRGB;
        return new int[]{r, g, b};
    }

    public static void clearTheWindow(BufferedImage image){
        if(image == null) return;
        EditorFrame.removePreviousImageFromPanel();
    }

    static int[][] imageArray;
    public static void storeCurrentImageState(BufferedImage image){
        if(image == null) return;
        imageArray = new int[image.getWidth()][image.getHeight()];
        for(int y = 0; y<image.getHeight(); y++){
            for(int x = 0; x<image.getWidth(); x++){
                imageArray[x][y] = image.getRGB(x, y);
            }
        }
        System.out.println("The current state is stored!");
        System.out.println("Image Width: " + image.getWidth() + " Image Height: " + image.getHeight());
        System.out.println("Array Width: " + imageArray.length + " Array Height: " + imageArray[0].length);
    }

    public static void recoverToPreviousCurrentState(BufferedImage image){
        if(imageArray == null){
            return;
        }
        for(int y = 0; y<image.getHeight(); y++){
            for(int x = 0; x<image.getWidth(); x++){
                image.setRGB(x, y, imageArray[x][y]);
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void grayScaleFilter(BufferedImage image){
        if(image == null) return;
        for(int y = 0; y<image.getHeight(); y++){
            for(int x = 0; x<image.getWidth(); x++){
                Color color = new Color(image.getRGB(x, y));
                int avg = (color.getRed() + color.getGreen() + color.getBlue())/3;
                Color gray = new Color(avg, avg, avg);
                image.setRGB(x, y, gray.getRGB());
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void negativeFilter(BufferedImage image){
        if(image == null) return;
        for(int y = 0; y<image.getHeight(); y++){
            for(int x = 0; x<image.getWidth(); x++){
                Color color = new Color(image.getRGB(x, y));
                int nRed = 255-color.getRed();
                int nGreen = 255-color.getGreen();
                int nBlue = 255-color.getBlue();
                int[] vals = checkBoundary(nRed, nGreen, nBlue);
                nRed = vals[0];
                nGreen = vals[1];
                nBlue = vals[2];
                Color negaiveColor = new Color(nRed, nGreen, nBlue);
                image.setRGB(x, y, negaiveColor.getRGB());
            }
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void logTransformation(BufferedImage image, float scalingFactor, float upscalingFactor){
        if(image == null) return;
        for(int y = 0; y<image.getHeight(); y++){
            for(int x = 0; x<image.getWidth(); x++){
                Color color = new Color(image.getRGB(x, y));
                int logR = (int)(scalingFactor * Math.log1p(color.getRed() + 1) + upscalingFactor);
                int logG = (int)(scalingFactor * Math.log1p(color.getGreen() + 1) + upscalingFactor);
                int logB = (int)(scalingFactor * Math.log1p(color.getBlue() + 1) + upscalingFactor);
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

    public static void powerLawTransformation(BufferedImage image, float scalingFactor, float gamma){
        if(image == null) return;
        for(int y = 0; y<image.getHeight(); y++){
            for(int x = 0; x<image.getWidth(); x++){
                Color color = new Color(image.getRGB(x, y));
                int powR = (int)(scalingFactor * Math.pow(color.getRed(), gamma));
                int powG = (int)(scalingFactor * Math.pow(color.getGreen(), gamma));
                int powB = (int)(scalingFactor * Math.pow(color.getBlue(), gamma));
                int[] vals = checkBoundary(powR, powG, powB);
                powR = vals[0];
                powG = vals[1];
                powB = vals[2];
                if(powR > 255 || powG > 255 || powB > 255) System.out.println(powR + ": " + powG + ": " + powB);
                image.setRGB(x, y, new Color(powR, powG, powB).getRGB());
            }   
        }
        EditorFrame.removePreviousImageFromPanel();
        EditorFrame.displayImage(image);
    }

    public static void exponentialTransformation(BufferedImage image, float scalingFactor){
        if(image == null) return;
        for(int y = 0; y<image.getHeight(); y++){
            for(int x = 0; x<image.getWidth(); x++){
                Color color = new Color(image.getRGB(x, y));
                int expR = (int)(scalingFactor * Math.exp(color.getRed()));
                int expG = (int)(scalingFactor * Math.exp(color.getGreen()));
                int expB = (int)(scalingFactor * Math.exp(color.getBlue()));
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

    public static void rotateTheImage(BufferedImage image){
        System.out.println("TODO: not implemented yet!");
        return;
    }

    
}
