/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import matchescreation.Chart;
import matchescreation.Dictionary;
import matchescreation.Main;
import matchescreation.Node;
import matchescreation.ZoomHandler;


/**
 *
 * @author Emilia
 */
public class ChartMakerController implements Initializable{
    
    private AnchorPane root;
 
    @FXML
    private HBox box;
    @FXML
    private VBox chartBox;
    @FXML
    private VBox matchesBox;
    
    @FXML
    private ScrollPane bracketScrollPane;
    @FXML
    private Group bracketScrollContent;
    @FXML
    private StackPane bracketZoomGroup;
    
    @FXML
    private TextField athletesNo;
    
    
    @FXML
    private Label currLvl;
    @FXML
    private Label currentShiro;
    @FXML
    private Label currentAka;
    @FXML
    private Label currentShiroClub;
    @FXML
    private Label currentAkaClub;
    //--
    @FXML
    private Label nextLvl;
    @FXML
    private Label nextShiro;
    @FXML
    private Label nextAka;
    @FXML
    private Label nextShiroClub;
    @FXML
    private Label nextAkaClub;
    
    
    @FXML
    private Button shiroBtn;
    @FXML
    private Button akaBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button generateBtn;
    @FXML
    private Button applyBtn;
    

    
    Main mainApp;
    
    public ChartMakerController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //chart.setText("gotIT!");
        
        //box.getChildren().add(new Button("Java Button"));
 
