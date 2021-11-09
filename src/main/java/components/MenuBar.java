package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.LinkedList;

import static utils.Define.*;

public class MenuBar extends JMenuBar {
    private final Canvas canvas;

    private LinkedList<JMenu> initMenus() {
        LinkedList<JMenu> menus = new LinkedList<>();
        {
            JMenu menuFile = new JMenu("File");
            menuFile.setMnemonic(KeyEvent.VK_F);
            {
                JMenuItem fileOpen = new JMenuItem("Open", KeyEvent.VK_O);
                fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
                fileOpen.addActionListener(e -> {
                    FileDialog fileDialog = new FileDialog(
                            (Frame)this.getTopLevelAncestor(),
                            "Open",
                            FileDialog.LOAD
                    );
                    fileDialog.setVisible(true);
                    String directory = fileDialog.getDirectory();
                    String fileName = fileDialog.getFile();
                    if(directory == null || fileName == null){
                        return;
                    }
                    try (ObjectInputStream is =
                                 new ObjectInputStream(
                                         new FileInputStream(directory + File.separator + fileName))
                    ) {
                        canvas.load(is);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                this.getTopLevelAncestor(),
                                "Load error",
                                "IO exception",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    canvas.repaint();
                });
                menuFile.add(fileOpen);
            }
            {
                JMenuItem fileSave = new JMenuItem("Save", KeyEvent.VK_S);
                fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
                fileSave.addActionListener(e -> {
                    FileDialog fileDialog = new FileDialog(
                            (Frame)this.getTopLevelAncestor(),
                            "Save",
                            FileDialog.SAVE
                    );
                    fileDialog.setVisible(true);
                    String directory = fileDialog.getDirectory();
                    String fileName = fileDialog.getFile();
                    if (directory == null || fileName == null){
                        return;
                    }
                    try (ObjectOutputStream os =
                                 new ObjectOutputStream(
                                         new FileOutputStream(directory + File.separator + fileName))
                    ) {
                        canvas.save(os);
                        os.flush();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                this.getTopLevelAncestor(),
                                "Save error",
                                "IO exception",
                                JOptionPane.ERROR_MESSAGE
                        );
                        ex.printStackTrace();
                    }
                });
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

    public MenuBar(Canvas c) {
        this.canvas = c;
        Font f = new Font(this.getFont().getFontName(), this.getFont().getStyle(), MENU_FONT_SIZE);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        initMenus().forEach(this::add);
    }
}
