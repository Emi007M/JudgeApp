/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.view;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import matchescreation.Chart;
import matchescreation.Dictionary;
import matchescreation.Node;

/**
 *
 * @author Emilia
 */
public class BracketChart extends Region {
    
    private ArrayList<Node> matches;
    private Node current;
    
    private int vert_gap;
    private int match_width;


    BracketChart(ArrayList<Node> matches, Node current) {
        this.matches = matches;
        this.current = current;      
        this.setId("bracket-chart");
        
        vert_gap = 70;
        match_width = 220;
        
        initBracket();
    }
    
    private void initBracket(){
        int lvl = matches.get(0).getChartLvl();
        int cur_lvl = lvl;
        int m_amount = matches.size();
        int j = 0;
        
        
        addLvlNames();
        
        
        for(int i = 0; i< m_amount;i++){
            if(cur_lvl>matches.get(i).getChartLvl()){
                cur_lvl = matches.get(i).getChartLvl();
                j=0;
            }
            int tmp = lvl-cur_lvl;
            int posX = (tmp)*300+30; //horizontal distance
            int posY = (int) (Math.pow(2,tmp)*vert_gap + j++*2*vert_gap*(Math.pow(2, tmp))); // vertical distance + top shift

            if(matches.get(i).equals(current))
                this.addCurrMatch(matches.get(i), posX, posY);
            else    
                this.addMatch(matches.get(i), posX, posY);
       
            if(cur_lvl<lvl)
                addJoining(posX, posY, (int) ((Math.pow(2, tmp-1))*vert_gap));
        }
        
        addDottedLine(lvl*300+30, (int) (Math.pow(2,lvl)*vert_gap));
        
    }
    
    private void addMatch(Node match, int posX, int posY){
        
        //prepare names
        String akaName = "-";
        String akaClub = "-";
        if(match.getAka() != null && match.getAka().getAthlete() != null){
            akaName = match.getAka().getAthlete().getFullName();
            akaClub = match.getAka().getAthlete().getClub();
        }
        String akaScore = (match.getScoreAka() != null) ? match.getScoreAka().toString() : "";
        
        String shiroName = "-";
        String shiroClub = "-";
        if(match.getShiro() != null && match.getShiro().getAthlete() != null){
            shiroName = match.getShiro().getAthlete().getFullName();
            shiroClub = match.getShiro().getAthlete().getClub();
        }
        String shiroScore = (match.getScoreShiro() != null) ? match.getScoreShiro().toString() : "";
        
        
        
        
        //distances
        int distY = 50;
        int distClub = 20;
        int distScore = match_width;
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
    
    private void addCurrMatch(Node match, int posX, int posY){
        Rectangle rect = new Rectangle(posX-15, posY-15, match_width+50, 100);
        rect.getStyleClass().add("current-match");
        
        Label l = new Label(Dictionary.getString("current-match").toUpperCase());
        l.relocate(posX-15, posY-35);
        l.getStyleClass().add("current-match-text");
        l.setStyle("-fx-min-width: " + (match_width+50) + ";");
        
        this.getChildren().addAll(rect, l);
        
        addMatch(match,posX,posY);
    }
    
    private void addJoining(int posX, int posY, int height){
        int width = 20;
        int dist = height;
        
        posX-= width+10;
        posY+= 35;
        
        Line l_top = new Line(posX-width, posY-dist, posX, posY-dist);
        l_top.getStyleClass().add("line");
        
        Line l_bottom = new Line(posX-width, posY+dist, posX, posY+dist);
        l_bottom.getStyleClass().add("line");
        
        Line l_middle = new Line(posX, posY, posX+width, posY);
        l_middle.getStyleClass().add("line");
        
        Line l_vert = new Line(posX, posY-dist, posX, posY+dist);
        l_vert.getStyleClass().add("line");
        
        this.getChildren().addAll(l_top, l_bottom, l_middle, l_vert);
    }
    
    private void addLvlNames(){
        int i = matches.get(0).getChartLvl();
        int j=0;
        
        ArrayList<Label> labels = new ArrayList<>();
        for(;i>=0;i--){
            Label l = new Label(Chart.getLvlToString(i).toUpperCase());
            l.relocate(j++*300+30, 10);
            l.getStyleClass().add("lvl");
            l.getStyleClass().add("lvl-"+i);
            l.setStyle("-fx-min-width: " + (match_width+50) + ";");
            labels.add(l);
        }
        
        this.getChildren().addAll(labels);
    }
    
    private void addDottedLine(int posX, int posY){
        Line l = new Line(30, posY+35, posX-10, posY+35);
        l.getStyleClass().add("line");
        l.getStyleClass().add("dotted-line");
        l.getStrokeDashArray().addAll(30d, 30d);
        
        this.getChildren().add(l);
    }
}
