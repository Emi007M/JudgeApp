/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Emilia
 */
public class RootLayoutController implements Initializable{

    @FXML
    TabPane tabs;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            
            tabs.getTabs().add((Tab) FXMLLoader.load(getClass().getResource("startingListsTab/StartingListsTab.fxml")));
            tabs.getTabs().add((Tab) FXMLLoader.load(getClass().getResource("competitorsTab/CompetitorsTab.fxml")));
            tabs.getTabs().add((Tab) FXMLLoader.load(getClass().getResource("tournamentTab/TournamentTab.fxml")));
        
        } catch (IOException ex) {
            Logger.getLogger(RootLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
