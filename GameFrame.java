import Packages.Functions.*;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private JPanel content;

    public GameFrame() {
        content = new JPanel();
        setTitle("Gyruss");
        setIconImage(new ImageIcon("./Data/icon.png").getImage());
        setBounds(600, 10, 700, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(content);
        setLayout(null);
        content.setOpaque(false);
        new GameMenu(content, this);
        setVisible(true);
    }

    public void Remove(Component Obj) {
        content.remove(Obj);
        content.repaint();
    }
}