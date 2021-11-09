package graphics;

import java.awt.*;

public class TextField extends Graphic {
    public static String text;
    private FontMetrics fm;

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        Font f = new Font("SansSerif", Font.PLAIN, Math.abs(src.y - dest.y));
        fm = g.getFontMetrics(f);
        g.setFont(f);
        g.drawString(TextField.text, src.x, src.y);
    }

    public boolean canSelect(Point p) {
        return ((src.x <= p.x && p.x <= src.x + fm.stringWidth(TextField.text))
                && (src.y >= p.y && p.y >= src.y - fm.getHeight()));
    }
}
