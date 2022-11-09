package Packages.RolePackage;

import Packages.Functions.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Boss extends Role {

    private boolean dir = true;
    private JPanel content;
    private JLabel amountOfHP;
    private BossShoot strsho;
    private long remain = 5000;
    private SoundPlayer BossBGM;

    public Boss(int x, int y, double speed, int width, int height, Image img, long remain, JLabel amountOfHP,
            JPanel content) {
        super(x, y, speed, width, height, img);
        this.remain = remain;
        this.content = content;
        this.amountOfHP = amountOfHP;
        BossBGM=new SoundPlayer("./Data/Sound/Boss.wav");
        setHP(1000);
        content.add(label);
        start();
        strsho = new BossShoot(content, this);
    }

    public BossShoot getBossShoot() {
        return strsho;
    }

    public void HPrepaint(int NewHP) {
        setHP(NewHP);
        double length = 250 * ((double) HP / 1000);
        amountOfHP.setSize((int) length, 10);
    }

    @Override
    public void run() {
        BossBGM.PlayLoopMusic();
        java.util.Timer BossmoveTimer = new java.util.Timer();
        TimerTask move = new TimerTask() {
            @Override
            public void run() {
                boolean brea = dir;
                while (surivie) {
                    if (dir) {
                        while (label.getLocation().x >= 290) {
                            if (pause) {
                                brea = true;
                                break;
                            }
                            label.setLocation(label.getLocation().x - 1, label.getLocation().y);
                            try {
                                sleep(25);
                            } catch (Exception e) {
                            }
                            dir = true;
                            brea = false;
                        }
                        if (!brea) {
                            dir = false;
                        }
                    } else {
                        while (label.getLocation().x <= 385) {
                            if (pause) {
                                brea = true;
                                break;
                            }
                            label.setLocation(label.getLocation().x + 1, label.getLocation().y);
                            try {
                                sleep(25);
                            } catch (Exception e) {
                            }
                            dir = false;
                            brea = false;
                        }
                        if (!brea) {
                            dir = true;
                        }
                    }
                }
            }
        };
        while (GameMenu.getState() == 1 && label.getLocation().y <= 10) {
            if (!pause) {
                label.setLocation(label.getLocation().x, (int) (label.getLocation().y + speed));
            }
            try {
                sleep(10);
            } catch (Exception e) {
            }
        }
        BossmoveTimer.schedule(move, remain);
        while (GameMenu.getState() == 1 || GameMenu.getState() == 4) {
            try {
                sleep(10);
            } catch (Exception e) {
            }
        }
        BossBGM.StopPlay();
        BossmoveTimer.cancel();
        SoundPlayer Music =new SoundPlayer("./Data/Sound/BossDie.wav");
        Music.PlayMusic();
    }

    public void HPdecrease(int Damage) {
        HP -= Damage;
        double length = 250 * ((double) HP / 1000);
        amountOfHP.setSize((int) length, 10);
        if (HP <= 0) {
            Die();
        }
    }

    @Override
    public void Die() {
        surivie = false;
        content.remove(label);
        content.remove(amountOfHP);
        content.repaint();
    }
}
