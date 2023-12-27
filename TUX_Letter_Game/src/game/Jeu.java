/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
import game.XMLUtil.DocumentTransform;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lwjgl.input.Keyboard;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import org.w3c.dom.Text;

public abstract class Jeu {

    // Une énumération pour définir les choix de l'utilisateur
    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    public  Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    public ArrayList<Letter> lettres;
    public Dico dictionnaire;

    private final Room menuRoom;
    EnvText textNomJoueur;
    EnvText textMenuQuestion;

    EnvText textMenuJeu1;
    EnvText textMenuJeu2;
    EnvText textMenuJeu3;
    EnvText textMenuJeu4;

    EnvText textMenuPrincipal1;
    EnvText textMenuPrincipal2;
    EnvText textMenuPrincipal3;
    EnvText textMenuPrincipal4;

    EnvText textDatePartie;
    EnvText textChoixNiveau;
    EnvText textSecondesRestantes;
    EnvText textMotAtrouver;

    // texte afficher en jeux
    EnvText timeInGame;
    EnvText nextLetterToFind;
    EnvText savePartieInFile;

    public Jeu() {
        this.lettres = new ArrayList<Letter>();
        env = new Env();

        // Instancie une Room
        room = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil("src/xml/default.xml");

        // Dictionnaire
        System.out.println("Chargement du dico : ");
        dictionnaire = new Dico("src/xml/dico.xml");
        try {
            //ou dictionnaire.lireDictionnaireDOM("src/XML/", "dictionnaire.xml");
            dictionnaire.lireDictionnaire();
        } catch (Exception ex) 
        {
            System.out.println("Erreur chargement dico");
        }

        // Textes affichés à l'écran
        textMenuQuestion = new EnvText(env, "Voulez vous ?", 200, 300);
        textMenuJeu1 = new EnvText(env, "1. Commencer une nouvelle partie ?", 250, 280);
        textMenuJeu2 = new EnvText(env, "2. Charger une partie existante ?", 250, 260);
        textMenuJeu3 = new EnvText(env, "3. Sortir de ce jeu ?", 250, 240);
        textMenuJeu4 = new EnvText(env, "4. Quitter le jeu ?", 250, 220);
        textNomJoueur = new EnvText(env, "Choisissez un nom de joueur : ", 200, 300);
        textMenuPrincipal1 = new EnvText(env, "1. Charger un profil de joueur existant ?", 250, 280);
        textMenuPrincipal2 = new EnvText(env, "2. Créer un nouveau joueur ?", 250, 260);
        textMenuPrincipal3 = new EnvText(env, "3. Sortir du jeu ?", 250, 240);
        textMenuPrincipal4 = new EnvText(env, "4. Ajouter un mot dans le dico ? (dans la console)", 250, 220);

        textChoixNiveau = new EnvText(env, "Choisir niveau du mot : \n\t\t\t-Niveau 1\n\t\t\t-Niveau 2\n\t\t\t-Niveau 3\n\t\t\t-Niveau 4\n\t\t\t-Niveau 5", 250, 300);
        
        textDatePartie = new EnvText(env, "Date de votre partie : ( aaaa-mm-jj ) : ", 250, 280);
        textSecondesRestantes = new EnvText(env, "", 250, 220, 100, 0, 255, 0, 0, 0, 0);
        textMotAtrouver = new EnvText(env, "", 250, 240, 100, 0, 255, 0, 0, 0, 0);

        timeInGame = new EnvText(env, "Secondes restantes : ", 150, 480);
        nextLetterToFind = new EnvText(env, "Lettre  : ", 400, 480);
        savePartieInFile = new EnvText(env, "Voulez vous entregistrer votre partie ? (O/N)", 250, 220);
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {
        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }

    /**
     * Teste si une code clavier correspond bien à une lettre
     *
     * @return true si le code est une lettre
     */
    private Boolean isLetter(int codeKey) {
        return codeKey == Keyboard.KEY_A || codeKey == Keyboard.KEY_B || codeKey == Keyboard.KEY_C || codeKey == Keyboard.KEY_D || codeKey == Keyboard.KEY_E
                || codeKey == Keyboard.KEY_F || codeKey == Keyboard.KEY_G || codeKey == Keyboard.KEY_H || codeKey == Keyboard.KEY_I || codeKey == Keyboard.KEY_J
                || codeKey == Keyboard.KEY_K || codeKey == Keyboard.KEY_L || codeKey == Keyboard.KEY_M || codeKey == Keyboard.KEY_N || codeKey == Keyboard.KEY_O
                || codeKey == Keyboard.KEY_P || codeKey == Keyboard.KEY_Q || codeKey == Keyboard.KEY_R || codeKey == Keyboard.KEY_S || codeKey == Keyboard.KEY_T
                || codeKey == Keyboard.KEY_U || codeKey == Keyboard.KEY_V || codeKey == Keyboard.KEY_W || codeKey == Keyboard.KEY_X || codeKey == Keyboard.KEY_Y
                || codeKey == Keyboard.KEY_Z;
    }

    /**
     * Récupère une lettre à partir d'un code clavier
     *
     * @return une lettre au format String
     */
    private String getLetter(int letterKeyCode) {
        Boolean shift = false;
        if (this.env.getKeyDown(Keyboard.KEY_LSHIFT) || this.env.getKeyDown(Keyboard.KEY_RSHIFT)) {
            shift = true;
        }
        env.advanceOneFrame();
        String letterStr = "";
        if ((letterKeyCode == Keyboard.KEY_SUBTRACT || letterKeyCode == Keyboard.KEY_MINUS)) {
            letterStr = "-";
        } else {
            letterStr = Keyboard.getKeyName(letterKeyCode);
        }
        if (shift) {
            return letterStr.toUpperCase();
        }
        return letterStr.toLowerCase();
    }

    
    private String getNomJoueur() {
        textNomJoueur.modifyTextAndDisplay("Choisissez un nom de joueur : ");

        int touche = 0;
        String nomJoueur = "";
        while (!(nomJoueur.length() > 0 && touche == Keyboard.KEY_RETURN)) {
            touche = 0;
            while (!isLetter(touche) && touche != Keyboard.KEY_MINUS && touche != Keyboard.KEY_SUBTRACT && touche != Keyboard.KEY_RETURN) {
                touche = env.getKey();
                env.advanceOneFrame();
            }
            if (touche != Keyboard.KEY_RETURN) {
                if ((touche == Keyboard.KEY_SUBTRACT || touche == Keyboard.KEY_MINUS) && nomJoueur.length() > 0) {
                    nomJoueur += "-";
                } else {
                    nomJoueur += getLetter(touche);
                }
                textNomJoueur.modifyTextAndDisplay("Choisissez un nom de joueur : " + nomJoueur);
            }
        }
        textNomJoueur.destroy();
        return nomJoueur;
    }

    
    private MENU_VAL menuPrincipal() {
        System.out.println("menu principal");
        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        String nomJoueur;

        // restaure la room du menu
        env.setRoom(menuRoom);

        // affiche le menu principal
        textMenuQuestion.display();
        textMenuPrincipal1.display();
        textMenuPrincipal2.display();
        textMenuPrincipal3.display();
        textMenuPrincipal4.display();

        // vérifie qu'une touche 1, 2 , 3 ou 4 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        // efface le menu
        textMenuQuestion.clean();
        textMenuPrincipal1.clean();
        textMenuPrincipal2.clean();
        textMenuPrincipal3.clean();
        textMenuPrincipal4.clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                nomJoueur = getNomJoueur();
                System.out.println("Nom du joueur choisi : " + nomJoueur);

                // charge le profil de ce joueur si possible
                File f = new File("src/Joueurs/" + nomJoueur + ".xml");
                if (f.exists()) {
                    this.profil = new Profil("src/Joueurs/" + nomJoueur + ".xml");
                    // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                    System.out.println("Nom du joueur VALIDE : " + nomJoueur + "PROFIL = " + profil);
                    choix = menuJeu();
                } else {
                    // sinon continue (et boucle dans ce menu)
                    System.out.println("Nom du joueur NON VALIDE : " + nomJoueur);
                    choix = MENU_VAL.MENU_CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:
                // demande le nom du nouveau joueur
                nomJoueur = getNomJoueur();
                
                // On creer un fichier nomJoueur.xml pour le nouveau joueur   
                File profileFile = new File("src/Joueurs/" + nomJoueur + ".xml");
                if (!profileFile.exists()) {
                    this.newJoueurProfil(nomJoueur);
                    profil = new Profil("src/Joueurs/" + nomJoueur + ".xml");
                }              
                // lance le menu de jeu et récupère le choix à la sortie de ce menu de jeu
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                // le choix est de sortir du jeu (quitter l'application)
                choix = MENU_VAL.MENU_SORTIE;
                break;
            // -------------------------------------
            // Touche 4 : Permet au joueur d'ajouter un mot et son niveau
            // -------------------------------------
            case Keyboard.KEY_4:
                // le choix est de sortir du jeu (quitter l'application)
                System.out.println("*****AJOUT D'UNE LETTRE******");
                Scanner sc = new Scanner(System.in);
                System.out.println("Saisissez votre mot: ");
                String mot = sc.nextLine();
                System.out.println("niveau du mot : ");
                int niveau = sc.nextInt();
                EditeurDico editDico = new EditeurDico();
                editDico.lireDOM("src/xml/dico.xml");
                editDico.ajouterMot(mot, niveau);
                editDico.ecrireDOM("src/xml/dico.xml");
                System.out.print("Ajout de  : " + mot + " ( niveau = " + niveau + ") dans le dictionnaire\n\n");
                break;
        }
        return choix;
    }

    /**
     * Menu de jeu
     *
     * @return le choix du joueur une fois la partie terminée
     */
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);

