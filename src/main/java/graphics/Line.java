package graphics;

import java.awt.*;

public class Line extends Graphic {
    @Override
    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.setStroke(new BasicStroke(2));
        g.drawLine(src.x, src.y, dest.x, dest.y);
    }
}
