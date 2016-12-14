/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.presentation.tournamentTab;

import java.io.IOException;
import matchescreation.presentation.competitorsTab.*;
import matchescreation.presentation.startingListsTab.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import matchescreation.Main;

/**
 *
 * @author Emilia
 */
public class TournamentTabController implements Initializable{

    @FXML
    private Tab tournamentTab;
    @FXML
    private AnchorPane rootPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Load chart maker.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("presentation/tournamentTab/ChartMaker.fxml"));
            
            AnchorPane chartMaker = (AnchorPane) loader.load();
           
            // Set it into the center of root layout.

            
            rootPane.getChildren().add(chartMaker);
            
            // Give the controller access to the main app.
        ChartMakerController controller = loader.getController();
//        controller.setMainApp(this);
//        controller.setChartRoot(chartMaker);
        controller.reloadChart();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
