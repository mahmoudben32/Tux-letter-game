/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;
import env3d.Env;
import env3d.advanced.EnvNode; 
import org.lwjgl.input.Keyboard;
/**
 *
 * @author mahmoud
 */
public class Tux extends EnvNode {
    private Env env;
    private Room room;
    
    
    public Tux(Env env, Room room)
    {
        this.env = env;
        this.room = room;
        this.setScale(4.0);
        setX(50);
        setY(getScale());
        setZ(50);
        this.setTexture("models/tux/tux_happy.png");
        this.setModel("models/tux/tux.obj");
    }
    
    public void deplace()
    {
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
       // Haut
       System.out.println("move up : [X=" + this.getX() +"] [Y="+ this.getY()+"] [Z="+ this.getZ()+"]" );  
       if(!this.testeRoomColision(this.getX(), this.getZ()-1))
       {
       this.setRotateY(180);
       this.setZ(this.getZ() - 1.0);
       }
       }
     if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
       // Gauche
        if(!this.testeRoomColision(this.getX()-1, this.getZ()))
       {
       System.out.println("move down : [X=" + this.getX() +"] [Y="+ this.getY()+"] [Z="+ this.getZ()+"]" );     
       this.setRotateY(-90);
       this.setX(this.getX() - 1.0);
       }
       }
      if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'gauche' ou Q
       // droite
        if(!this.testeRoomColision(this.getX(), this.getZ()+1))
       {
       System.out.println("move left : [X=" + this.getX() +"] [Y="+ this.getY()+"] [Z="+ this.getZ()+"]" );     
       this.setRotateY(-180);
       this.setZ(this.getZ() + 1.0);
       }
       }
       if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'gauche' ou Q
       // bas
        if(!this.testeRoomColision(this.getX()+1, this.getZ()))
       {
       System.out.println("move right : [X=" + this.getX() +"] [Y="+ this.getY()+"] [Z="+ this.getZ()+"]" );     
   
      this.setRotateY(90);
       this.setX(this.getX() + 1.0);
       }
       }
    
    }
    
    public boolean testeRoomColision(double x, double y)
    {
        boolean colision = false;
        if(!(x > 0 && x < room.getWidth()))
        {
            colision = true;
        }
        if(!(y > 0 && y < room.getDepth()))
        {
            colision = true;
        }
        
        
        return  colision;
    }
    
}