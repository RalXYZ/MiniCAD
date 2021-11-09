package graphics;

import java.awt.*;

public class None extends Graphic {
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
    }

    public boolean canSelect(Point p) {
        return false;
    }
}
