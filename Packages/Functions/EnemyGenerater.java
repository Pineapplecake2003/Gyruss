package Packages.Functions;

import Packages.RolePackage.*;
import javax.swing.*;
import java.util.*;

public class EnemyGenerater extends Thread {

    private static final int TIME_THE_BOSS_EMERGE = 20000;
    private final int TIME_CYCLE_OF_BOUNS = 10000;
    private final int TIME_CYCLE_OF_ENEMY = 1000;
    private static long remain = TIME_THE_BOSS_EMERGE;
    private static long passthroughTime = 0;
    private static long bounsremain = 0;
    private JPanel content;
    public JLabel BossHP;
    private int BoX;
    private static long startTime;
    private static long pauseTime;
    private boolean cancelYet = false;
    private static boolean needBornBoss = true;
    private SoundPlayer Music; 

    public EnemyGenerater(JPanel content, JLabel BossHP,SoundPlayer Music) {
        this.Music=Music;
        this.BossHP = BossHP;
        this.content = content;
        BossHP.setIcon(new ImageIcon("./Data/HP.png"));
        BossHP.setBounds(24, 86, 250, 10);
        content.add(BossHP);
        content.repaint();
        start();
    }

    public static void setRemainTime(long r) {
        remain = r;
    }

    public static void setBounsRemain(long r) {
        
        bounsremain = r;
    }

    public static void setNeedBornBoss(boolean need) {
        needBornBoss = need;
    }

    public static void setPassthroughTime(long p) {
        passthroughTime = p;
        long remainTime = 20000 - passthroughTime;
        long bounstime = 10000 - (passthroughTime % 10000);
        if (remainTime >= 0) {
            setRemainTime(remainTime);
            setNeedBornBoss(true);
        } else {
            setNeedBornBoss(false);
        }
        setBounsRemain(bounstime);
    }

    public static long getPassthroughTime() {
        return passthroughTime;
    }


    @Override
    public void run() {
        boolean setPauseYet = false;
        java.util.Timer startBorn = new java.util.Timer();
        java.util.Timer bounsBorn = new java.util.Timer();
        TimerTask bouns = new TimerTask() {
            @Override
            public void run() {
                if (!GameMenu.getPause()) {
                    int X = (int) (290 + Math.random() * (360));
                    Bouns bouns = new Bouns(X, -40, 2, 40, 40, new ImageIcon("./Data/bouns.png").getImage(), content);
                    Checker.add(bouns);
                    BoX = X;
                }
            }
        };
        TimerTask born = new TimerTask() {
            @Override
            public void run() {
                if (!GameMenu.getPause()) {
                    int EnX = (int) (290 + Math.random() * 336);
                    while (Math.abs(EnX - BoX) <= 10) {
                        EnX = (int) (290 + Math.random() * 336);
                    }
                    EnemyPlane pl = new EnemyPlane(EnX, -36, 2, 54, 36,
                            new ImageIcon("./Data/enemy/.gif/enemy_font111.gif").getImage(), content);
                    Checker.add(pl);
                }
            }
        };
        TimerTask Bossemerge = new TimerTask() {
            @Override
            public void run() {
                Music.StopPlay();
                Boss SE = new Boss(350, -284, 1, 300, 284,
                        new ImageIcon("./Data/enemy/.gif/boss.gif").getImage(), 5000, BossHP, content);
                        Saver.setBossnenrageTime(new Date().getTime());
                        Checker.add(SE);
                startBorn.cancel();
                cancelYet = true;
            }
        };

        if (needBornBoss) {
            startBorn.schedule(Bossemerge, remain);
            startBorn.schedule(born, 0, TIME_CYCLE_OF_ENEMY);
        }

        startTime = new Date().getTime();

        bounsBorn.schedule(bouns, bounsremain, TIME_CYCLE_OF_BOUNS);

        while (GameMenu.getState() == 1 || GameMenu.getState() == 4) {
            if (GameMenu.getState() == 4) {
                if (!setPauseYet) {
                    Bossemerge.cancel();
                    setPauseYet = true;
                    pauseTime = new Date().getTime();
                }
            } else if (GameMenu.getState() == 1) {
                if (setPauseYet) {
                    long remain = (TIME_THE_BOSS_EMERGE - (pauseTime - startTime));
                    Bossemerge = new TimerTask() {
                        @Override
                        public void run() {
                            Music.StopPlay();
                            Boss SE = new Boss(350, -284, 1, 300, 284,
                                    new ImageIcon("./Data/enemy/.gif/boss.gif").getImage(), 5000, BossHP, content);
                                    Saver.setBossnenrageTime(new Date().getTime());
                                    Checker.add(SE);
                            startBorn.cancel();
                            cancelYet = true;
                        }
                    };
                    if (!cancelYet) {
                        startBorn.schedule(Bossemerge, remain);
                    }
                    setPauseYet = false;
                }
            }
            try {
                sleep(5);
            } catch (Exception e) {
            }
        }
        startBorn.cancel();
        bounsBorn.cancel();
    }

    public static long getStartTime() {
        return startTime;
    }

    public static long getPauseTime() {
        return pauseTime;
    }
}
