/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;;
import videogame.Game;

/**
 *
 * @author Hack'n Matata
 */
public class File {
    
    /**
     * Method to load the file
     * @param game the game
     */
    public static void loadFile(Game game){
        BufferedReader br = null;
        FileReader fr = null;
        String line;
        try{
            fr = new FileReader("save.txt");
            br = new BufferedReader(fr);
            
        }catch(Exception ioe){
            
            System.out.println("No hay nada grabado " + ioe.toString());
        }
    }
    
    /**
     * Method to save the file
     * @param game the game
     */
    public static void saveFile(Game game){
        try{
            PrintWriter printWriter = new PrintWriter(new FileWriter("save.txt"));
            
            printWriter.close();
        }catch(IOException ioe){
            System.out.println("Se lleno el disco duro, no se guardo el juego" + ioe.toString());
        }
    }
    
}