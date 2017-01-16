/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.presentation;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import serializable.model.Competition;
import judgeApp.model.CurrentTournament;
import judgeApp.presentation.competitorsTab.CompetitorsTabController;
import judgeApp.presentation.resultsTab.ResultsTabController;
import judgeApp.presentation.startingListsTab.StartingListsTabController;
import judgeApp.presentation.tournamentTab.TournamentTabController;

/**
 *
 * @author Emilia
 */
public class RootLayoutController implements Initializable {

    @FXML
    TabPane tabs;
    
    @FXML
    private JFXComboBox boardCombo;
    @FXML
    private TextField ipField;

    @FXML
    private Tab tabStartingLists;
    private StartingListsTabController startingListsController;
    @FXML
    private Tab tabCompetitors;
    private CompetitorsTabController competitorsController;
    @FXML
    private Tab tabTournament;
    private TournamentTabController tournamentController;
    @FXML
    private Tab tabResults;
    private ResultsTabController resultsController;
    
    boolean initialized = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        boardCombo.getItems().addAll(0,1,2,3,4);
        boardCombo.setOnAction(e->handleComboBoxAction());
        boardCombo.setValue(0);
        
        try {
            FXMLLoader loader0 = new FXMLLoader(getClass().getResource("startingListsTab/StartingListsTab.fxml"));
            tabs.getTabs().get(0).setContent(loader0.load());
            startingListsController = loader0.getController();
            
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("competitorsTab/CompetitorsTab.fxml"));
            tabs.getTabs().get(1).setContent(loader1.load());
            competitorsController = loader1.getController();

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("tournamentTab/TournamentTab.fxml"));
            tabs.getTabs().get(2).setContent(loader2.load());
            tournamentController = loader2.getController();

            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("resultsTab/ResultsTab.fxml"));
            tabs.getTabs().get(3).setContent(loader3.load());
            resultsController = loader3.getController();

        } catch (IOException ex) {
            Logger.getLogger(RootLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tabs.getTabs().get(1).setDisable(true);
        tabs.getTabs().get(2).setDisable(true);
        tabs.getTabs().get(3).setDisable(true);

        initialized = true;

    }

    public void init() {
        if(initialized){
            startingListsController.init();
                 
        }
        else
            System.err.println("not initialized");
        
        
        
        startingListsController.setRootController(this);
        tournamentController.setRootController(this);
    }
    
    
    //to be used from StartingListsTab
    public void chooseCompetition(Competition c){
        CurrentTournament.setCurrentCompetition(c);
        System.out.println("Competition chosen root");
        
        tabs.getTabs().get(3).setDisable(true);
        
        competitorsController.init();
        tournamentController.init();
        
        tabs.getTabs().get(1).setDisable(false);
        tabs.getTabs().get(2).setDisable(false);
        
        //move to the next tab
        SingleSelectionModel<Tab> selectedTab = tabs.getSelectionModel();
        selectedTab.selectNext();
        
    }

    public void displayResults() {
        initResults();
        //move to the next tab
        SingleSelectionModel<Tab> selectedTab = tabs.getSelectionModel();
        selectedTab.selectNext();
    }
    
    public void initResults() {
        resultsController.init();  
        tabs.getTabs().get(3).setDisable(false);    
    }

    private void handleComboBoxAction() {
        CurrentTournament.setBoardID((Integer) boardCombo.getValue());
        startingListsController.fillInTable();
        
    }
    
    public String getIP(){
        String ret = null;
        ret = ipField.getText();
        return ret;
    }
    
  
}