            // affiche le menu de jeu
            textMenuQuestion.display();
            textMenuJeu1.display();
            textMenuJeu2.display();
            textMenuJeu3.display();
            textMenuJeu4.display();

            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // efface le menu
            textMenuQuestion.clean();
            textMenuJeu1.clean();
            textMenuJeu2.clean();
            textMenuJeu3.clean();
            textMenuJeu4.clean();

            // restaure la room du jeu
            env.setRoom(room);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico

                    int toucheNiveau = this.getNiveauChoix();
                    String mot = dictionnaire.getMotDepuisListeNiveaux(toucheNiveau);

                    System.out.println("Mot a chercher = " + mot);

                    // .......... dico ...........
                    // crée une nouvelle partie
                    partie = new Partie(java.time.LocalDate.now().toString(), mot, toucheNiveau);
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil .........

                    this.profil.ajouterPartie(partie);
                    this.profil.sauvegarder("src/Joueurs/" + this.profil.getNom() + ".xml");

                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante

                    // demande a l'utilisateur une date au format YYYY/MM/DD
                    String date = getDateUtilisateur();

                    // tenter de trouver une partie à cette date
                    Partie partieJouable = null;
                    boolean partieJouableFound = false;
                    int i = 0;

                    while (i < this.profil.getParties().size() && !partieJouableFound) {
                        // Partie au rang i
                        Partie p = this.profil.getParties().get(i);
                        try {
                            // format de la date 
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                            Date datePartie = dateformat.parse(p.getDate());
                            Date dateSouhaitee = dateformat.parse(date);
                            // partie et date demandee MEME jour
                            if (datePartie.compareTo(dateSouhaitee) == 0) {
                                System.out.println("\t\tPartie = " + p);
                                partieJouable = p;
                                partieJouableFound = true;
                            } // jour different
                            else {
                                System.out.println("DATE DIFF JOUR = " + datePartie.getTime() + " --- " + dateSouhaitee.getTime());

                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        i++;

                    }

                    // Si partie trouvée, recupère le mot de la partie existante, sinon ???
                    if (partieJouableFound) {
                        System.out.println("PARTIE Jeu = " + partieJouable);
                        // joue
                        joue(partieJouable);
                        // enregistre la partie dans le profil --> enregistre le profil
                        System.out.print("Enregistrement ");
                        this.profil.sauvegarder("enregistrer.xml");
                        playTheGame = MENU_VAL.MENU_JOUE;
                    } else {
                        System.out.println("PARTIE INCONNUE A LA DATE : " + date);
                    }
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    public String getDateUtilisateur() {
        String date = "";
        // demander de fournir une date    (format jj/mm/aaaa)
        String regex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        //Creating a pattern object
        Pattern pattern = Pattern.compile(regex);
        //Matching the compiled pattern in the String
        Matcher matcher;

        this.textDatePartie.modifyTextAndDisplay("Date de votre partie : ( aaaa-mm-jj ) : ");

        // recupere la date que l'utilisateur veut                    
        do {
            date = this.textDatePartie.lire(true);
            matcher = pattern.matcher(date);
        } while (!matcher.matches());

        this.textMenuPrincipal1.clean();
        return date;
    }

    // permet au joueur de saisir un niveau entre 1 et 5 
    private int getNiveauChoix() {
        int toucheNiveau = 0;
        this.textChoixNiveau.display();
        toucheNiveau = 0;
        while (toucheNiveau < 1 || toucheNiveau > 5) {
            toucheNiveau = env.getKey();
            toucheNiveau -= 1;
            env.advanceOneFrame();
        }
        this.textChoixNiveau.clean();
        return toucheNiveau;
    }

    public void joue(Partie partie) {
        System.out.println("joue partie : " + partie);

        // affiche 5 secondes la lettre a l'ecran 
        Chronometre c = new Chronometre(5000);
        this.textMotAtrouver.modifyTextAndDisplay("Mot a trouver : " + partie.getMot());
        this.textSecondesRestantes.modifyTextAndDisplay((5 - c.timeSpent()) + " secondes restantes");
        while (c.remainsTime()) {
            env.advanceOneFrame();
            this.textSecondesRestantes.modifyTextAndDisplay((5 - c.timeSpent()) + " secondes restantes");
        }

        textSecondesRestantes.clean();
        textMotAtrouver.clean();
        // Instancie un Tux
        this.tux = new Tux(env, room);
        env.addObject(tux);

        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);

        // On affiche les lettres dans l'environnement     
        for (Letter l : lettres) {
            env.addObject(l);
        }

        tux.deplace();
        // Boucle de jeu
        Boolean finished;
        finished = false;
        // Boucle s'arrete si : l'utilisateur presse ECHAP ou le temps du chrono est depasse ou le mot est trouve 
        while (!finished && partie.isTempsDispo() && partie.isMotTrouve()) {

            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == 1) {
                finished = true;
            }
            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.deplace();
            // Ici, on applique les regles
            appliqueRegles(partie);

            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }

        // On enleve les lettres dans l'environnement (elles disparaissent de l'ecran)    
        for (Letter l : lettres) {
            env.removeObject(l);
        }
        // on vide la tableau de lettres au cas ou le joueur veut rejouer un partie
        lettres.clear();

        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
        System.out.println("Partie = " + partie);
    }

// Permet de verifier si la lettre 'letter' entre en collision avec le personnage 'Tux'
    public boolean collision(Letter letter) {
        if (distance(letter) > this.tux.getScale()) {
            return false;
        } else {
            return true;
        }
    }

