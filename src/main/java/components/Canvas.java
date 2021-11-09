package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;

import graphics.Graphic;
import graphics.Line;
import graphics.None;
import static components.ToolBar.ToolInfo.Types.*;

public class Canvas extends JPanel {
    private Graphic currentGraphic;
    private final LinkedList<Graphic> graphicArr = new LinkedList<>();

    public void resetCursorByToolType() {
        if (ToolBar.currentType == ToolBar.ToolInfo.Types.NONE) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    public Canvas() {
        Canvas that = this;
        this.setBackground(Color.white);
        this.resetCursorByToolType();
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (ToolBar.currentType == LINE || ToolBar.currentType == RECTANGLE) {
                    that.currentGraphic.dest = e.getPoint();
                    that.repaint();
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                switch (ToolBar.currentType) {
                    case LINE:
                        that.currentGraphic = new Line();
                        break;
                    default:
                        that.currentGraphic = new None();
                        break;
                }
                that.currentGraphic.src = e.getPoint();
                that.currentGraphic.dest = e.getPoint();
                that.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                that.currentGraphic.dest = e.getPoint();
                if (ToolBar.currentType != null) {
                    that.graphicArr.add(currentGraphic);
                }
                that.repaint();
            }
        });
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
