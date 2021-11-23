/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anojo.echecs.pieces;

import com.anojo.echecs.Piece;

/**
 *
 * @author Benja
 */
public class Pion extends Piece {
    
    public Pion(int x, int y){
        super(x,y);
    }
    
    @Override
    public boolean deplacer(int x, int y){
        return true;
    }
    
}
