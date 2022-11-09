package Packages.RolePackage;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Bouns extends Role{
    
    JPanel content;

    public Bouns(int x,int y,double speed,int width,int height,Image img,JPanel content)
    {
        super( x, y,speed, width, height,img);
        this.content=content;
        start();
    }


    @Override
    public void run() {
        content.add(label);
        java.util.Timer bounsmove=new java.util.Timer();
        TimerTask bouns =new TimerTask() {
            @Override
            public void run() {
                if(!pause){
                    label.setLocation(label.getLocation().x,(int)(label.getLocation().y+speed));
                }
            }
        };
        bounsmove.schedule(bouns,0,25);
        while(surivie&&label.getLocation().y<=700)
        {
            try {
                sleep(10);
            } catch (Exception e) {
            }
        }
        bounsmove.cancel();
    }
    
    @Override
    public void Die() {
        surivie=false;
        content.remove(label);
        content.repaint();
    }
}
