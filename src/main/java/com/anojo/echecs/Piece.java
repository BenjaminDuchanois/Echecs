/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anojo.echecs;

/**
 *
 * @author Benja
 */
public abstract class Piece {
    String name;
    int x, y;
    
    public Piece(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public abstract boolean deplacer(int x, int y);

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