       //  bracketScrollPane.addEventFilter(ScrollEvent.ANY, new ZoomHandler(bracketZoomGroup,));
        
        
        
    }
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        //chart.setText(mainApp.getMatches());
    }
    
    @FXML
    private void handleButtonAction(javafx.event.ActionEvent event) {
        System.out.println("You clicked me!");
        //int amount = Integer.parseInt( athletesNo.getText());
        //if(amount<=0) amount = 8;
        
        mainApp.currentChart = new Chart();
        reloadChart();
    }
    
    @FXML
    private void handleShiroBtnAction(javafx.event.ActionEvent event){
        System.out.println("SHIRO");
        int score = Integer.parseInt(this.shiroBtn.getText());
        this.shiroBtn.setText(Integer.toString(++score));
    }
    
    @FXML
    private void handleAkaBtnAction(javafx.event.ActionEvent event){
        System.out.println("AKA");
        int score = Integer.parseInt(this.akaBtn.getText());
        this.akaBtn.setText(Integer.toString(++score));
    }
     @FXML
    private void handleShiroBtnSubtract(MouseEvent event){
        if(event.getButton() != MouseButton.SECONDARY) return;
        System.out.println("SHIRO");
        int score = Integer.parseInt(this.shiroBtn.getText());
        score = (score==0)? 0 : score-1;
        this.shiroBtn.setText(Integer.toString(score));
    }
    
    @FXML
    private void handleAkaBtnSubtract(MouseEvent event){
        if(event.getButton() != MouseButton.SECONDARY) return;
        System.out.println("AKA");
        int score = Integer.parseInt(this.akaBtn.getText());
        score = (score==0)? 0 : score-1;
        this.akaBtn.setText(Integer.toString(score));
    }
    
    @FXML
    private void handleStartBtnAction(javafx.event.ActionEvent event){
        System.out.println("Start");
        updateCurrentMatch();
        root.getChildren().remove(this.startBtn); 
        root.getChildren().remove(this.generateBtn); 
        
    }
    
    @FXML
    private void handleNextBtnAction(javafx.event.ActionEvent event){
        System.out.println("saving and going to next match");
        int scoreAka = Integer.parseInt(this.akaBtn.getText());
        int scoreShiro = Integer.parseInt(this.shiroBtn.getText());
        
        if(scoreAka==scoreShiro) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(Dictionary.getString("draw-warning"));
            alert.setHeaderText(Dictionary.getString("draw"));
            alert.setContentText(Dictionary.getString("draw-text"));

            alert.showAndWait();
            return;
        }
        
        Node match = mainApp.currentChart.getFirstMatch();
        match.setScoreAka(scoreAka);
        match.setScoreShiro(scoreShiro);
        if(scoreAka>scoreShiro) match.setAthlete(match.getAka().getAthlete());
        else match.setAthlete(match.getShiro().getAthlete());
        
        reloadChart();
        updateCurrentMatch();
        updateMatchesBox();
    }
    
    public void reloadChart(){
        
        if(mainApp.currentChart==null)
            mainApp.currentChart = new Chart();
        
        
        int lvls = mainApp.currentChart.getMaxLvl();
        
        
        //performance minimal notification overhead ->replaced code
//        
//        chartBox.getChildren().clear();
//        
//        for(int i=lvls;i>=0;i--)
//            chartBox.getChildren().add(new Label(mainApp.currentChart.getLvl(i)));
//        
//
//        List<Label> labels = new ArrayList<>();
//        for(int i=lvls;i>=0;i--)
//            labels.add(new Label(mainApp.currentChart.getLvl(i)));
   
        //chartBox.getChildren().setAll(labels);
        showBrackets();
        updateMatchesBox();
        
    }
    
     
    public void updateCurrentMatch(){
      Node match = mainApp.currentChart.getFirstMatch();
      if(match !=null){
          
          this.currLvl.setText(mainApp.currentChart.getLvlToString(match.getChartLvl()));
          
          //if there are both athletes
          if(match.hasOneAthlete()==null){
            this.akaBtn.setText("0");
            this.shiroBtn.setText("0");

            this.currentAka.setText(match.getAka().getAthlete().getFullName());
            this.currentShiro.setText(match.getShiro().getAthlete().getFullName());
            this.currentAkaClub.setText(match.getAka().getAthlete().getClub());
            this.currentShiroClub.setText(match.getShiro().getAthlete().getClub());



            updateNextMatch();

          }
          else {//if there is only one athlete
            this.akaBtn.setText("0");
            this.shiroBtn.setText("0");

            this.currentAka.setText("-");
            this.currentShiro.setText("-");
            this.currentAkaClub.setText("");
            this.currentShiroClub.setText("");
          }
      }//if there is no match
      else {
          this.akaBtn.setText("0");
          this.shiroBtn.setText("0");
          
          this.currentAka.setText("-");
          this.currentShiro.setText("-");
          this.currentAkaClub.setText("");
          this.currentShiroClub.setText("");
          
          this.currLvl.setText("");
      }
      
      
    }
    
    public void updateNextMatch(){
      Node match = mainApp.currentChart.getWaitingMatch();
      if(match !=null ){
        
        this.nextLvl.setText(mainApp.currentChart.getLvlToString(match.getChartLvl()));
        
        //if there are both athletes
        if(match.hasOneAthlete()==null){
          
          this.nextAka.setText(match.getAka().getAthlete().getFullName());
          this.nextShiro.setText(match.getShiro().getAthlete().getFullName());
          this.nextAkaClub.setText(match.getAka().getAthlete().getClub());
          this.nextShiroClub.setText(match.getShiro().getAthlete().getClub());
        } 
        else {//if there is only one athlete
            this.nextAka.setText("");
          this.nextShiro.setText("");
          this.nextAkaClub.setText("");
          this.nextShiroClub.setText("");
        }
               

      }
      else {//if there is no match
          this.nextAka.setText("");
          this.nextShiro.setText("");
          this.nextAkaClub.setText("");
          this.nextShiroClub.setText("");
          
          this.nextLvl.setText("");
      }
      
      
    }
    
    public void updateMatchesBox(){
        ArrayList<Node> matches = mainApp.currentChart.getMatches();
        ArrayList<Control> elements = new ArrayList<>();
        
        Node current = mainApp.currentChart.getFirstMatch();
        
        int lvl = -1;
        
        for(Node m: matches){
            if(lvl != m.getChartLvl()) {
                lvl = m.getChartLvl();
                Label l = new Label(mainApp.currentChart.getLvlToString(lvl));
                l.getStyleClass().add("lvl");
                elements.add(new Label());
                elements.add(l);
            }
            Label l = new Label(m.toString());
            if(m.equals(current)) l.getStyleClass().add("current");
            elements.add(l);
        }
        elements.add(new Label());
        
        this.matchesBox.getChildren().setAll(elements);
    }

    public void setChartRoot(AnchorPane root) {
        this.root = root;
    }
    
    
    public void showBrackets() {

        bracketScrollPane.addEventFilter(ScrollEvent.ANY, new ZoomHandler(bracketZoomGroup,chartBox));
        chartBox.setStyle("-fx-background-color: #222");
        
        
        ArrayList<Node> matches = mainApp.currentChart.getBracketMatches();
  
        Node current = mainApp.currentChart.getFirstMatch();
        
        BracketChart chart = new BracketChart(matches, current);

        
        
        chartBox.getChildren().setAll(chart);
      //  bracketScrollContent = new Group(bracketZoomGroup);
        //bracketScrollPane.getChildrenUnmodifiable().setAll(bracketScrollContent);
        
        chart.setScrollFixed(bracketScrollPane, bracketScrollContent,bracketZoomGroup);
        
        
        
    
    }
    
    
    
}
