package graphics;

import components.ColorBar;
import utils.Define;

import java.awt.*;

public abstract class Graphic {
    public Point src;
    public Point dest;
    public Color color = ColorBar.currentColor;
    public int lineWidth = Define.DEFAULT_LINE_WIDTH;

    public abstract void draw(Graphics2D g);
    public abstract boolean canSelect(Point p);
}
