/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import base.Item;
import java.awt.Graphics;

/**
 *
 * @author Hack'n matata
 */
public class Mob extends Item{
    
    private Game game;
    
    public Mob(int x, int y, int width, int height, Game game, int vel){
        super(x, y, width, height);
        this.game = game;
        
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
       
    }
}
