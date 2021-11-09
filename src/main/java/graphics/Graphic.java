package graphics;

import java.awt.*;

public abstract class Graphic {
    public Point src;
    public Point dest;
    public Color color;
    public boolean destHasSet = false;
    public abstract void draw(Graphics2D g);
}
