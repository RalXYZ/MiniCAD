package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class MenuBar extends JMenuBar {
    private LinkedList<JMenu> initMenus() {
        LinkedList<JMenu> menus = new LinkedList<>();
        {
            JMenu menuFile = new JMenu("File");
            menuFile.setMnemonic(KeyEvent.VK_F);
            {
                JMenuItem fileOpen = new JMenuItem("Open", KeyEvent.VK_O);
                fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
                fileOpen.addActionListener(e -> {
                    FileDialog fileDialog = new FileDialog((Frame)this.getTopLevelAncestor(), "Open", FileDialog.LOAD);
                    fileDialog.setVisible(true);
                    // TODO: open file
                });
                menuFile.add(fileOpen);
            }
            {
                JMenuItem fileSave = new JMenuItem("Save", KeyEvent.VK_S);
                fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
                menuFile.add(fileSave);
            }
            menus.add(menuFile);
        }
        {
            JMenu menuUtils = new JMenu("Utils");
            menuUtils.setMnemonic(KeyEvent.VK_U);
            {
                JMenuItem utilsExit = new JMenuItem("Exit", KeyEvent.VK_E);
                utilsExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
                utilsExit.addActionListener(e -> System.exit(0));
                menuUtils.add(utilsExit);
            }
            menus.add(menuUtils);
        }
        return menus;
    }

    public MenuBar() {
        initMenus().forEach(this::add);
    }
}
