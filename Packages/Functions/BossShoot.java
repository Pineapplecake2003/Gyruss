package Packages.Functions;

import Packages.RolePackage.*;
import javax.swing.*;
import java.util.*;

public class BossShoot extends Thread {

    private Boss Sep;
    private JPanel content;
    private boolean pause;
    private static long delay=3000;

    public BossShoot(JPanel content, Boss Sep) {
        this.content = content;
        this.Sep = Sep;
        start();
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }


    public static void setDelay(long del){
        delay =del;
    }

    public static long getDelay() {
        return delay;
    }

    @Override
    public void run() {
        java.util.Timer shootTimer = new java.util.Timer();
        TimerTask shoot = new TimerTask() {
            @Override
            public void run() {
                if (!pause) {
                    Bullet enemyBullet0 = new Bullet(Sep.getLabel().getLocation().x,
                            Sep.getLabel().getLocation().y + 120,
                            7, 12, 12, new ImageIcon("./Data/enemy/enemy_bullet.png").getImage(), content, false);
                    Checker.add(enemyBullet0);
                    Bullet enemyBullet3 = new Bullet(Sep.getLabel().getLocation().x + 150,
                            Sep.getLabel().getLocation().y + 284,
                            7, 12, 12, new ImageIcon("./Data/enemy/enemy_bullet.png").getImage(), content, false);
                    Checker.add(enemyBullet3);
                    Bullet enemyBullet5 = new Bullet(Sep.getLabel().getLocation().x + 300,
                            Sep.getLabel().getLocation().y + 120,
                            7, 12, 12, new ImageIcon("./Data/enemy/enemy_bullet.png").getImage(), content, false);
                    Checker.add(enemyBullet5);
                }
            }
        };
        shootTimer.schedule(shoot, delay, 500);
        while (Sep.getSurvive()) {
            try {
                sleep(25);
            } catch (Exception e) {
            }
        }
        shootTimer.cancel();
    }
}
