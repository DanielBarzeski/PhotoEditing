import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuDisplay extends JPanel {
    private BufferedImage image;
    public MenuDisplay() {
        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        setPreferredSize(new Dimension(Data.WIDTH-2, Data.HEIGHT/3-4));
        setBackground(Color.yellow);
        JButton open = new JButton("Open Image");
        open.addActionListener(e -> {
            try {
                image = ImageIO.read(FileManager.getImageFromDesktop());
            } catch (Exception ignored) {}
            if (image != null) {
                Data.IMAGES.clear();
                Data.IMAGES.add(ImageManipulation.fix(image,Data.IMAGE_WIDTH,Data.IMAGE_HEIGHT));
                image = null;
            }
        });
        add(open);
        JButton save = new JButton("Save To Directory");
        save.addActionListener(e -> {
            if (!Data.IMAGES.isEmpty())
                FileManager.saveImagesToDirectory(Data.IMAGES,"saving");
        });
        add(save);
        JButton back = new JButton("Back");
        back.addActionListener(e -> {
            if (Data.IMAGES.size() > 1)
                Data.IMAGES.removeLast();
        });
        add(back);
        JButton resetImage = new JButton("Reset Image");
        resetImage.addActionListener(e -> {
            if (Data.IMAGES.size() > 1)
                Data.IMAGES.subList(1, Data.IMAGES.size()).clear();
        });
        add(resetImage);
        JButton resetPoints = new JButton("Reset Points");
        resetPoints.addActionListener(e -> {
            ImageDisplay.setMouseListeners();
        });
        add(resetPoints);
        ImageManipulation.EModify[] m = ImageManipulation.EModify.values();
        JButton[] buttons = new JButton[m.length];
        for (int i = 0; i < buttons.length; i++) {
            int finalI = i;
            buttons[finalI] = new JButton(m[i].name());
            buttons[finalI].addActionListener(e -> {
                if (!Data.IMAGES.isEmpty() && Data.IMAGES.getLast() != null)
                    Data.IMAGES.add(ImageManipulation.applyEffect(m[finalI], Data.IMAGES.getLast()));
            });
            add(buttons[finalI]);
        }
    }
}
