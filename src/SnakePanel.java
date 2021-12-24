import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener {

    /**
     * 
    **/
    private static final long serialVersionUID = 1L;
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int UNIT_SIZE = 25;
    public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    public final int DELAY = 75;
    public final int x[] = new int[GAME_UNITS];
    public final int y[] = new int[GAME_UNITS];
    public int bodyParts = 6;
    public int micesEaten = 0;
    public int miceX;
    public int miceY;
    public char direction = 'R';
    public boolean running = false;
    public Timer timer;
    public Random random;


    
    SnakePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.game();
    }

    public void game() {
        this.newMice();
        this.running = true;
        this.timer = new Timer(this.DELAY, this);
        this.timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(this.miceX, this.miceY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.CYAN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            g.setColor(Color.red);
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score : " + micesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score : " + micesEaten)) / 2,
                    g.getFont().getSize());
        
        }
        else {
            gameOver(g);
        }

    }

    public void newMice() {
        this.miceX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        this.miceY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];
        }

        switch (this.direction) {
        case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;
        case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
        case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
        case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;
        }
    }

    public void checkMice() {
        if ((x[0] == miceX) && (y[0] == miceY)) {
            bodyParts++;
            micesEaten++;
            newMice();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        if (x[0] < 0) running = false;
    
        if (x[0] > SCREEN_WIDTH) running = false;
        
        if (y[0] < 0) running = false;
    
        if (y[0] > SCREEN_HEIGHT) running = false;

        if(!running) timer.stop();
    }
    
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkMice();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') direction = 'D';
                    break;
            }
        }
    }
    
}
