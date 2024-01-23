package Game.Sprites;

import Game.CasseBrique;

import java.awt.*;
import java.util.ArrayList;

import static Game.CasseBrique.*;

public class Balle extends Sprite{

    private final int diametre;
    private int speedX;
    private int speedY;

    public Balle(int diametre) {
        super(
                WINDOW_WIDTH / 2 - BALL_DIAMETER / 2,
                WINDOW_HEIGHT - BALL_DIAMETER * 3,
                new Color((float) Math.random(), (float) Math.random(), (float) Math.random())
        );

        this.diametre = diametre;
        this.speedX = 0;
        this.speedY = 0;
    }

    public Balle(int diametre, int x, int y, Color color) {
        super(x, y, color);
        this.diametre = diametre;
        this.speedX = 0;
        this.speedY = 3;
    }

    public void move(Balle ball, Barre bar, ArrayList<Brique> briques, boolean isBonus) {
        x += speedX;
        y += speedY;
        this.windowCollision(ball);
        this.barCollision(ball, bar, isBonus);
        this.bricksCollision(briques, isBonus);
    }

    public void windowCollision(Balle b){
        boolean up           = y <= 0;
        boolean down         = y >= WINDOW_HEIGHT;
        boolean rightAndLeft = x >= (WINDOW_WIDTH - diametre * 1.5) || x <= 0;

        if (up)           speedY = -speedY;
        if (rightAndLeft) speedX = -speedX;
        if (down)         {speedY = 0; CasseBrique.ballsOut.add(b);}
    }

    public void barCollision(Balle bonus, Barre b, boolean isBonus){
        int numberBarSection = 7;
        int barSection       = b.width / numberBarSection;

        boolean bar = y >= b.y - diametre
                   && x >= b.x
                   && x <= b.x + b.width;

        boolean barLeft        = x >= b.x && x <= b.x + barSection;
        boolean barMiddle      = x >= b.x + 3 * barSection && x <= b.x + 4 * barSection;
        boolean barRight       = x >= b.x + 6 * barSection && x <= b.x + 7 * barSection;

        if (bar) {
            if (isBonus){
                b.x -= 5;
                b.width += 10;
                CasseBrique.bonusOut.add(bonus);
            } else {
                if (barMiddle) speedX =  0; speedY = -8;
                if (barLeft) speedX = speedX > 0 ? -3 : speedX - 3;
                if (barRight) speedX = speedX > 0 ? speedX + 3 : 3;
            }
        }
    }

    public void bricksCollision(ArrayList<Brique> briques, boolean isBonus) {
        if (!isBonus){
            for (Brique block : briques) {
                boolean brick = x + diametre >= block.x
                             && x <= block.x + block.width
                             && y + diametre >= block.y
                             && y <= block.y + block.height;

                if (brick) {
                    CasseBrique.oldBriques.add(block);
                    if (x >= block.x + block.width || x + diametre <= block.width) {
                        speedX = -speedX;
                    } else speedY = -speedY;

                    if (Math.random() <= 0.2) // 20% chance to launch bonus
                        CasseBrique.bonus.add(new Balle(
                                BONUS_DIAMETER,
                                block.x + block.width / 2,
                                block.y + block.height / 2,
                                Color.ORANGE
                        ));
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D drawing) {
        drawing.setColor(color);
        drawing.fillOval(x, y, diametre, diametre);
    }

    public void render(Graphics2D drawing, Balle ball, Barre bar, ArrayList<Brique> briques, boolean isBonus) {
        draw(drawing);
        move(ball, bar, briques, isBonus);
    }

}
