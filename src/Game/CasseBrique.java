package Game;

import Game.Sprites.Balle;
import Game.Sprites.Barre;
import Game.Sprites.Brique;
import Game.Sprites.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CasseBrique extends Canvas {

    public static final int WINDOW_WIDTH         = 800;
    public static final int WINDOW_HEIGHT        = 600;
    public static final int BRICK_WIDTH          = 55;
    public static final int BRICK_HEIGHT         = 30;
    public static final int BRICK_LINE_NUMBER    = 12;
    public static final int BRICK_COLUMN_NUMBER  = 10;
    public static final int BRICK_POINTS         = 20;
    public static final int BALL_STARTING_NUMBER = 1;
    public static final int BALL_DIAMETER        = 20;
    public static final int BONUS_DIAMETER       = 10;
    public static boolean GAME_RUNNING         = true;
    public static boolean GAME_RESET           = false;

    Barre barre  = Barre.getInstance();
    Button play  = new Button(10, 20, Color.BLUE,    20, 20, "play");
    Button reset = new Button(32, 20, Color.magenta, 20, 20, "reset");

    public static ArrayList<Button> buttons    = new ArrayList<>();
    public static ArrayList<Balle>  balls      = new ArrayList<>();
    public static ArrayList<Balle>  ballsOut   = new ArrayList<>();
    public static ArrayList<Balle>  bonus      = new ArrayList<>();
    public static ArrayList<Balle>  bonusOut   = new ArrayList<>();
    public static ArrayList<Brique> briques    = new ArrayList<>();
    public static ArrayList<Brique> oldBriques = new ArrayList<>();

    int scoreMax = 0;

    public CasseBrique() {
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setFocusable(false);
        this.setIgnoreRepaint(true);

        // Components
        JFrame window = new JFrame();
        Container panel = window.getContentPane();

        window.pack();
        window.setTitle("Casse Brique");
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.requestFocus();
        panel.add(this);

        buttons.add(play);
        buttons.add(reset);

        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean left  = e.getKeyCode() == KeyEvent.VK_LEFT ;
                boolean right = e.getKeyCode() == KeyEvent.VK_RIGHT;

                if (left)  barre.move(false);
                if (right) barre.move(true);
            }
        });
        this.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = getMousePosition().x;
                int mouseY = getMousePosition().y;

                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (Button b : buttons) {
                        boolean   isButton = mouseX >= b.getX()
                                && mouseX <= b.getX() + b.getWidth()
                                && mouseY >= b.getY()
                                && mouseY <= b.getY() + b.getHeight();
                        if (isButton) {
                            b.addClickListener();
                        }
                    }
                }
            }
        });

        this.createBufferStrategy(2);
        window.setVisible(true);
        run();
    }

    public void init(){
        for (int i = 0; i < BALL_STARTING_NUMBER; i++)
            balls.add(new Balle(BALL_DIAMETER));
        for (int i = 1; i < BRICK_LINE_NUMBER; i++)
            for (int j = 1; j < BRICK_COLUMN_NUMBER; j++)
                briques.add(new Brique(BRICK_WIDTH * i, BRICK_HEIGHT * j));
        scoreMax = briques.size() * BRICK_POINTS;
    }

    public void update() {
        try {
            getBufferStrategy().show();
            do {
                if (GAME_RESET) reset();
                Graphics2D drawing = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
                drawing.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
                for (Button b : buttons) b.draw(drawing);
                drawing.dispose();
            }
            while(!GAME_RUNNING);
            Thread.sleep(1000 / 60);
        } catch (InterruptedException ignored){}
    }

    public void render() {
        Graphics2D drawing = (Graphics2D) this.getBufferStrategy().getDrawGraphics();
        drawing.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        drawing.setColor(Color.black);
        drawing.drawString("Score: " + (scoreMax - BRICK_POINTS * briques.size()), 10, 10);
        barre.draw(drawing);
        for (Button b : buttons) b.draw(drawing);
        for (Brique b : briques) b.draw(drawing);
        for (Balle b  : balls)   b.render(drawing, b, barre, briques,false);
        for (Balle b  : bonus)   b.render(drawing, b, barre, briques,true);

        clear();
        drawing.dispose();
    }

    public void run() {
        init();
        while (!briques.isEmpty() && !balls.isEmpty()) {
            update();
            render();
        }
        String msg = briques.isEmpty() ? "You win !" : "You lose.";
        JOptionPane.showMessageDialog(null, msg);
        System.exit(0);
    }

    public static void clear(){
        briques.removeAll(oldBriques);
        balls.removeAll(ballsOut);
        bonus.removeAll(bonusOut);
    }


    public void reset() {
        clear();
        barReset();
        briques.clear();
        balls.clear();
        bonus.clear();

        GAME_RESET = false;
        if (!GAME_RUNNING) { GAME_RUNNING = true;}
        run();
    }

    public void barReset() {
        barre = Barre.getInstance();
        barre.setX(WINDOW_WIDTH / 2 - 200 / 2);
        barre.setY(WINDOW_HEIGHT - 10 * 5);
        barre.setWidth(200);
    }

    public static void main(String[] args) {
        new CasseBrique();
    }
}