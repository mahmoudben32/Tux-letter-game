/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author mahmoud
 */
public class Dico extends DefaultHandler {

    private StringBuffer buffer;
    // chemin du dictionnaire XML
    private static String pathToDicoFile = "src/xml/dico.xml";
    private boolean insideMot = false;
    private int motNiveau;

    // tableaux contenant les mots
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    
    private String cheminFichierDico;

    // @cheminFichierDico = chemin du dictionnaire XML qui est dans dico.xml
    public Dico(String cheminFichierDico) {
        super();
        this.cheminFichierDico = cheminFichierDico;
        this.listeNiveau1 = new ArrayList<String>();
        this.listeNiveau2 = new ArrayList<String>();
        this.listeNiveau3 = new ArrayList<String>();
        this.listeNiveau4 = new ArrayList<String>();
        this.listeNiveau5 = new ArrayList<String>();

    }

    //METHODES DEFAULT HANDLER 
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {        
        if(qName.equals("mot"))
       {
            // On recupere la valeur de l'attribut 'niveau'
            String niveauMot = attributes.getValue("niveau");
            this.motNiveau = Integer.parseInt(niveauMot);
            // on indique qu'on est dans l'element <mot>
            insideMot = true;
       }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // on sort de <mot>
        if(qName.equals("mot"))
        {
           insideMot = false;
        }
    }

    // On recupere la valeur de l'element <mot></mot> et on le mets dans son ArrayList correspondant
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(insideMot)
        {
        String donnees = new String(ch, start, length);
        switch (this.motNiveau) {
            case 1:
                this.listeNiveau1.add(donnees);
                break;
            case 2:
                this.listeNiveau2.add(donnees);
                break;
            case 3:
                this.listeNiveau3.add(donnees);
                break;
            case 4:
                this.listeNiveau4.add(donnees);
                break;
            case 5:
                this.listeNiveau5.add(donnees);
                break;

        }
        System.out.println("     valeur = *" + donnees + "*");    
        }
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("START DOCUMENT ");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("END DOCUMENT");
    }

    // Lecture du fichier Dico.pathToDicoFile avec SAX
    public void lireDictionnaire() {
        try {
            XMLReader parser;
            parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(this);
            parser.parse(Dico.pathToDicoFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Méthode qui permet de piocher aleatoirement un mot dans une liste de niveau "niveau"
    public String getMotDepuisListeNiveaux(int niveau) {
        String aretourner = "";
        niveau = this.verifieNiveau(niveau);
        switch (niveau) {
            case 1:
                aretourner += this.getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                aretourner += this.getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                aretourner += this.getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                aretourner += this.getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                aretourner += this.getMotDepuisListe(listeNiveau5);
                break;
        }
        return aretourner;
    }

    // ajoute le mot "mot" dans la liste de niveau "niveau"
    public void ajouteMotADico(int niveau, String mot) {
        switch (this.verifieNiveau(niveau)) {
            case 1:
                this.listeNiveau1.add(mot);
                break;
            case 2:
                this.listeNiveau2.add(mot);
                break;
            case 3:
                this.listeNiveau3.add(mot);
                break;
            case 4:
                this.listeNiveau4.add(mot);
                break;
            case 5:
                this.listeNiveau5.add(mot);
                break;
        }
    }

    public String getCheminFichierDico() {
        return this.cheminFichierDico;
    }

    // Verifie que le niveau passé soit bien dans l'interval des valeurs [1,5]
    private int verifieNiveau(int niveau) {
        int aretourner = niveau;
        if (niveau < 1 || niveau > 5) {
            niveau = 1;
        }
        return aretourner;
    }

    // Extrait un mot aleatoirement dans une liste
    private String getMotDepuisListe(ArrayList<String> list) {
        System.out.println("TIRAGE ALEATOIRE DANS :");
        for(String s : list)
        {
            System.out.println("\t\t" + s);

        }
        int intervalValeur = list.size() - 1;
        Random r = new Random();
        if (list.size() > 0) {
            return list.get(r.nextInt(intervalValeur));
        } else {
            return "default";
        }
    }

    
    public void lireDictionnaireDOM(String path, String filename) throws SAXException, IOException {

        DOMParser parser = new DOMParser();
        parser.parse(path + "" + filename);
        Document racine = parser.getDocument();

        // récupère la liste des éléments nommés tr:pos
        NodeList posList = racine.getElementsByTagName("mot");
        System.out.println("nombre de mot =   " + posList.getLength());

        for (int i = 0; i < posList.getLength(); i++) {
            Element mot = (Element) posList.item(i);
            String mot_text = mot.getTextContent();
            int mot_niveau = Integer.parseInt(mot.getAttribute("niveau"));
            System.out.println(mot_text + "(" + mot_niveau + ")");

            switch (mot_niveau) {
                case 1:
                    this.listeNiveau1.add(mot_text);
                    break;
                case 2:
                    this.listeNiveau2.add(mot_text);
                    break;
                case 3:
                    this.listeNiveau3.add(mot_text);
                    break;
                case 4:
                    this.listeNiveau4.add(mot_text);
                    break;
                case 5:
                    this.listeNiveau5.add(mot_text);
                    break;
            }

        }
    }

}