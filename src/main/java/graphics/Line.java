package graphics;

import utils.Define;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line extends Graphic {
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.drawLine(src.x, src.y, dest.x, dest.y);
    }

    public boolean canSelect(Point p) {
        return new Line2D.Double(src, dest).ptSegDist(p) <= Define.SELECT_DISTANCE;
    }
}
