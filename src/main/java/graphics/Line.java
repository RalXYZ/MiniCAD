package graphics;

import utils.Define;

import java.awt.*;

public class Line extends Graphic {
    @Override
    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.lineWidth));
        g.drawLine(src.x, src.y, dest.x, dest.y);
    }

    public boolean canSelect(Point p) {
        final double pToPointSrc = Math.sqrt(Math.pow(p.x - src.x, 2) + Math.pow(p.y - src.y, 2));
        final double pToPointDest = Math.sqrt(Math.pow(p.x - dest.x, 2) + Math.pow(p.y - dest.y, 2));
        double pToLine;
        if (src.x == dest.x) {
            pToLine = Math.abs(p.x - src.x);
        } else {
            final double k = ((double)(src.y - dest.y)) / (src.x - dest.x);
            final double a = -k;
            final double b = 1;
            final double c = k * src.x - src.y;
            pToLine = Math.abs(a * p.x + b * p.y + c) / Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        }
        return Math.min(Math.min(pToPointSrc, pToPointDest), pToLine) < Define.SELECT_DISTANCE;
    }
}
