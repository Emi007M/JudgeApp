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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import matchescreation.Chart;
import matchescreation.Main;

/**
 *
 * @author Emilia
 */
public class ChartMakerController implements Initializable{
    
    
 
    @FXML
    private HBox box;
    @FXML
    private VBox chartBox;
    
    @FXML
    private TextField athletesNo;
    
    @FXML
    private Label currentShiro;
    @FXML
    private Label currentAka;
    @FXML
    private Label currentShiroClub;
    @FXML
    private Label currentAkaClub;
    
    @FXML
    private Button shiroBtn;
    @FXML
    private Button akaBtn;
    

    
    Main mainApp;
    
    public ChartMakerController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //chart.setText("gotIT!");
        
        //box.getChildren().add(new Button("Java Button"));
        
        
        
        
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
    }
    
    @FXML
    private void handleAkaBtnAction(javafx.event.ActionEvent event){
        System.out.println("AKA");
    }
    
    
    public void reloadChart(){
        
        if(mainApp.currentChart==null)
            mainApp.currentChart = new Chart();
        
        
        int lvls = mainApp.currentChart.getMaxLvl();
        
        chartBox.getChildren().clear();
        
        for(int i=lvls;i>=0;i--){
            chartBox.getChildren().add(new Label(mainApp.currentChart.getLvl(i)));
        }
    }
    
     
    public void updateCurrentMatch(){
      //TODO  
    } 
    
}
