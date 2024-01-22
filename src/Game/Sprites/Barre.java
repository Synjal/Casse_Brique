package Game.Sprites;

import java.awt.*;

import static Game.CasseBrique.WINDOW_HEIGHT;
import static Game.CasseBrique.WINDOW_WIDTH;

public class Barre extends Rectangle {
    protected int speedBar = 20;

    private Barre(){
        super(
            WINDOW_WIDTH / 2 - 200 / 2,
            WINDOW_HEIGHT - 10 * 5,
            Color.black,
            200,
            10
        );
    }

    // Thread safe singleton w/ synchronized
    private static class BarreHelper {
        private static final Barre INSTANCE = new Barre();
    }

    public static Barre getInstance() {
        return BarreHelper.INSTANCE;
    }

    public void move(boolean direction) {
        if ((this.x <= 0 && !direction) ||
            (this.x >= WINDOW_WIDTH - width * 1.05 && direction))
            return;
        x += direction ? speedBar : -speedBar;
    }
}
