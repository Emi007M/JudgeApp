/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import matchescreation.Node;

/**
 *
 * @author Emilia
 */
public class BracketChart extends Region {
    
    
    
    public BracketChart(){
        this.setId("bracket-chart");
    }
    
    public void addMatch(Node match, int posX, int posY){
        
        //prepare names
        String akaName = "-";
        String akaClub = "-";
        if(match.getAka() != null && match.getAka().getAthlete() != null){
            akaName = match.getAka().getAthlete().getFullName();
            akaClub = match.getAka().getAthlete().getClub();
        }
        String akaScore = (match.getScoreAka() != null) ? match.getScoreAka().toString() : "x";
        
        String shiroName = "-";
        String shiroClub = "-";
        if(match.getShiro() != null && match.getShiro().getAthlete() != null){
            shiroName = match.getShiro().getAthlete().getFullName();
            shiroClub = match.getShiro().getAthlete().getClub();
        }
        String shiroScore = (match.getScoreShiro() != null) ? match.getScoreShiro().toString() : "x";
        
        
        
        
        //distances
        int distY = 50;
        int distClub = 20;
        int distScore = 200;
        int icoMargin = 10;
        int icoSize = 20;
        int textShift = 15;
        
       //competitor color icons
        ImageView akaIco = new ImageView("matchescreation/view/images/aka.png");
        akaIco.setFitWidth(icoSize);
        akaIco.setFitHeight(icoSize);
        akaIco.relocate(posX, posY);
        ImageView shiroIco = new ImageView("matchescreation/view/images/shiro.png");
        shiroIco.setFitWidth(icoSize);
        shiroIco.setFitHeight(icoSize);    
        shiroIco.relocate(posX, posY+distY);
        
        //setFill(Color.rgb(222, 222, 222)); //text color
        Label a1 = new Label(akaName);
        a1.relocate(posX + icoSize + icoMargin, posY-textShift);
        Label a2 = new Label(akaClub);
        a2.relocate(posX + icoSize + icoMargin, posY+distClub-textShift);
        a2.getStyleClass().add("club");
        Label a3 = new Label(akaScore);
        a3.relocate(posX+distScore, posY+0-textShift+5);
        a3.getStyleClass().add("score");
        
        Label s1 = new Label(shiroName);
        s1.relocate(posX + icoSize + icoMargin, posY+distY-textShift);
        Label s2 = new Label(shiroClub);
        s2.relocate(posX + icoSize + icoMargin, posY+distClub+distY-textShift);
        s2.getStyleClass().add("club");
        Label s3 = new Label(shiroScore);
        s3.relocate(posX+distScore, posY+distY-textShift+5);
        s3.getStyleClass().add("score");
        
        Line l = new Line(posX, posY+distY-15, posX+distScore+20, posY+distY-15);
        l.getStyleClass().add("line");
        
        //create match region and add it to the chart
        Pane m = new Pane();
        m.getChildren().setAll(akaIco,shiroIco, a1,a2,a3, s1,s2,s3, l);
        this.getChildren().addAll(m);
        
    }
}
