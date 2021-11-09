package graphics;

import utils.Define;

import java.awt.*;

public abstract class Graphic {
    public Point src;
    public Point dest;
    public Color color;
    public int lineWidth = Define.DEFAULT_LINE_WIDTH;

    public boolean destHasSet = false;
    public abstract void draw(Graphics2D g);
    public abstract boolean canSelect(Point p);
}
