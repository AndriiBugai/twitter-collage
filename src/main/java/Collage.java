import javax.imageio.ImageIO;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by strapper on 13.09.15.
 */
public class Collage {

    Collage() {

    }

    public static BufferedImage createCollage(ArrayList<BufferedImage> list, int wantedSize) throws IOException {
        list = cutList(list, wantedSize);

        int widthOfPicture = list.get(0).getWidth();

        int dimension[] = getDimensions(list.size());
        int width = dimension[0] * widthOfPicture;
        int length = dimension[1] * widthOfPicture;

        BufferedImage result = new BufferedImage (width, length, BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();

        Graphics2D    graphics = result.createGraphics();
        graphics.setPaint(new Color(255, 255, 255));    // choose white color
        graphics.fillRect(0, 0, result.getWidth(), result.getHeight()); // paint results' background

        int x = 0;
        int y = 0;

        for(BufferedImage image : list){
            g.drawImage(image, x, y, null);
            x += widthOfPicture;
            if(x >= result.getWidth()){
                x = 0;
                y += image.getHeight();
            }
        }
        return result;
    }

    private static int[] getDimensions(int numberOfImages) {
        int size = (int) Math.ceil( Math.sqrt(numberOfImages));
        int width = size;
        int length = size;
        int square = length * (width - 1);
        if(square >= numberOfImages) {
            length--;
        }
        int[] dimension = new int[2];
        dimension[0] = width;
        dimension[1] = length;
        return dimension;
    }

    private static ArrayList<BufferedImage> cutList(ArrayList<BufferedImage> list, int wantedSize) {
        int oldSize = list.size();

        if(oldSize <= wantedSize) {
            return list;
        }

        ArrayList<BufferedImage> newList = new ArrayList<BufferedImage>();
        for(int i = 0; i < wantedSize; i++) {
            BufferedImage item = list.get(i);
            newList.add(item);
        }
        return newList;
    }


    public static BufferedImage createCollage(ArrayList<BufferedImage> list) throws IOException {

        int widthOfPicture = list.get(0).getWidth();
        int numberOfBigPictures = getDimensions(list.size())[0] / 2;
        int dimension[] = getDimensions(list.size() + 3 * numberOfBigPictures );

        for(int i = 0; i < numberOfBigPictures; i++) {
            BufferedImage img = list.get(i);
            img = scaleImage(img);
            list.set(i, img);
        }

        int width = dimension[0] * widthOfPicture;
        int length = dimension[1] * widthOfPicture;

        int array[][] = new int[width][length];
        for(int i = 0; i < 2 ; i++) {
            for(int j = 0; j < 2 * numberOfBigPictures ; j++) {
                array[i][j] = 1;
            }
         }

        BufferedImage result = new BufferedImage (width, length, BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();

        Graphics2D    graphics = result.createGraphics();
        graphics.setPaint(new Color(255, 255, 255));    // choose white color
        graphics.fillRect(0, 0, result.getWidth(), result.getHeight()); // paint results' background

        int i = 0; // index of array
        int j = 0;
        int x = 0; // coordinate of current avatar on image
        int y = 0;

        for(BufferedImage image : list){
            if(image.getWidth() > 100) {  // image is big or small
                while (array[i][j] != 1) {
                    x += widthOfPicture;
                    j++;
                    if (x >= result.getWidth()) {
                        x = 0;
                        j = 0;
                        y += image.getHeight();
                        i++;
                    }
                }
                g.drawImage(image, x, y, null);
                x += image.getWidth();
                j+=2;
                if (x >= result.getWidth()) {
                    System.out.println("uuuuuuuuuuuuuuuu");
                    x = 0;
                    j = 0;
                    y += image.getHeight();
                    i+=2;
                }
            } else {
                while (array[i][j] != 0) {
                    x += widthOfPicture;
                    j++;
                    if (x >= result.getWidth()) {
                        x = 0;
                        j = 0;
                        y += image.getHeight();
                        i++;
                    }
                }
                g.drawImage(image, x, y, null);
                x += widthOfPicture;
                j++;
                if (x >= result.getWidth()) {
                    x = 0;
                    j = 0;
                    y += image.getHeight();
                    i++;
                }
            }

        }
        return result;
    }

    // turn small image into big
    public static BufferedImage scaleImage(BufferedImage before) {
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage(2*w, 2*h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(2.0, 2.0);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, after);
        System.out.println("width " +  after.getWidth() );
        return after;
    }

}
