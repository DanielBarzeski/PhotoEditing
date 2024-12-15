import javax.swing.*;
import java.awt.*;

public class ImageDisplay extends JPanel implements Runnable {
    private static MouseClickListener[] MOUSE;
    private final int x = Data.IMAGE_START_X, y = Data.IMAGE_START_Y, w = Data.IMAGE_WIDTH, h = Data.IMAGE_HEIGHT;

    public ImageDisplay() {
        setPreferredSize(new Dimension(Data.WIDTH - 2, Data.HEIGHT / 3 * 2 - 2));
        setBackground(Color.CYAN);
        MOUSE = new MouseClickListener[]{new MouseClickListener(x-1, y-1),
                new MouseClickListener(w + 1, y-1),
                new MouseClickListener(w + 1, h + 1),
                new MouseClickListener(x-1, h + 1)
        };
        for (MouseClickListener mouseClickListener : MOUSE) {
            addMouseListener(mouseClickListener);
            addMouseMotionListener(mouseClickListener);
        }
        new Thread(this).start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!Data.IMAGES.isEmpty() && Data.IMAGES.getLast() != null)
            g.drawImage(Data.IMAGES.getLast(), x, y, w, h, null);
        drawMouseListener(g);
    }

    private void drawMouseListener(Graphics graphics) {
        if (MOUSE == null) return;
        int a = MouseClickListener.dotSize;
        if (MOUSE[0].getY() != MOUSE[1].getY() )
            MOUSE[0].setY(MOUSE[1].getY());
        if (MOUSE[1].getX() != MOUSE[2].getX() )
            MOUSE[1].setX(MOUSE[2].getX());
        if (MOUSE[2].getY() != MOUSE[3].getY() )
            MOUSE[2].setY(MOUSE[3].getY());
        if (MOUSE[3].getX() != MOUSE[0].getX() )
            MOUSE[3].setX(MOUSE[0].getX());
        for (int i = 0; i < MOUSE.length; i++) {
            if (i%2 == 0)
                graphics.setColor(Color.blue);
            else
                graphics.setColor(Color.RED);
            if (MOUSE[i].getX() < x-1)
                MOUSE[i].setX(x-1);
            if (MOUSE[i].getX() > w + 1)
                MOUSE[i].setX(w + 1);
            if (MOUSE[i].getY() < y-1)
                MOUSE[i].setY(y-1);
            if (MOUSE[i].getY() > h + 1)
                MOUSE[i].setY(h + 1);
            graphics.fillOval(MOUSE[i].getX() - a / 2 + 1, MOUSE[i].getY() - a / 2 + 1, a, a);
        }
        graphics.setColor(Color.BLACK);
        graphics.drawLine(MOUSE[0].getX(), MOUSE[0].getY(), MOUSE[1].getX(), MOUSE[1].getY());
        graphics.drawLine(MOUSE[1].getX(), MOUSE[1].getY(), MOUSE[2].getX(), MOUSE[2].getY());
        graphics.drawLine(MOUSE[2].getX(), MOUSE[2].getY(), MOUSE[3].getX(), MOUSE[3].getY());
        graphics.drawLine(MOUSE[3].getX(), MOUSE[3].getY(), MOUSE[0].getX(), MOUSE[0].getY());

    }
    public static void setMouseListeners(){
        if (MOUSE != null) {
            for (MouseClickListener mouseClickListener : MOUSE) {
                mouseClickListener.setAll();
            }
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (MOUSE != null) {

                Data.setPOLYGON(new Polygon(
                        new int[]{MOUSE[0].getX() - 2, MOUSE[1].getX() - 1, MOUSE[2].getX() - 1, MOUSE[3].getX() - 2},
                        new int[]{MOUSE[0].getY() - 2, MOUSE[1].getY() - 2, MOUSE[2].getY() - 1, MOUSE[3].getY() - 1},
                        4)
                );
            }
            revalidate();
            repaint();
        }
    }
}
