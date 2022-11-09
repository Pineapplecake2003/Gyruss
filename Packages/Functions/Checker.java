package Packages.Functions;

import Packages.RolePackage.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Checker extends Thread {

    private JPanel content;
    private static Player player;
    private static ArrayList<Bullet> AllBullet = new ArrayList<>();
    private static ArrayList<EnemyPlane> AllEnemyPlane = new ArrayList<>();
    private static ArrayList<Bouns> AllBouns = new ArrayList<>();
    private static Boss Boss;
    private static boolean HasBoss = false;
    private static boolean SetpauseYet = false;
    private SoundPlayer Music;

    public Checker(String s) {
        super(s);
    }

    Thread PlayerBoom = new Thread() {
        @Override
        public void run() {
            GameMenu.setCanPause(false);
            JLabel boom = new JLabel(new ImageIcon("./Data/player/player_boom.png"));
            boom.setBounds(player.getLabel().getLocation().x + 4,
                    player.getLabel().getLocation().y - 7, 63,
                    63);
            content.add(boom);
            try {
                sleep(250);
            } catch (Exception e) {
            }
            GameMenu.setState(2);
            clear();
            content.remove(boom);
            content.repaint();
            GameMenu.setCanPause(true);
            EnemyGenerater.setNeedBornBoss(true);
            EnemyGenerater.setPassthroughTime(0);
            BossShoot.setDelay(3000);
        }
    };

    public Checker(Player p, JPanel content, String s) {
        super(s);
        player = p;
        this.content = content;
        start();
        Music = new SoundPlayer("./Data/Sound/getBouns.wav");
    }

    public static void add(EnemyPlane e) {
        AllEnemyPlane.add(e);
    }

    public static void add(Bullet b) {
        AllBullet.add(b);
    }

    public static void add(Bouns b) {
        AllBouns.add(b);
    }

    public static void add(Boss boss) {
        Boss = boss;
        HasBoss = true;
    }

    @Override
    public void run() {
        while (GameMenu.getState()==1||GameMenu.getState()==4) {
            try {
                sleep(10);
            } catch (Exception e) {
            }
            if (GameMenu.getState() == 1) {
                if(SetpauseYet)
                {
                    PauseOrResume(false);
                }
                CheckePlayerCheckCoilion();
                CheckBullet();
            } else if (GameMenu.getState() == 4 && !SetpauseYet) {
                PauseOrResume(true);
            }
        }
    }

    public static void PauseOrResume(boolean pOr) {
        player.setPause(pOr);
        for (int i = 0; i <= AllEnemyPlane.size() - 1; i++) {
            AllEnemyPlane.get(i).setPause(pOr);
            AllEnemyPlane.get(i).getEnemyShoot().setPause(pOr);
        }
        for (int i = 0; i <= AllBullet.size() - 1; i++) {
            AllBullet.get(i).setPause(pOr);
        }
        for (int i = 0; i <= AllBouns.size() - 1; i++) {
            AllBouns.get(i).setPause(pOr);
        }
        if (HasBoss) {
            Boss.setPause(pOr);
            Boss.getBossShoot().setPause(pOr);
        }
        SetpauseYet = pOr;
    }

    public static void clear() {
        for (int i = 0; i <= AllBullet.size() - 1; i++) {
            AllBullet.get(i).Die();
        }
        AllBullet.clear();
        for (int i = 0; i <= AllEnemyPlane.size() - 1; i++) {
            AllEnemyPlane.get(i).Die();
        }
        AllEnemyPlane.clear();
        for (int i = 0; i <= AllBouns.size() - 1; i++) {
            AllBouns.get(i).Die();
        }
        AllBouns.clear();
        if (HasBoss) {
            Boss.Die();
        }
        Boss = null;
        HasBoss = false;
        GameMenu.BossHP.setIcon(null);
    }

    public void CheckePlayerCheckCoilion() {
        for (int i = 0; i <= AllEnemyPlane.size() - 1; i++) {
            if (AllEnemyPlane.get(i).getSurvive()) {
                if (CheckCoilion(player, AllEnemyPlane.get(i)) && player.getSurvive()) {
                    PlayerBoom.start();
                    
                    EnemyPlane tmp = AllEnemyPlane.get(i);
                    Thread EnemyDie = new Thread() {
                        @Override
                        public void run() {
                            tmp.Die();
                            JLabel Eboom = new JLabel(new ImageIcon("./Data/enemy/enemy_boom.png"));
                            Eboom.setBounds(tmp.getLabel().getLocation().x - 4, tmp.getLabel().getLocation().y - 13,
                                    63, 63);
                            content.add(Eboom);
                            try {
                                sleep(250);
                            } catch (Exception e) {
                            }
                            content.remove(Eboom);
                            content.repaint();
                        }
                    };
                    EnemyDie.start();
                    player.HPdecrease(300);
                    content.remove(player.getLabel());
                    content.repaint();
                    break;
                }
            }
        }
        if (HasBoss) {
            if (CheckCoilion(player, Boss) && player.getSurvive()) {
                PlayerBoom.start();
                player.HPdecrease(300);
                content.remove(player.getLabel());
                content.repaint();
            }
        }
        for (int i = 0; i <= AllBouns.size() - 1; i++) {
            if (CheckCoilion(player, AllBouns.get(i)) && player.getSurvive()) {
                Music.StopPlay();

                Music.PlayMusic();
                AllBouns.get(i).Die();
                AllBouns.remove(AllBouns.get(i));
                player.HPincrease();
                break;
            }
        }
    }

    public void CheckBullet() {
        for (int i = 0; i <= AllBullet.size() - 1; i++) {
            for (int j = 0; j <= AllEnemyPlane.size() - 1; j++) {
                if (CheckBullet(
                        new Point(AllBullet.get(i).getLabel().getLocation().x + 6,
                                AllBullet.get(i).getLabel().getLocation().y + 6),
                        new Rectangle(AllEnemyPlane.get(j).getLabel().getLocation().x,
                                AllEnemyPlane.get(j).getLabel().getLocation().y,
                                AllEnemyPlane.get(j).getLabel().getWidth(),
                                AllEnemyPlane.get(j).getLabel().getHeight()))
                        && AllBullet.get(i).isAlive()) {
                    if (AllBullet.get(i).getBelong()) {
                        EnemyPlane tmp = AllEnemyPlane.get(j);
                        AllEnemyPlane.remove(AllEnemyPlane.get(j));
                        AllBullet.get(i).Die();
                        AllBullet.remove(AllBullet.get(i));
                        Thread EnemyDie = new Thread() {
                            @Override
                            public void run() {
                                tmp.Die();
                                JLabel Eboom = new JLabel(new ImageIcon("./Data/enemy/enemy_boom.png"));
                                Eboom.setBounds(tmp.getLabel().getLocation().x - 4, tmp.getLabel().getLocation().y - 13,
                                        63, 63);
                                content.add(Eboom);
                                try {
                                    sleep(250);
                                } catch (Exception e) {
                                }
                                content.remove(Eboom);
                                content.repaint();
                            }
                        };
                        EnemyDie.start();
                        player.addScore(10);
                        return;
                    }
                }
            }
            if (CheckBullet(
                    new Point(AllBullet.get(i).getLabel().getLocation().x + 6,
                            AllBullet.get(i).getLabel().getLocation().y + 6),
                    new Rectangle(player.getLabel().getLocation().x,
                            player.getLabel().getLocation().y,
                            player.getLabel().getWidth(),
                            player.getLabel().getHeight()))) {
                if (!AllBullet.get(i).getBelong() && player.getSurvive()) {
                    new SoundPlayer("./Data/Sound/player_shooted.wav").PlayMusic();
                    player.HPdecrease(30);
                    AllBullet.get(i).Die();
                    AllBullet.remove(AllBullet.get(i));
                    if (!player.getSurvive()) {
                        PlayerBoom.start();
                    }
                    break;
                }
            }
            if (HasBoss && checkBossshootedBullet(AllBullet.get(i), Boss)) {
                if (AllBullet.get(i).getBelong() && Boss.getSurvive() && AllBullet.get(i).isAlive()) {
                    Boss.HPdecrease(10);
                    AllBullet.get(i).Die();
                    AllBullet.remove(AllBullet.get(i));
                    if (!Boss.getSurvive()) {
                        Thread BossBoom = new Thread() {
                            @Override
                            public void run() {
                                JLabel[] boom = new JLabel[50];
                                for (int i = 0; i <= boom.length - 1; i++) {
                                    int x = (int) (Boss.getLabel().getLocation().x
                                            + Math.random() * Boss.getLabel().getWidth());
                                    int y = (int) (Boss.getLabel().getLocation().y + 20
                                            + Math.random() * (Boss.getLabel().getHeight() - 100));
                                    boom[i] = new JLabel(new ImageIcon("./Data/enemy/enemy_boom.png"));
                                    boom[i].setBounds(x, y, 63, 63);
                                    content.add(boom[i]);
                                }
                                try {
                                    sleep(250);
                                } catch (Exception e) {
                                }
                                for (int i = 0; i <= boom.length - 1; i++) {
                                    content.remove(boom[i]);
                                }
                                content.repaint();
                                GameMenu.setState(3);
                                clear();
                                player.Die();
                                EnemyGenerater.setNeedBornBoss(true);
                                BossShoot.setDelay(3000);
                                EnemyGenerater.setPassthroughTime(0);
                            }
                        };
                        BossBoom.start();
                    }
                    break;
                }
            }
        }
    }

    public boolean checkBossshootedBullet(Bullet b, Boss Sep) {
        int bx = b.getLabel().getLocation().x + 6;
        int by = b.getLabel().getLocation().y + 6;

        if (bx >= Sep.getLabel().getLocation().x && bx <= Sep.getLabel().getLocation().x + Sep.getLabel().getWidth()) {
            if (by >= Sep.getLabel().getLocation().y
                    && by <= Sep.getLabel().getLocation().y + Sep.getLabel().getHeight()) {
                if (bx <= Sep.getLabel().getLocation().x + 125 && by <= Sep.getLabel().getLocation().y + 175) {
                    return true;
                } else if (bx >= Sep.getLabel().getLocation().x + 175 && by <= Sep.getLabel().getLocation().y + 175) {
                    return true;
                } else if (bx <= Sep.getLabel().getLocation().x + 175 && bx >= Sep.getLabel().getLocation().x + 125) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean CheckBullet(Point p1, Rectangle r) {
        if (p1.x >= r.x && p1.x <= r.x + r.width && p1.y >= r.y && p1.y <= r.y + r.height) {
            return true;
        } else {
            return false;
        }
    }

    public boolean CheckCoilion(Player p, EnemyPlane e) {
        int playerX = p.getLabel().getLocation().x;
        int playerY = p.getLabel().getLocation().y;
        int playerW = p.getWidth();
        int playerH = p.getHeight();

        int enemyX = e.getLabel().getLocation().x;
        int enemyY = e.getLabel().getLocation().y;
        int enemyW = e.getWidth();
        int enemyH = e.getHeight();

        int[][] epoints = new int[][] { { enemyX, enemyY },
                { enemyX + enemyW, enemyY },
                { enemyX, enemyY + enemyH },
                { enemyX + enemyW, enemyY + enemyH } };
        for (int po = 0; po <= 3; po++) {
            if (inRectangle(epoints[po], new Rectangle(playerX, playerY, playerW, playerH))) {
                return true;
            }
        }
        return false;
    }

    public boolean CheckCoilion(Player p, Boss e) {
        int playerX = p.getLabel().getLocation().x;
        int playerY = p.getLabel().getLocation().y;
        int playerW = p.getWidth();
        int playerH = p.getHeight();

        int enemyX = e.getLabel().getLocation().x;
        int enemyY = e.getLabel().getLocation().y;

        int[][] epoints = new int[][] { { playerX, playerY },
                { playerX + playerW, playerY },
                { playerX, playerY + playerH },
                { playerX + playerW, playerY + playerH } };
        for (int po = 0; po <= 3; po++) {
            if (inRectangle(epoints[po], new Rectangle(enemyX, enemyY, 125, 175))
                    || inRectangle(epoints[po], new Rectangle(enemyX + 125, enemyY, 50, 284))
                    || inRectangle(epoints[po], new Rectangle(enemyX + 175, enemyY, 125, 175))) {
                return true;
            }
        }
        return false;
    }

    public boolean CheckCoilion(Player p, Bouns b) {
        int playerX = p.getLabel().getLocation().x;
        int playerY = p.getLabel().getLocation().y;
        int playerW = p.getWidth();
        int playerH = p.getHeight();

        int bounsX = b.getLabel().getLocation().x;
        int bounsY = b.getLabel().getLocation().y;
        int bounsW = b.getWidth();
        int bounsH = b.getHeight();

        int[][] bpoints = new int[][] { { bounsX, bounsY },
                { bounsX + bounsW, bounsY },
                { bounsX, bounsY + bounsH },
                { bounsX + bounsW, bounsY + bounsH } };
        for (int po = 0; po <= 3; po++) {
            if (inRectangle(bpoints[po], new Rectangle(playerX, playerY, playerW, playerH))) {
                return true;
            }
        }
        return false;
    }

    private boolean inRectangle(int[] point, Rectangle r) {
        if (point[0] >= r.x && point[0] <= (r.x + r.width) && point[1] >= r.y && point[1] <= (r.y + r.height)) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Bullet> getBulletArray() {
        return AllBullet;
    }

    public static ArrayList<EnemyPlane> getEnemyPlaneArray() {
        return AllEnemyPlane;
    }

    public static ArrayList<Bouns> getBounsArray()
    {
        return AllBouns;
    }

    public static Player getPlayer(){
        return player;
    }

    public static boolean getHasBoss(){
        return HasBoss;
    } 

    public static Boss getBoss(){
        return Boss;
    }
}