/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

/**
 *
 * @author MACBOOK
 */
public class Cualquiera {
    private int x;
    private int y;
    private float lado;
    private float alt;

    public Cualquiera(int x, int y, float lado, float alt) {
        this.x = x;
        this.y = y;
        this.lado = lado;
        this.alt = alt;
    }

    @Override
    public String toString() {
        return "Cualquiera{" + "x=" + x + ", y=" + y + ", lado=" + lado + ", alt=" + alt + '}';
    }

    public Cualquiera() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getLado() {
        return lado;
    }

    public float getAlt() {
        return alt;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLado(float lado) {
        this.lado = lado;
    }

    public void setAlt(float alt) {
        this.alt = alt;
    }
    
    
    
}
