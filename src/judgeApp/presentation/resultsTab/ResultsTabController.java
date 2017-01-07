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
        
        for(String r : CurrentTournament.getCurrentCompetition().getResults()){
            items.add(r);
        }
    }
    
}
