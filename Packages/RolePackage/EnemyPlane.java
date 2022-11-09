package Packages.RolePackage;

import Packages.Functions.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class EnemyPlane extends Role {

    private JPanel content;
    private EnemyShoot sho;
    private SoundPlayer Music;

    public EnemyPlane(int x, int y, double speed, int width, int height, Image img, JPanel content) {
        super(x, y, speed, width, height, img);
        this.content = content;
        content.add(this.label);
        Music = new SoundPlayer("./Data/Sound/enemy_destoryed_1.wav");
        sho = new EnemyShoot(content, this);
        sho.setName("EnemyShoot");
        start();
        sho.start();
    }

    public EnemyShoot getEnemyShoot() {
        return sho;
    }

    public void Die() {
        surivie = false;
        content.remove(label);
        content.repaint();
        sho.interrupt();
    }

    @Override
    public void run() {
        java.util.Timer t = new java.util.Timer();
        TimerTask enemymove = new TimerTask() {
            @Override
            public void run() {
                if (!pause) {
                    label.setLocation(label.getLocation().x, (int) (label.getLocation().y + speed));
                }
            }
        };
        t.schedule(enemymove, 0, 25);
        while (surivie && label.getLocation().y < 700) {
            try {
                sleep(10);
            } catch (Exception e) {
            }
        }
        t.cancel();
        Music.PlayMusic();
    }
}
