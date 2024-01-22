package Game.Sprites;

import Game.CasseBrique;

import java.awt.*;

public class Brique extends Rectangle {

    public Brique(int x, int y) {
        super(x, y, Color.RED, CasseBrique.BRICK_WIDTH - 5 , CasseBrique.BRICK_HEIGHT - 5);
    }

}
