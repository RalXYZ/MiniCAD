package graphics;

import components.ColorBar;
import utils.Define;

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
        this.lineWidth += 0.25;
    }

    public void lineWidthDown() {
        this.lineWidth -= 0.25;
        // FIXME: if width <= 0, pop up a message box
    }

    public void move(int x, int y) {
        this.src.x += x;
        this.src.y += y;
        this.dest.x += x;
        this.dest.y += y;
    }

}
