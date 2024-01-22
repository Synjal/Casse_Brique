package Game.Sprites;

import java.awt.*;

public abstract class Rectangle extends Sprite {
    protected int width;
    protected int height;

    public Rectangle(int x, int y, Color color, int width, int height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void draw(Graphics2D drawing) {
        drawing.setColor(color);
        drawing.fillRect(x, y, width, height);
    }
}
