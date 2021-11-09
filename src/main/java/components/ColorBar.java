package components;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static utils.Define.*;

public class ColorBar extends JPanel {
    private final Canvas c;

    public static Color currentColor;
    Color[] colors = {
            Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
            Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
            Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
    };

    public ColorBar(Canvas c) {
        this.c = c;
        this.setLayout(new GridLayout(1, this.colors.length));
        Arrays.stream(colors).forEach(x -> {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(COLOR_BUTTON_SIZE, COLOR_BUTTON_SIZE));
            button.setBackground(x);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createLoweredBevelBorder());
            button.addActionListener(e -> {
                currentColor = x;
                c.colorChangeCallback();
            });
            this.add(button);
        });
    }
}
