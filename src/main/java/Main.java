import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.BorderLayout.*;
import static utils.Define.*;

import components.Canvas;
import components.ColorBar;
import components.MenuBar;
import components.ToolBar;

public class Main extends JFrame {
    Main() {
        this.setTitle("MiniCAD");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        Canvas c = new Canvas();
        this.add(c, CENTER);
        this.add(new MenuBar(), NORTH);
        this.add(new ToolBar(c), WEST);
        this.add(new ColorBar(c), SOUTH);

        this.setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}
