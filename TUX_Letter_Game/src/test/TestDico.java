/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Aurel
 */
import game.Dico;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;
public class TestDico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            System.out.println("Chargement du dico : ");
           Dico dictionnaire = new Dico("");
        try {
            dictionnaire.lireDictionnaireDOM("src/game/", "dictionnaire.xml");
        } catch (SAXException ex) {
            Logger.getLogger(TestDico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestDico.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}