/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author mahmoud
 */
public class Room {

    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private static String textureTop;
    private static String textureSouth;

    public Room() {
        depth = 100;
        height = 60;
        width = 100;
        textureBottom = "textures/skybox/cave/bottom.png";
        textureNorth = "textures/skybox/cave/north.png";
        textureEast = "textures/skybox/cave/east.png";
        textureWest = "textures/skybox/cave/west.png";
        
        try {
           
        // On charge le fichier plateau.xml
        File xmlFile = new File("src/xml/plateau.xml");   
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document docPlateau = dBuilder.parse(xmlFile);
        docPlateau.getDocumentElement().normalize();
      
        // On recupere les nodes 
        Element heightNode = (Element)docPlateau.getElementsByTagName("height").item(0);
        Element WidthNode = (Element)docPlateau.getElementsByTagName("width").item(0);
        Element depthtNode = (Element)docPlateau.getElementsByTagName("depth").item(0);

        Element textureBottomtNode = (Element)docPlateau.getElementsByTagName("textureBottom").item(0);
        Element textureEastNode = (Element)docPlateau.getElementsByTagName("textureEast").item(0);
        Element textureWestNode = (Element)docPlateau.getElementsByTagName("textureWest").item(0);
        Element textureNorthNode = (Element)docPlateau.getElementsByTagName("textureNorth").item(0);

        // on mets les valeurs aux attributss
        int depthValue = Integer.parseInt(depthtNode.getTextContent());
        int widthValue = Integer.parseInt(WidthNode.getTextContent());
        int heightValue = Integer.parseInt(heightNode.getTextContent());
        
        String bottomValue = textureBottomtNode.getTextContent();
        String northValue = textureNorthNode.getTextContent();
        String eastValue = textureEastNode.getTextContent();
        String westValue = textureWestNode.getTextContent();
        
        this.depth = depthValue;
        this.height = heightValue;
        this.width = widthValue;
        this.textureBottom = bottomValue;
        this.textureEast = eastValue;
        this.textureNorth = northValue;
        this.textureWest = westValue;
        
        
        System.out.println("height = " +heightNode.getTextContent());
        System.out.println("width = " +WidthNode.getTextContent());
        System.out.println("depth = " +depthtNode.getTextContent());
        System.out.println("Bottom = " +textureBottomtNode.getTextContent());
        System.out.println("north = " +textureNorthNode.getTextContent());
        System.out.println("east = " +textureEastNode.getTextContent());
        System.out.println("west = " +textureWestNode.getTextContent());

        

            
            
        } catch (Exception ex) {
            System.out.println("Erreur parseur plateau XML");
        } 
            
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public static String getTextureTop() {
        return textureTop;
    }

    public static void setTextureTop(String textureTop) {
        Room.textureTop = textureTop;
    }

    public static String getTextureSouth() {
        return textureSouth;
    }

    public static void setTextureSouth(String textureSouth) {
        Room.textureSouth = textureSouth;
    }

}