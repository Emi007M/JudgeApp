/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.presentation.resultsTab;



import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import judgeApp.model.CurrentTournament;
import judgeApp.model.SocketClient;
import serializable.model.Competition;

/**
 *
 * @author Emilia
 */
public class ResultsTabController implements Initializable{
    
    @FXML
    private BorderPane rootPane;
    @FXML
    private ListView list;
    
    private ObservableList<String> items;
    
    @FXML 
    private Button sendBtn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.setPadding(new Insets(0, 10, 0, 10));

        items =FXCollections.observableArrayList();
        list.setItems(items);
        
    }

    public void init() {
        items.clear();
        
        if(!CurrentTournament.getCurrentCompetition().hasResults())
            return;
        
        for(String r : CurrentTournament.getCurrentCompetition().getResults()){
            items.add(r);
        }
    }
    
    @FXML
    public void handleSendBtn(){
        System.out.println("sending results");
        Competition current = CurrentTournament.getCurrentCompetition();
        if(CurrentTournament.getCurrentCompetition().isFinished())
            System.out.println("finished" ); 
        else 
            System.out.println("not finished");  
        SocketClient.sendResultsToServer(CurrentTournament.getCurrentCompetition(), null);
        
        CurrentTournament.setCurrentCompetition(current);
    }
    
}