    // calcul la distance entre 'Tux' et 'Letter'
    public double distance(Letter letter) {
        double x_tux = this.tux.getZ();
        double y_tux = this.tux.getX();

        double x_letter = letter.getZ();
        double y_letter = letter.getX();

        double distance_x = Math.pow(x_letter - x_tux, 2);
        double distance_y = Math.pow(y_letter - y_tux, 2);
        double distance = Math.sqrt(distance_x + distance_y);

        return distance;
    }

    public void newJoueurProfil(String nomProfile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // root element
            Element rootElement = doc.createElement("profil");
            doc.appendChild(rootElement);

            // nom element
            Element nomElement = doc.createElement("nom");
            rootElement.appendChild(nomElement);
            Text nomText = doc.createTextNode(nomProfile);
            nomElement.appendChild(nomText);

            // avatar element
            Element avatarElement = doc.createElement("avatar");
            rootElement.appendChild(avatarElement);
            Text avatarText = doc.createTextNode(nomProfile + ".jpg");
            avatarElement.appendChild(avatarText);

            // anniversaire element
            Element annivElement = doc.createElement("anniversaire");
            rootElement.appendChild(annivElement);
            Text annivText = doc.createTextNode("");
            annivElement.appendChild(annivText);

            // parties element
            Element partiesElement = doc.createElement("parties");
            rootElement.appendChild(partiesElement);

            // ecriture du dom vers xml
            DocumentTransform.writeDoc(doc, "src/Joueurs/" + nomProfile + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Methode utilisee au lancement du jeu (1 seule fois par partie)
    protected abstract void demarrePartie(Partie partie);

    // Methode utilisee pendant toute la duree du jeu
    protected abstract void appliqueRegles(Partie partie);

    // Methode utilisee a la fin du jeu
    protected abstract void terminePartie(Partie partie);
}