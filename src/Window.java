import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window(){
        setTitle("Image Display");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JPanel display = new JPanel();
        display.setBackground(Color.darkGray);
        display.setLayout(new FlowLayout(FlowLayout.CENTER,2,2));
        display.setPreferredSize(new Dimension(Data.WIDTH, Data.HEIGHT));
        ImageDisplay imageDisplay = new ImageDisplay();
        MenuDisplay menuDisplay = new MenuDisplay();
        display.add(imageDisplay);
        display.add(menuDisplay);
        add(display);
        pack();
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
    }
}
