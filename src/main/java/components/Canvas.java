package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

import graphics.Graphic;
import graphics.Line;
import graphics.None;

import static components.ToolBar.ToolInfo.Types.*;

public class Canvas extends JPanel {
    private Graphic currentGraphic;
    private Graphic selectedGraphic;

    private final LinkedList<Graphic> graphicArr = new LinkedList<>();

    public void resetCursorByToolType() {
        if (ToolBar.currentType == ToolBar.ToolInfo.Types.NONE) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    public void colorChangeCallback() {
        if (selectedGraphic == null) {
            return;
        }
        selectedGraphic.color = ColorBar.currentColor;
        repaint();
    }

    public Canvas() {
        this.setBackground(Color.white);
        this.resetCursorByToolType();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (ToolBar.currentType == LINE || ToolBar.currentType == RECTANGLE) {
                    Canvas.this.currentGraphic.dest = e.getPoint();
                    Canvas.this.repaint();
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ToolBar.currentType != NONE) {
                    return;
                }
                ArrayList<Graphic> target = (ArrayList<Graphic>) Canvas.this.graphicArr
                        .stream()
                        .filter(x -> x.canSelect(e.getPoint()))
                        .limit(1)
                        .collect(Collectors.toList());
                if (target.size() == 0) {
                    Canvas.this.selectedGraphic = null;
                } else if (target.size() == 1) {
                    Canvas.this.selectedGraphic = target.get(0);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
                switch (ToolBar.currentType) {
                    case LINE:
                        Canvas.this.currentGraphic = new Line();
                        break;
                    default:
                        Canvas.this.currentGraphic = new None();
                        break;
                }
                Canvas.this.currentGraphic.src = e.getPoint();
                Canvas.this.currentGraphic.dest = e.getPoint();
                Canvas.this.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                Canvas.this.currentGraphic.dest = e.getPoint();
                if (ToolBar.currentType != null) {
                    Canvas.this.graphicArr.add(currentGraphic);
                }
                Canvas.this.repaint();
            }
        });
    }

    private class MyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (Canvas.this.selectedGraphic == null) {
                return false;
            }
            if (e.getID() != KeyEvent.KEY_RELEASED) {
                return false;
            }
            switch (e.getKeyChar()) {
                case '+': case '=':
                    Canvas.this.selectedGraphic.scaleUp();
                    break;
                case '-': case '_':
                    Canvas.this.selectedGraphic.scaleDown();
                    break;
                case '<': case ',':
                    Canvas.this.selectedGraphic.lineWidthDown();
                    break;
                case '>': case '.':
                    Canvas.this.selectedGraphic.lineWidthUp();
            }
            Canvas.this.repaint();
            return true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphicArr.forEach(x -> x.draw((Graphics2D) g));
        if (this.currentGraphic != null) {
            this.currentGraphic.draw((Graphics2D) g);
        }
    }
}
