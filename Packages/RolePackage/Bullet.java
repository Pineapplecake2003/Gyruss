package Packages.RolePackage;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Bullet extends Role {

    private boolean belong = true;
    private JPanel content;


    public Bullet(int x, int y, double speed, int width, int height, Image img, JPanel content, boolean belong) {
        super(x, y, speed, width, height, img);
        this.belong = belong;
        this.content = content;
        content.add(label);
        start();
    }

    public boolean getBelong() {
        return belong;
    }

    @Override
    public void run() {
        java.util.Timer movetimer = new java.util.Timer();
        TimerTask move = new TimerTask() {
            @Override
            public void run() {
                if (!pause) {
                    if (belong) {
                        label.setLocation(label.getLocation().x, (int) (label.getLocation().y - speed));
                    } else {
                        label.setLocation(label.getLocation().x, (int) (label.getLocation().y + speed));
                    }
                }
            }
        };
        movetimer.schedule(move, 0, 30);
        while (label.getLocation().y <= 670 && label.getLocation().y >= -40 && surivie) {
            try {
                sleep(10);
            } catch (Exception e) {
            }
        }
        movetimer.cancel();
        label.setIcon(null);
        Die();
    }

    @Override
    public void Die() {
        surivie = false;
        content.remove(label);
        content.repaint();
    }
}
