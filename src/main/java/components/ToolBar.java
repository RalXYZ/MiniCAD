package components;

import graphics.TextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Stream;

import static utils.Define.*;

public class ToolBar extends JPanel {
    static ToolInfo.Types currentType = ToolInfo.Types.NONE;
    private final Canvas canvas;

    static class ToolInfo {
        enum Types { NONE, LINE, RECTANGLE, CIRCLE, TEXT_FIELD }
        Types type;
        ImageIcon icon;

        public ToolInfo(Types type, String iconPath) {
            this.type = type;
            try {
                BufferedImage image = ImageIO.read(new File(iconPath));
                this.icon = new ImageIcon(image);
                this.icon.setImage(
                        this.icon.getImage().getScaledInstance(TOOL_ICON_SIZE, TOOL_ICON_SIZE, Image.SCALE_DEFAULT)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ToolBar(Canvas c) {
        this.canvas = c;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Stream.of(
                new ToolInfo(ToolInfo.Types.NONE, ICON_BASE_PATH + "/pointer.png"),
                new ToolInfo(ToolInfo.Types.LINE, ICON_BASE_PATH + "/line.png"),
                new ToolInfo(ToolInfo.Types.RECTANGLE, ICON_BASE_PATH + "/rectangle.png"),
                new ToolInfo(ToolInfo.Types.CIRCLE, ICON_BASE_PATH + "/circle.png"),
                new ToolInfo(ToolInfo.Types.TEXT_FIELD, ICON_BASE_PATH + "/text-field.png")
        ).forEach(x -> {
            JButton button = new JButton(x.icon);
            button.setSize(TOOL_BUTTON_SIZE, TOOL_BUTTON_SIZE);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            this.add(button);
            button.addActionListener(e -> {
                if (x.type == ToolInfo.Types.TEXT_FIELD) {
                    final String result = JOptionPane.showInputDialog("Input some text");
                    if (result == null) {
                        return;
                    } else {
                        TextField.text = result;
                    }
                }
                currentType = x.type;
                canvas.resetCursorByToolType();
            });
        });

    }
}
