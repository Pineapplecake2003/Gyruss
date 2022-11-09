package Packages.Functions;

import Packages.RolePackage.*;
import javax.swing.*;
import java.util.*;

public class EnemyShoot extends Thread {

    private JPanel content;
    private EnemyPlane ep;
    private boolean pause = false;

    public EnemyShoot(JPanel content, EnemyPlane ep) {
        this.content = content;
        this.ep = ep;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    @Override
    public void run() {
        java.util.Timer shootSingal = new java.util.Timer();
        TimerTask shoot = new TimerTask() {
            @Override
            public void run() {
                if (!pause) {
                    Bullet enemyBullet = new Bullet(ep.getLabel().getLocation().x + 22, ep.getLabel().getLocation().y,
                            7, 12, 12, new ImageIcon("./Data/enemy/enemy_bullet.png").getImage(), content, false);
                    Checker.add(enemyBullet);
                }
            }
        };
        shootSingal.schedule(shoot, 0, 1500);
        while (ep.isAlive()) {
            try {
                sleep(10);
            } catch (Exception e) {
            }
        }
        shootSingal.cancel();
    }
}
