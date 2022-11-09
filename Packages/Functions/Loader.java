package Packages.Functions;

import Packages.RolePackage.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Loader {

    private JPanel content;
    private JFrame GameFrame;
    private long remainTime;
    private long bossmoveRemain;
    private SoundPlayer Music;

    public Loader(JPanel content, JFrame GameFrame,SoundPlayer Music) {
        this.content = content;
        this.GameFrame = GameFrame;
        this.Music=Music;
        ReadArchive();
    }

    private void ReadArchive() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./Archive/SavedData.dat"));
            String title = reader.readLine();

            while (title != null) {
                if (title.equals("Player")) {
                    String[] playerDatas = reader.readLine().split(" ");
                    Player pl = new Player(Integer.parseInt(playerDatas[0]), Integer.parseInt(playerDatas[1]), 4, 72,
                            48,
                            new ImageIcon("./data/player/.gif/player_font2.gif").getImage(), content, GameFrame);
                    pl.setHP(Integer.parseInt(playerDatas[2]));
                    pl.setScore(Integer.parseInt(playerDatas[3]));
                    pl.setShoot(Boolean.parseBoolean(playerDatas[4]));
                    new Checker(pl, content, "CHECKER");

                } else if (title.equals("Enemys")) {
                    String datalines = reader.readLine();
                    while (!datalines.isEmpty()) {
                        String[] Enemysdata = datalines.split(" ");
                        int x = Integer.parseInt(Enemysdata[0]);
                        int y = Integer.parseInt(Enemysdata[1]);
                        EnemyPlane ep = new EnemyPlane(x, y, 2, 54, 36,
                                new ImageIcon("./Data/enemy/.gif/enemy_font111.gif").getImage(), content);
                        Checker.add(ep);
                        datalines = reader.readLine();
                    }

                } else if (title.equals("Bullets")) {
                    String lines = reader.readLine();
                    while (!lines.isEmpty()) {
                        String[] BulletsXY = lines.split(" ");
                        int x = Integer.parseInt(BulletsXY[0]);
                        int y = Integer.parseInt(BulletsXY[1]);
                        boolean belong = Boolean.parseBoolean(BulletsXY[2]);
                        ImageIcon pic = (belong) ? new ImageIcon("./Data/player/player_bullet.png")
                                : new ImageIcon("./Data/enemy/enemy_bullet.png");
                        Bullet bullet = new Bullet(x, y, 7, 12, 12,
                                pic.getImage(), content, belong);
                        Checker.add(bullet);
                        lines = reader.readLine();
                    }

                } else if (title.equals("Bouns")) {
                    String lines = reader.readLine();
                    while (!lines.isEmpty()) {
                        String[] BounsData = lines.split(" ");
                        int x = Integer.parseInt(BounsData[0]);
                        int y = Integer.parseInt(BounsData[1]);
                        Bouns bouns = new Bouns(x, y, 2, 40, 40, new ImageIcon("./Data/bouns.png").getImage(), content);
                        Checker.add(bouns);
                        lines = reader.readLine();
                    }

                } else if (title.equals("Time")) {
                    long passthroughTime = Long.parseLong(reader.readLine());
                    EnemyGenerater.setPassthroughTime(passthroughTime);
                    remainTime = 20000 - passthroughTime;
                    if (remainTime < 0) {
                        bossmoveRemain = 5000 - (passthroughTime - 20000)+3000;
                        if(bossmoveRemain<0){
                            bossmoveRemain=0;
                        }
                    }

                } else if (title.equals("Boss")) {
                    String lines = reader.readLine();
                    while (lines != null) {
                        Music.StopPlay();
                        String[] BossData = lines.split(" ");
                        int x = Integer.parseInt(BossData[0]);
                        int y = Integer.parseInt(BossData[1]);
                        int HP = Integer.parseInt(BossData[2]);
                        Saver.setAgesOfBoss(Long.parseLong(BossData[3]));
                        long delay =3000-(Saver.getAgesOfBoss());
                        if(delay>0){
                            BossShoot.setDelay(delay);
                        }
                        else{
                            BossShoot.setDelay(0);
                        }
                        Boss SE = new Boss(x, y, 1, 300, 284,
                                new ImageIcon("./Data/enemy/.gif/boss.gif").getImage(), bossmoveRemain, GameMenu.BossHP,
                                content);
                        Saver.setBossnenrageTime(new Date().getTime());
                        SE.HPrepaint(HP);
                        Checker.add(SE);
                        lines = reader.readLine();
                    }
                }
                title = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}