package Game.Sprites;

import Game.CasseBrique;
import Game.Listeners.ClickListener;

import javax.swing.*;
import java.awt.*;

import static Game.CasseBrique.GAME_RUNNING;
import static Game.CasseBrique.GAME_RESET;

public class Button extends Rectangle implements ClickListener {
    private final String name;
    ImageIcon play = new ImageIcon("assets/img/play.png");
    ImageIcon pause = new ImageIcon("assets/img/stop.png");

    public Button(int x, int y, Color color, int width, int height, String name) {
        super(x, y, color, width, height);
        this.name = name;
    }

    public void addClickListener(ClickListener listener) {
        onClick();
    }

    @Override
    public void onClick() {
        switch (name) {
            case "play":
                GAME_RUNNING = !GAME_RUNNING;
                break;
            case "reset":
                GAME_RESET = true;
                break;
        }
    }

    @Override
    public void draw(Graphics2D drawing) {
        if (GAME_RUNNING && name.equals("play")) drawing.drawImage(pause.getImage(), x, y, null);
        else                                     drawing.drawImage(play .getImage(), x, y, null);
    }
}
