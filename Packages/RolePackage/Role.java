package Packages.RolePackage;

import javax.swing.*;
import java.awt.*;

public abstract class Role extends Thread {
    final int RATE_OF_REFASH = 25;
    protected double speed = 0.4;
    protected int width;
    protected int height;
    protected JLabel label;
    protected int x = 0;
    protected int y = 0;
    protected Image img;
    protected boolean surivie = true;
    protected boolean pause = false;
    protected int HP;

    public Role(int x, int y, double speed, int width, int height, Image img) {
        label = new JLabel(new ImageIcon(img));
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.img = img;
        this.width = width;
        this.height = height;
        label.setIcon(new ImageIcon(img));
        label.setBounds(x, y, width, height);
    }

    public Role(double speed, int width, int height, Image img) {
        label = new JLabel(new ImageIcon(img));
        this.speed = speed;
        this.img = img;
        this.width = width;
        this.height = height;
        label.setIcon(new ImageIcon(img));
    }

    public Role(int width, int height, Image img) {
        label = new JLabel(new ImageIcon(img));
        this.img = img;
        this.width = width;
        this.height = height;
        label.setIcon(new ImageIcon(img));
    }

    public void setImg(Image img) {
        this.img = img;
        label.setIcon(new ImageIcon(img));
    }

    public void setLocate(int x, int y) {
        this.x = x;
        this.y = y;
        label.setLocation(x, y);
    }

    public void setSize(int width, int height) {
        this.height = height;
        this.width = width;
        label.setSize(width, height);
    }


    public void setHP(int HP){
        this.HP=HP;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public int getX() {
        return label.getLocation().x;
    }

    public int getY() {
        return label.getLocation().y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JLabel getLabel() {
        return label;
    }

    public boolean getSurvive() {
        return surivie;
    }

    public int getHP(){
        return HP;
    }

    public abstract void Die();
}
