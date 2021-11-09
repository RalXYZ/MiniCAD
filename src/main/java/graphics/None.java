package graphics;

import java.awt.*;

public class None extends Graphic {
    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public boolean canSelect(Point p) {
        return false;
    }
}
