package Packages.Functions;

import Packages.RolePackage.*;
import java.io.*;
import java.util.*;

public class Saver {

    private static long BossnenrageTime;
    private ArrayList<EnemyPlane> AllEnemyPlane;
    private ArrayList<Bullet> AllBullet;
    private ArrayList<Bouns> AllBouns;
    private Player player;
    private boolean HasBoss;
    private Boss Boss;
    private boolean existNumber = false;
    private long startTime;
    private long pauseTime;
    private static long ageOfBoss=0;

    public Saver(ArrayList<EnemyPlane> AllEnemyPlane, ArrayList<Bullet> AllBullet, ArrayList<Bouns> AllBouns,
            Player player, boolean HasBoss, Boss Boss, long startTime, long pauseTime) {
        this.AllEnemyPlane = AllEnemyPlane;
        this.AllBullet = AllBullet;
        this.AllBouns = AllBouns;
        this.Boss = Boss;
        this.HasBoss = HasBoss;
        this.player = player;
        this.HasBoss = HasBoss;
        this.Boss = Boss;
        this.startTime = startTime;
        this.pauseTime = pauseTime;
        SaveArchive();
    }

    private void SaveArchive() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./Archive/SavedData.dat"));
            writer.write("Player\n");
            writer.write(player.getX() + " " + player.getY() + " " + player.getHP() + " " + player.getScore() + " "
                    + player.getShooting() + "\n\n");

            writer.write("Enemys\n");
            for (int i = 0; i <= AllEnemyPlane.size() - 1; i++) {
                if (AllEnemyPlane.get(i).isAlive()) {
                    existNumber = true;
                    break;
                }
            }
            if (!(AllEnemyPlane.isEmpty() || !existNumber)) {
                for (int i = 0; i <= AllEnemyPlane.size() - 1; i++) {
                    if (AllEnemyPlane.get(i).isAlive()) {
                        writer.write(AllEnemyPlane.get(i).getX() + " " + AllEnemyPlane.get(i).getY() + '\n');
                    }
                }
            }
            writer.write('\n');
            existNumber = false;

            writer.write("Bullets\n");
            for (int i = 0; i <= AllBullet.size() - 1; i++) {
                if (AllBullet.get(i).isAlive()) {
                    existNumber = true;
                    break;
                }
                
            }
            if (!(AllBullet.isEmpty() || !existNumber)) {
                for (int i = 0; i <= AllBullet.size() - 1; i++) {
                    if (AllBullet.get(i).isAlive()) {
                        Boolean belong = AllBullet.get(i).getBelong();
                        writer.write(AllBullet.get(i).getX() + " " + AllBullet.get(i).getY() + " " + belong.toString()
                                + '\n');
                    }
                }
            }
            writer.write('\n');

            writer.write("Bouns\n");
            for (int i = 0; i <= AllBouns.size() - 1; i++) {
                if (AllBouns.get(i).isAlive()) {
                    existNumber = true;
                    break;
                }
            }
            if (!(AllBouns.isEmpty() || !existNumber)) {
                for (int i = 0; i <= AllBouns.size() - 1; i++) {
                    if (AllBouns.get(i).isAlive()) {
                        writer.write(AllBouns.get(i).getX() + " " + AllBouns.get(i).getY() + '\n');
                    }
                }
            }
            writer.write('\n');

            writer.write("Time\n");
            Long remain = EnemyGenerater.getPassthroughTime()+(pauseTime - startTime);
            writer.write(remain.toString()+"\n\n");
            

            writer.write("Boss");
            if (HasBoss) {
                writer.write("\n"+Boss.getX() + " " + Boss.getY() + " " + Boss.getHP()+" "+(new Date().getTime()-BossnenrageTime+ageOfBoss));
            }

            writer.close();
            System.exit(1);
        } catch (Exception e) {
        }
    }


    public static void setBossnenrageTime(long boss){
        BossnenrageTime= boss;
    }

    public static long getBossnenrageTime(){
        return BossnenrageTime;
    }

    public static long getAgesOfBoss(){
        return ageOfBoss;
    }

    public static void setAgesOfBoss(long a){
        BossnenrageTime=new Date().getTime();
        ageOfBoss=a;
    }
}
