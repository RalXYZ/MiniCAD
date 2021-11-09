package graphics;

import java.awt.*;

public class Circle extends Graphic {
    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.lineWidth));
        g.drawOval(src.x, src.y, dest.x - src.x, dest.y - src.y);
    }

    public boolean canSelect(Point p) {
        final double a = (dest.x - src.x) / 2d;
        final double b = (dest.y - src.y) / 2d;
        final double c = Math.sqrt(Math.abs(Math.pow(a, 2) - Math.pow(b, 2)));
        double f1x, f1y, f2x, f2y;
        if (a > b) {
            f1x = src.x + a - c;
            f2x = src.x + a + c;
            f1y = src.y + b;
            f2y = src.y + b;
        } else {
            f1x = src.x + a;
            f2x = src.x + a;
            f1y = src.y + b - c;
            f2y = src.y + b + c;
        }
        return Math.sqrt(Math.pow(p.x - f1x, 2) + Math.pow(p.y - f1y, 2))
                + Math.sqrt(Math.pow(p.x - f2x, 2) + Math.pow(p.y - f2y, 2)) <= 2 * a;
    }
}
