/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.advanced.EnvNode;

/**
 *
 * @author mahmoud
 */
public class Letter extends EnvNode{
    private char letter;
    public Letter(char l, double x, double y)
    {
        letter = l;
        this.setScale(4.0);
        setX(x);
        setY(getScale());
        setZ(y);
        this.setTexture("models/letter/"+letter+".png");
        this.setModel("models/letter/cube.obj");
    }
    
     public Letter( double x, double y)
    {
        
        this.setScale(4.0);
        setX(x);
        setY(getScale());
        setZ(y);
        this.setTexture("models/letter/cube.png");
        this.setModel("models/letter/cube.obj");
    }
     
     public String toString(){
         return Character.toString(this.letter);
     }
    
}