
package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author mahmoud
 */
public class EditeurDico {
    // dictionnaire contenant les mot
    private Document dom;
    
    
    public void lireDOM(String fichier)
    {
       dom =  this.fromXML(fichier);
       System.out.println(dom.getNodeName());
    }
    
    public void ecrireDOM(String fichier)
    {
        this.toXML(fichier);
    }
    
    public void ajouterMot(String mot, int niveau)
    {
        // Creation d'un element <mot>
        Element motElt = this.dom.createElement("mot");
        // ajout du texte
        Text textMot = this.dom.createTextNode(mot);
        motElt.appendChild(textMot);
        // ajout attribut 'niveau'
        motElt.setAttribute("niveau", String.valueOf(niveau));
        // ajout de <mot> dans <dictionnaire>
        Element racine = (Element)this.dom.getElementsByTagName("dictionnaire").item(0);
        racine.appendChild(motElt);   
    }
    
     public void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(dom, nomFichier);
        } catch (Exception ex) {
                System.out.println(ex.getMessage());
        }
    }
     
     // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
                System.out.println(ex.getMessage());
        }
        return null;
    }
    
}