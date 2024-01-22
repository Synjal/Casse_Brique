package Game.Sprites;

import java.awt.*;

public abstract class Sprite {
    protected int x;
    protected int y;
    protected Color color;

    public Sprite(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public abstract void draw(Graphics2D drawing);
}
