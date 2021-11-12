package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

import graphics.Graphic;
import graphics.Line;
import graphics.None;
import graphics.TextField;

import static components.ToolBar.ToolInfo.Types.*;

public class Canvas extends JPanel {
    private Graphic currentGraphic;
    private Graphic selectedGraphic;

    private final LinkedList<Graphic> graphicArr = new LinkedList<>();

    public void load(ObjectInputStream is) throws IOException {
        this.graphicArr.clear();
        try {
            while (true) {
                this.graphicArr.add((Graphic) is.readObject());
            }
        } catch (EOFException ignored) {
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void save(ObjectOutputStream os) throws IOException {
        for (Graphic g : this.graphicArr) {
            os.writeObject(g);
        }
    }

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
        this.repaint();
    }

    public Canvas() {
        this.setBackground(Color.white);
        this.resetCursorByToolType();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());

        final Point[] state = new Point[2];
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (ToolBar.currentType == NONE && Canvas.this.selectedGraphic != null) {
                    state[1] = e.getPoint();
                    if (state[0] != null) {
                        final int xOffset = state[1].x - state[0].x;
                        final int yOffset = state[1].y - state[0].y;
                        selectedGraphic.move(xOffset, yOffset);
                    }
                    state[0] = state[1];
                    Canvas.this.repaint();
                    return;
                }
                if (ToolBar.currentType != NONE) {
                    Canvas.this.currentGraphic.dest = e.getPoint();
                    Canvas.this.repaint();
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ToolBar.currentType == NONE) {
                    state[0] = null;
                    state[1] = null;
                    ArrayList<Graphic> target = (ArrayList<Graphic>) graphicArr
                            .stream()
                            .filter(x -> x.canSelect(e.getPoint()))
                            .limit(1)
                            .collect(Collectors.toList());
                    if (target.size() == 0) {
                        Canvas.this.selectedGraphic = null;
                    } else if (target.size() == 1) {
                        Canvas.this.selectedGraphic = target.get(0);
                    }
                    return;
                }
                switch (ToolBar.currentType) {
                    case LINE:
                        Canvas.this.currentGraphic = new Line();
                        break;
                    case RECTANGLE:
                        Canvas.this.currentGraphic = new graphics.Rectangle();
                        break;
                    case CIRCLE:
                        Canvas.this.currentGraphic = new graphics.Circle();
                        break;
                    case TEXT_FIELD:
                        Canvas.this.currentGraphic = new TextField();
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
                if (ToolBar.currentType == NONE) {
                    return;
                }
                Canvas.this.currentGraphic.dest = e.getPoint();
                if (ToolBar.currentType != null) {
                    graphicArr.add(currentGraphic);
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
                    break;
                case 'r': case 'R':
                    graphicArr.remove(Canvas.this.selectedGraphic);
                    Canvas.this.selectedGraphic = null;
                    break;
            }
            Canvas.this.repaint();
            return true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphicArr.forEach(x -> x.draw((Graphics2D) g));
        if (this.currentGraphic != null) {
            this.currentGraphic.draw((Graphics2D) g);
        }
    }
}
