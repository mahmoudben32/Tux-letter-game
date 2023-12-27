package game;

import java.util.Random;
import env3d.Env;
/**
 *
 * @author mahmoud
 */
public class JeuDevineLeMotOrdre extends Jeu {

    // nombre de lettres restantes a trouve dans le mot
    private int nbLettresRestantes;
    // chrono de la partie
    private Chronometre chrono;
    public JeuDevineLeMotOrdre() {
        super();
    }

    // Lancement du chronometre + instanciation des lettres de l'environnement
    @Override
    protected void demarrePartie(Partie partie) {
        System.out.println("DEMARRE PARTIE");
        
        // temps limite pour que le joueur trouve le mot 
        this.chrono = new Chronometre(30000);

        
        char[] mot_caracteres = partie.getMot().toCharArray();
        Random r = new Random();
        int z;
        int y;
        for (char c : mot_caracteres) {
            z = r.nextInt(101);
            y = r.nextInt(101);
            this.lettres.add(new Letter(c, z, y));
        }
        this.nbLettresRestantes = super.lettres.size();
    }

    @Override
    protected void appliqueRegles(Partie partie) {
         super.timeInGame.modifyTextAndDisplay("Secondes restantes : " + (this.chrono.getSeconds() -  this.chrono.timeSpent()) );
        // Le temps est ecoule
        if (chrono.remainsTime() == false) {
            this.chrono.stop();
            partie.setTempsDispo(false); 

        } // il reste du temps
        else {
            // Il reste des lettres a trouve
            if (this.nbLettresRestantes != 0) {
               super.nextLetterToFind.modifyTextAndDisplay("Lettre suivante : "  + super.lettres.get(super.lettres.size() - this.nbLettresRestantes) );
                boolean trouveLettre = tuxTrouveLettre();
                Letter nextCarac = super.lettres.get(super.lettres.size() - this.nbLettresRestantes);
      
                if (trouveLettre) {
                    this.nbLettresRestantes--;
                }
            } // Il n'y a plus de lettre a trouver
            else {
                partie.setMotTrouve(false);
            }

        }
    }

    @Override
    protected void terminePartie(Partie partie) {
        System.out.println("TERMINE PARTIE");
       
        // On recupere les valeurs de la classe partie
        partie.setTrouve(nbLettresRestantes);
        partie.setTemps(chrono.timeSpent());
        
        // On efface les champs de texte de l'ecran
        this.timeInGame.modifyTextAndDisplay("");
        this.nextLetterToFind.modifyTextAndDisplay("");
    }

    // Renvoie vrai si le personnage Tux est en contact avec la lettre qu'il doit trouver et l'efface de l'environement.
    private boolean tuxTrouveLettre() {
        Letter courante = super.lettres.get(super.lettres.size() - this.nbLettresRestantes);
        if (super.collision(courante)) {
            super.env.removeObject(courante);
            return true;
        } else {
            return false;
        }

    }

    // retourne le nombre de lettres restantes a trouver
    private int getNbLettresRestantes() {
        return this.nbLettresRestantes;
    }

    // retourne le temps en secondes que dure la partie
    private int getTemps() {
        return chrono.getSeconds();
    }
    
    
}