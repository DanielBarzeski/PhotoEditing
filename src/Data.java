import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Data {
    public final static int WIDTH = 600, HEIGHT = 600;
    public final static int IMAGE_START_Y = 2, IMAGE_START_X = 2, IMAGE_WIDTH = Data.WIDTH-6, IMAGE_HEIGHT = Data.HEIGHT/3*2-6;


    public static final ArrayList<BufferedImage> IMAGES = new ArrayList<>();


    private static Polygon POLYGON;

    public static Polygon getPOLYGON() {
        return POLYGON;
    }

    public static void setPOLYGON(Polygon POLYGON) {
        Data.POLYGON = POLYGON;
    }

}
