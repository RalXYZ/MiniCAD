import javax.swing.*;
import java.awt.*;

import utils.Define;
import components.MenuBar;

public class Main extends JFrame {
    Main() {
        this.setTitle("MiniCAD");
        this.setSize(Define.WINDOW_WIDTH, Define.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        this.add(new MenuBar(), BorderLayout.NORTH);

        this.setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}
