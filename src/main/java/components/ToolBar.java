package components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import static utils.Define.*;

public class ToolBar extends JPanel {
    static class ToolInfo {
        enum Types {LINE, RECTANGLE}
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

    public ToolBar() {
        this.setSize(TOOL_BUTTON_SIZE, WINDOW_HEIGHT);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final LinkedList<ToolInfo> toolInfoList = new LinkedList<>();
        toolInfoList.add(new ToolInfo(ToolInfo.Types.LINE, ICON_BASE_PATH + "/line.png"));
        toolInfoList.add(new ToolInfo(ToolInfo.Types.RECTANGLE, ICON_BASE_PATH + "/rectangle.png"));

        toolInfoList.forEach(x -> {
            JButton button = new JButton(x.icon);
            button.setSize(TOOL_BUTTON_SIZE, TOOL_BUTTON_SIZE);
            button.setOpaque(true);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            this.add(button);
        });

    }
}
