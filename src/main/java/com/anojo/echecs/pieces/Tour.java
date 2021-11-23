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
public class Tour extends Piece {
    
    public Tour(int x, int y){
        super(x,y);
    }
    
    @Override
    public boolean deplacer(int x, int y){
        return true;
    }
    
}
