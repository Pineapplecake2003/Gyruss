package Packages.Functions;

import Packages.RolePackage.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GameMenu implements KeyListener {

    private static boolean pause = false;
    private static boolean canPause = true;
    private static int state = 0;
    private int pauseSelect = 1;
    private int startSelect = 1;
    private boolean scheduled = false;
    private boolean firstPause = false;
    private JLabel selectLabel;
    private JLabel startMenuLabel;
    private JPanel content;
    private JFrame GameFrame;
    private JLabel GameBackgroundlabel_1;
    private JLabel GameBackgroundlabel_2;
    private JLabel GameBackgroundlabel_3;
    private JLabel gameOverLabel;
    private JLabel gameWinLabel;
    private JLabel gamePauseLabel;
    private Player player;
    private java.util.Timer backmove;
    public static JLabel BossHP;
    private SoundPlayer Music;
    TimerTask backmoveloop = new TimerTask() {
        @Override
        public void run() {
            if (!pause) {
                GameBackgroundlabel_1.setLocation(GameBackgroundlabel_1.getLocation().x,
                        GameBackgroundlabel_1.getLocation().y + 1);
                GameBackgroundlabel_2.setLocation(GameBackgroundlabel_2.getLocation().x,
                        GameBackgroundlabel_2.getLocation().y + 1);
                if (GameBackgroundlabel_2.getLocation().y == 0) {
                    GameBackgroundlabel_1.setLocation(GameBackgroundlabel_1.getLocation().x,
                            GameBackgroundlabel_2.getLocation().y - 1234);
                } else if (GameBackgroundlabel_1.getLocation().y == 0) {
                    GameBackgroundlabel_2.setLocation(GameBackgroundlabel_1.getLocation().x,
                            GameBackgroundlabel_1.getLocation().y - 1234);
                }
            }
        }
    };

    public GameMenu(JPanel content, JFrame GameFrame) {

        this.GameFrame = GameFrame;
        this.content = content;
        startMenuLabel = new JLabel(new ImageIcon("./Data/startmenu.png"));
        selectLabel = new JLabel(new ImageIcon("./Data/selectarrow.png"));

        startMenuLabel.setBounds(0, 0, 700, 613);
        selectLabel.setBounds(60, 446, 23, 45);
        content.add(selectLabel);
        content.add(startMenuLabel);

        gameOverLabel = new JLabel(new ImageIcon("./Data/gameover.png"));
        gameWinLabel = new JLabel(new ImageIcon("./Data/win.png"));
        gamePauseLabel = new JLabel(new ImageIcon("./Data/pause.png"));

        GameBackgroundlabel_1 = new JLabel(new ImageIcon("./Data/background2.png"));
        GameBackgroundlabel_2 = new JLabel(new ImageIcon("./Data/background2.png"));
        GameBackgroundlabel_3 = new JLabel(new ImageIcon("./Data/count_background3.png"));

        GameBackgroundlabel_3.setBounds(0, 0, 300, 650);
        GameBackgroundlabel_2.setBounds(290, -584, 400, 1234);
        GameBackgroundlabel_1.setBounds(290, GameBackgroundlabel_2.getLocation().y - 1234, 400, 1234);

        BossHP = new JLabel(new ImageIcon("./Data/HP.png"));
        BossHP.setBounds(24, 86, 250, 10);

        Music = new SoundPlayer("./Data/Sound/Start_Up.wav");
        Music.PlayMusic();

        GameFrame.addKeyListener(this);
    }

    public void GameOver() {
        for (int i = 0; i <= Checker.getBulletArray().size() - 1; i++) {
            content.remove(Checker.getBulletArray().get(i).getLabel());
        }
        for (int i = 0; i <= Checker.getEnemyPlaneArray().size() - 1; i++) {
            content.remove(Checker.getEnemyPlaneArray().get(i).getLabel());
        }
        content.repaint();
        GameFrame.getLayeredPane().remove(GameBackgroundlabel_1);
        GameFrame.getLayeredPane().remove(GameBackgroundlabel_2);
        GameFrame.getLayeredPane().remove(GameBackgroundlabel_3);
        selectLabel.setLocation(207, 318);
    }

    public JLabel getstartMenuLabel() {
        return startMenuLabel;
    }

    public JLabel getSELECTLabel() {
        return selectLabel;
    }

    public static int getState() {
        return state;
    }

    public static boolean getPause() {
        return pause;
    }

    public static void setState(int s) {
        state = s;
    }

    public static void setCanPause(boolean P) {
        canPause = P;
    }

    public static boolean getCanPause() {
        return canPause;
    }
    public SoundPlayer getSoundPlayer(){
        return Music;
    } 

    public void setSoundPlayyer(SoundPlayer Music){
        this.Music=Music;
    }

    private void GameStart() {
        Music.StopPlay();
        Music.setMusic("./Data/Sound/BGM.wav");
        Music.PlayLoopMusic();
        state = 1;
        Integer Layer = Integer.MIN_VALUE;
        GameFrame.getLayeredPane().add(GameBackgroundlabel_1,
                Layer /* new Integer(Integer.MIN_VALUE) */);
        GameFrame.getLayeredPane().add(GameBackgroundlabel_2,
                Layer /* new Integer(Integer.MIN_VALUE) */);
        GameFrame.getLayeredPane().add(GameBackgroundlabel_3,
                Layer /* new Integer(Integer.MIN_VALUE) */);
        backmove = new java.util.Timer();
        if (!scheduled) {
            backmove.schedule(backmoveloop, 0, 25);
            scheduled = true;
        }
        Thread gameOverThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        sleep(10);
                    } catch (Exception e) {
                    }
                    if (state == 0) {

                    } else if (state == 2) {
                        Music.StopPlay();
                        Music.setMusic("./Data/Sound/PlayerBoom.wav");
                        Music.PlayMusic();
                        try {
                            sleep(500);
                        } catch (Exception e) {
                        }
                        Music.StopPlay();
                        Music.setMusic("./Data/Sound/GameOver.wav");
                        Music.PlayMusic();
                        gameOverLabel = new JLabel(new ImageIcon("./Data/gameover.png"));
                        selectLabel = new JLabel(new ImageIcon("./Data/selectarrow.png"));
                        gameOverLabel.setBounds(0, 0, 700, 613);
                        selectLabel.setBounds(207, 318, 23, 45);
                        content.add(selectLabel);
                        content.add(gameOverLabel);
                        GameOver();
                        break;
                    } else if (state == 3) {
                        Music.StopPlay();
                        try {
                            sleep(500);
                        } catch (Exception e) {
                        }
                        Music.setMusic("./Data/Sound/Win.wav");
                        Music.PlayMusic();
                        gameWinLabel = new JLabel(new ImageIcon("./Data/win.png"));
                        selectLabel = new JLabel(new ImageIcon("./Data/selectarrow.png"));
                        gameWinLabel.setBounds(0, 0, 700, 613);
                        selectLabel.setBounds(207, 318, 23, 45);
                        content.add(selectLabel);
                        content.add(gameWinLabel);
                        GameOver();
                        break;
                    } else if (state == 4) {
                        if (pause) {
                            if (!firstPause) {
                                gamePauseLabel.setIcon(new ImageIcon("./Data/pause1.png"));
                                selectLabel.setIcon(new ImageIcon("./Data/selectarrow2.png"));
                                gamePauseLabel.setBounds(0, 150, 280, 134);
                                selectLabel.setBounds(19, 183, 23, 45);
                                content.add(selectLabel);
                                content.add(gamePauseLabel);
                                content.repaint();
                                pauseSelect = 1;
                                firstPause = true;
                            }
                        }
                    }
                }
            }
        };
        gameOverThread.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (state == 0) {
            switch (e.getKeyChar()) {
                case 'w':
                case 'W':
                    if (startSelect == 1) {
                        startSelect = 3;
                    } else {
                        startSelect--;
                    }
                    break;

                case 's':
                case 'S':
                    if (startSelect == 3) {
                        startSelect = 1;
                    } else {
                        startSelect++;
                    }
                    break;

                case '\n':
                    content.remove(startMenuLabel);
                    content.remove(selectLabel);
                    content.repaint();
                    if (startSelect == 1) {
                        GameStart();
                        player = new Player(450, 540, 4, 72, 48,
                                new ImageIcon("./Data/player/.gif/player_font2.gif").getImage(),
                                content, GameFrame);
                        new Checker(player, content, "CHECKER");
                        new EnemyGenerater(content, BossHP,Music);
                    } else if (startSelect == 2) {
                        GameStart();
                        new Loader(content, GameFrame,Music);
                        new EnemyGenerater(content, BossHP,Music);
                    } else {
                        System.exit(1);
                    }
                    break;
                default:
                    break;
            }
            switch (startSelect) {
                case 1:
                    selectLabel.setLocation(60, 446);
                    break;

                case 2:
                    selectLabel.setLocation(60, 497);
                    break;

                case 3:
                    selectLabel.setLocation(60, 547);
                    break;
            }
        } else if (state == 1) {
            if (e.getKeyChar() == 27 && canPause) {
                pause = true;
                state = 4;
            }
        } else if (state == 2 || state == 3) {
            switch (e.getKeyChar()) {
                case 'w':
                case 'W':
                    if (selectLabel.getLocation().x == 207 && selectLabel.getLocation().y == 399) {
                        selectLabel.setLocation(207, 318);
                        content.repaint();
                    } else {
                        selectLabel.setLocation(207, 399);
                    }
                    break;
                case 's':
                case 'S':
                    if (selectLabel.getLocation().x == 207 && selectLabel.getLocation().y == 399) {
                        selectLabel.setLocation(207, 318);
                    } else {
                        selectLabel.setLocation(207, 399);
                    }
                    break;
                case '\n':
                    Music.StopPlay();
                    Music.setMusic("./Data/Sound/Start_Up.wav");
                    Music.PlayMusic();
                    if (state == 2) {
                        content.remove(gameOverLabel);
                    } else {
                        content.remove(gameWinLabel);
                    }
                    content.remove(selectLabel);
                    content.repaint();
                    if (selectLabel.getLocation().x == 207 && selectLabel.getLocation().y == 318) {
                        startMenuLabel.setBounds(0, 0, 700, 613);
                        selectLabel.setBounds(60, 446, 23, 45);
                        content.add(selectLabel);
                        content.add(startMenuLabel);

                        GameBackgroundlabel_1 = new JLabel(new ImageIcon("./Data/background2.png"));
                        GameBackgroundlabel_2 = new JLabel(new ImageIcon("./Data/background2.png"));
                        GameBackgroundlabel_3 = new JLabel(new ImageIcon("./Data/count_background3.png"));

                        GameBackgroundlabel_3.setBounds(0, 0, 300, 650);
                        GameBackgroundlabel_2.setBounds(290, -584, 400, 1234);
                        GameBackgroundlabel_1.setBounds(290, GameBackgroundlabel_2.getLocation().y - 1234, 400, 1234);
                        state = 0;
                        startSelect = 1;
                    } else if (selectLabel.getLocation().x == 207 && selectLabel.getLocation().y == 399) {
                        System.exit(1);
                    }
                    break;
                default:
                    break;
            }
        } else if (state == 4) {
            switch (e.getKeyChar()) {
                case 'w':
                case 'W':
                    if (pauseSelect == 1) {
                        pauseSelect = 3;
                    } else {
                        pauseSelect--;
                    }
                    break;
                case 's':
                case 'S':
                    if (pauseSelect == 3) {
                        pauseSelect = 1;
                    } else {
                        pauseSelect++;
                    }
                    break;
                case '\n':
                    if (pauseSelect == 1) {
                        state = 1;
                        pause = false;
                        firstPause = false;
                        content.remove(gamePauseLabel);
                        content.remove(selectLabel);
                        content.repaint();
                    } else if (pauseSelect == 2) {
                        new Saver(Checker.getEnemyPlaneArray(), Checker.getBulletArray(), Checker.getBounsArray(),
                                Checker.getPlayer(), Checker.getHasBoss(), Checker.getBoss(),
                                EnemyGenerater.getStartTime(), EnemyGenerater.getPauseTime());
                    } else if (pauseSelect == 3) {
                        System.exit(1);
                    }
                    break;
                case 27:
                    state = 1;
                    pause = false;
                    firstPause = false;
                    content.remove(gamePauseLabel);
                    content.remove(selectLabel);
                    content.repaint();
                    break;
                default:
                    break;
            }
            switch (pauseSelect) {
                case 1:
                    selectLabel.setLocation(19, 183);
                    break;
                case 2:
                    selectLabel.setLocation(19, 219);
                    break;
                case 3:
                    selectLabel.setLocation(19, 253);
                    break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
