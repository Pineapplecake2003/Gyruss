package Packages.RolePackage;

import Packages.Functions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Player extends Role implements KeyListener {

    final int MAX_HP = 300;
    final int CYCLE_OF_PLAYER_SHOOT = 250;
    final double RATE_OF_HP_RECOVER = 0.3;
    private int score;

    private boolean w = false;
    private boolean wMoving = false;
    private boolean a = false;
    private boolean aMoving = false;
    private boolean s = false;
    private boolean sMoving = false;
    private boolean d = false;
    private boolean dMoving = false;
    private boolean shooting = false;

    private JLabel HPBar;
    private JLabel[] Score;
    private int[] S = new int[3];
    private JFrame GameFrame;
    private JPanel content;
    String[] imgfiles = new String[] {
            new String("./Data/player/.gif/player_font2.gif"),
            new String("./Data/player/.gif/player_left2.gif"),
            new String("./Data/player/.gif/player_right2.gif"),
            new String("./Data/player/.gif/player_boom2.gif"),
    };
    private SoundPlayer Music ;

    Thread playerShooting = new Thread() {
        java.util.Timer shootTimer = new java.util.Timer();
        TimerTask shoot = new TimerTask() {
            @Override
            public void run() {
                if (shooting && !pause) {
                    Music.PlayMusic();
                    Bullet playerBullet = new Bullet(label.getLocation().x + 30,
                            label.getLocation().y,
                            10, 12, 12, new ImageIcon("./Data/player/player_bullet1.png").getImage(), content,
                            true);
                    content.add(playerBullet.getLabel());
                    Checker.add(playerBullet);
                }
            }
        };

        @Override
        public void run() {
            shootTimer.schedule(shoot, 0, CYCLE_OF_PLAYER_SHOOT);
            while (surivie && (GameMenu.getState() == 1 || GameMenu.getState() == 4)) {
                try {
                    sleep(10);
                } catch (Exception e) {
                }
            }
            shootTimer.cancel();
        }
    };

    public Player(int x, int y, double speed, int width, int height, Image img, JPanel content, JFrame GameFrame) {
        super(x, y, speed, width, height, img);
        this.content = content;
        this.GameFrame = GameFrame;
        content.add(this.label);
        GameFrame.addKeyListener(this);
        HPBar = new JLabel(new ImageIcon("./Data/HP.png"));
        HPBar.setBounds(25, 594, 250, 10);
        content.add(HPBar);
        setHP(300);
        Music = new SoundPlayer("./Data/Sound/fire.wav");
        score = 0;
        Score = new JLabel[] { new JLabel(), new JLabel(), new JLabel() };
        for (int i = 0; i <= 2; i++) {
            Score[i].setBounds(170 + i * 20, 367, 20, 20);
        }
        paintScore();
        playerShooting.start();
    }

    public void addScore(int s) {
        score += s;
        if (surivie) {
            paintScore();
        }
    }

    public void setContent(JPanel content) {
        this.content = content;
    }

    public void setShoot(boolean shooting){
        this.shooting=shooting;
    }

    public void setScore(int score) {
        this.score = score;
        paintScore();
    }

    public void setHP(int HP) {
        this.HP = HP;
        double length = 250 * ((double) HP / 300);
        HPBar.setSize((int) length, 10);
    }

    public void HPdecrease(int Damage) {
        HP -= Damage;
        double length = 250 * ((double) HP / 300);
        HPBar.setSize((int) length, 10);
        if (HP <= 0) {
            Die();
        }
    }

    public void HPincrease() {
        HP += (int) (HP * RATE_OF_HP_RECOVER);
        if (HP >= 300) {
            HP = 300;
        }
        double length = 250 * ((double) HP / 300);
        HPBar.setSize((int) length, 10);
    }

    public void setScoreLabel() {
        int tmp = score;
        for (int i = 2; i >= 0; i--) {
            S[i] = tmp % 10;
            tmp /= 10;
        }
        for (int i = 0; i <= 2; i++) {
            Score[i].setIcon(new ImageIcon("./Data/number/" + Integer.toString(S[i]) + ".png"));
        }
    }

    public void paintScore() {
        setScoreLabel();
        int i;
        for (i = 0; i <= 2; i++) {
            if (S[i] != 0) {
                break;
            }
        }
        if (i == 3) {
            content.add(Score[i - 1]);
        } else {
            for (; i <= 2; i++) {
                content.add(Score[i]);
            }
        }
    }

    public void Die() {
        surivie = false;
        content.remove(label);
        content.remove(HPBar);
        for (int i = 0; i <= 2; i++) {
            GameFrame.getLayeredPane().remove(Score[i]);
            content.remove(Score[i]);
        }
        content.repaint();
        GameFrame.removeKeyListener(this);
        shooting = false;
        w = false;
        a = false;
        s = false;
        d = false;
    }

    public int getScore() {
        return score;
    }

    public boolean getShooting(){
        return shooting;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {

            case 'w':
                w = true;
                Thread Wmove = new Thread() {
                    @Override
                    public synchronized void run() {
                        while (w && label.getLocation().y > 200 && surivie && !pause) {
                            try {
                                sleep(25);
                            } catch (Exception e) {
                            }
                            label.setLocation(label.getLocation().x, (int) (label.getLocation().y - speed));
                        }
                    }
                };
                if (!wMoving) {
                    Wmove.start();
                    wMoving = true;
                }
                break;
            case 'a':
                a = true;
                Thread Amove = new Thread() {
                    @Override
                    public synchronized void run() {
                        while (a && surivie && !pause) {
                            if (label.getLocation().x > 290) {
                                try {
                                    sleep(25);
                                } catch (Exception e) {
                                }
                                label.setIcon(new ImageIcon(imgfiles[1]));
                                label.setLocation((int) (label.getLocation().x - speed), label.getLocation().y);
                            }
                            label.setIcon(new ImageIcon(imgfiles[1]));
                        }
                        label.setIcon(new ImageIcon(imgfiles[0]));
                    }
                };
                if (!aMoving) {
                    Amove.start();
                    aMoving = true;
                }
                break;
            case 's':
                s = true;
                Thread Smove = new Thread() {
                    @Override
                    public synchronized void run() {
                        while (s && label.getLocation().y < 560 && surivie && !pause) {
                            try {
                                sleep(25);
                            } catch (Exception e) {
                            }
                            label.setLocation(label.getLocation().x, (int) (label.getLocation().y + speed));
                        }
                    }
                };
                if (!sMoving) {
                    Smove.start();
                    sMoving = true;
                }
                break;
            case 'd':
                d = true;
                Thread Dmove = new Thread() {
                    @Override
                    public synchronized void run() {
                        while (d && surivie && !pause) {
                            label.setIcon(new ImageIcon(imgfiles[2]));
                            if (label.getLocation().x < 614) {
                                try {
                                    sleep(25);
                                } catch (Exception e) {
                                }
                                label.setIcon(new ImageIcon(imgfiles[2]));
                                label.setLocation((int) (label.getLocation().x + speed), label.getLocation().y);
                            }
                        }
                        label.setIcon(new ImageIcon(imgfiles[0]));
                    }
                };
                if (!dMoving) {
                    Dmove.start();
                    dMoving = true;
                }
                break;
            case ' ':
                shooting = !shooting;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                w = false;
                wMoving = false;
                break;
            case 'a':
                a = false;
                aMoving = false;
                break;
            case 's':
                s = false;
                sMoving = false;
                break;
            case 'd':
                d = false;
                dMoving = false;
                break;
            default:
                break;
        }
    }
}
