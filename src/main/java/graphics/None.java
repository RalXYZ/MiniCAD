package graphics;

import java.awt.*;

public class None extends Graphic {
    public void draw(Graphics2D g) {

    }

    public boolean canSelect(Point p) {
        return false;
    }
}
