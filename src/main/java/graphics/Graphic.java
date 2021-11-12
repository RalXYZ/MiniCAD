package graphics;

import components.ColorBar;
import utils.Define;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public abstract class Graphic implements Serializable {
    public Point src;
    public Point dest;
    public Color color = ColorBar.currentColor;
    public float lineWidth = Define.DEFAULT_LINE_WIDTH;

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.lineWidth));
    }
    public abstract boolean canSelect(Point p);

    public void scaleUp() {
        final int diffX = this.src.x - this.dest.x;
        final int diffY = this.src.y - this.dest.y;
        this.src.x += diffX / Define.SCALE_RATIO;
        this.src.y += diffY / Define.SCALE_RATIO;
        this.dest.x -= diffX / Define.SCALE_RATIO;
        this.dest.y -= diffY / Define.SCALE_RATIO;
    }

    public void scaleDown() {
        final int diffX = this.src.x - this.dest.x;
        final int diffY = this.src.y - this.dest.y;
        this.src.x -= diffX / Define.SCALE_RATIO;
        this.src.y -= diffY / Define.SCALE_RATIO;
        this.dest.x += diffX / Define.SCALE_RATIO;
        this.dest.y += diffY / Define.SCALE_RATIO;
    }

    public void lineWidthUp() {
        this.lineWidth += Define.LINE_WIDTH_STEP;
    }

    public void lineWidthDown() {
        if (lineWidth == Define.LINE_WIDTH_STEP) {
            JOptionPane.showMessageDialog(null, "Line width cannot be decreased any more");
        } else {
            this.lineWidth -= Define.LINE_WIDTH_STEP;
        }
    }

    public void move(int x, int y) {
        this.src.x += x;
        this.src.y += y;
        this.dest.x += x;
        this.dest.y += y;
    }

}
