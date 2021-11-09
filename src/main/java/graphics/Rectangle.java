package graphics;

import java.awt.*;
import java.awt.geom.Line2D;

public class Rectangle extends Graphic {
    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.lineWidth));
        g.drawLine(src.x, src.y, dest.x, src.y);
        g.drawLine(dest.x, src.y, dest.x, dest.y);
        g.drawLine(dest.x, dest.y, src.x, dest.y);
        g.drawLine(src.x, dest.y, src.x, src.y);
        // g.drawRect(src.x, src.y, dest.x - src.x, dest.y - src.y);
    }

    public boolean canSelect(Point p) {
        return new Line2D.Double(this.src, this.dest).getBounds().contains(p);
    }
}
