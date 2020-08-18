
package windows;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jesús
 */
public  class ImageTools {
    
    public static int[][] imageToMatrix(BufferedImage image){
        int[][] colorM=new int[image.getWidth()][image.getHeight()];
        for (int i = 0; i < colorM.length; i++) {
            for (int j = 0; j < colorM[0].length; j++) {
                colorM[i][j]=image.getRGB(i,j);
            }
        }
        return colorM;
    }
    
    public static BufferedImage matrixToImage(int[][] rgbM){
        BufferedImage image = new BufferedImage(rgbM.length, rgbM[0].length, BufferedImage.TYPE_INT_RGB);
        int rgb;
        for (int i = 0; i < rgbM.length; i++) {
            for (int j = 0; j < rgbM[0].length; j++) {
                //Cambiamos a formato RGB
                rgb=(rgbM[i][j] << 16) | (rgbM[i][j] << 8) | rgbM[i][j];
                image.setRGB(i, j, rgb);
            }
        }
        return image;
    }
    
    public static BufferedImage resize(BufferedImage image){
        return null;
    }
    
    public static int[][] grayScale(BufferedImage image){
        int[][] rgbM = imageToMatrix(image), grayM = new int[image.getWidth()][image.getHeight()];
        int gray;
        Color color;
        for( int i = 0; i < image.getWidth(); i++ ){
            for( int j = 0; j < image.getHeight(); j++ ){
                color=new Color(rgbM[i][j]);
                gray=(int)((color.getRed()+color.getGreen()+color.getBlue())/3);
                grayM[i][j] = gray;
            }
        }
        return grayM;
    }
    
    public static int[][] threshold(BufferedImage image, double threshold){
        int[][] grayM = grayScale(image), thresholdM = new int[image.getWidth()][image.getHeight()];
        for( int i = 0; i < image.getWidth(); i++ ){
            for( int j = 0; j < image.getHeight(); j++ ){
                if (grayM[i][j]<threshold) 
                    thresholdM[i][j] = 0;
                else
                    thresholdM[i][j] = 255;
            }
        }
        return thresholdM;
    }
    
}
