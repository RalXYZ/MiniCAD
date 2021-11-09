package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;

import graphics.Graphic;
import graphics.Line;

public class Canvas extends JPanel {
    static Graphic currentGraphic;
    private final LinkedList<Graphic> graphicArr = new LinkedList<>();

    private void updateCurrentGraphic() {
        switch (ToolBar.currentType) {
            case LINE:
                currentGraphic = new Line();
                break;
            case RECTANGLE:
                // TODO
        }
    }

    public Canvas() {
        Canvas that = this;
        this.updateCurrentGraphic();
        this.setBackground(Color.white);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentGraphic.src != null && !currentGraphic.destHasSet) {
                    currentGraphic.dest = new Point(e.getX(), e.getY());
                    that.repaint();
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentGraphic.src == null) {
                    currentGraphic.src = new Point(e.getX(), e.getY());
                    currentGraphic.dest = new Point(e.getX(), e.getY());
                } else if (!currentGraphic.destHasSet) {
                    currentGraphic.dest = new Point(e.getX(), e.getY());
                    currentGraphic.destHasSet = true;
                    that.graphicArr.add(currentGraphic);
                    that.updateCurrentGraphic();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphicArr.forEach(x -> x.draw((Graphics2D) g));
        if (currentGraphic.dest != null) {
            currentGraphic.draw((Graphics2D) g);
        }
    }
}
