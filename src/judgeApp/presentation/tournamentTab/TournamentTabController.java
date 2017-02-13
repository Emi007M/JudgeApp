/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.presentation.tournamentTab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import judgeApp.Main;
import judgeApp.model.CurrentTournament;
import judgeApp.presentation.RootLayoutController;

/**
 *
 * @author Emilia
 */
public class TournamentTabController implements Initializable {

    @FXML
    private AnchorPane rootPane;    
    @FXML
    private ScrollPane rootScrollPane;

    private ChartMakerController controller;
    private RootLayoutController rootController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Load chart maker.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("presentation/tournamentTab/ChartMaker.fxml"));

            AnchorPane chartMaker = (AnchorPane) loader.load();

            rootScrollPane.setContent(chartMaker);
            //.add(chartMaker);

            // Give the controller access to the main app.
            controller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        controller.currentChart = CurrentTournament.getCurrentCompetition().getChart();
        controller.reloadChart();
        controller.updateCurrentMatch();
        controller.setTournamentController(this);

        if (CurrentTournament.getCurrentCompetition().hasResults()) {
            rootController.initResults();
        }
    }

    /**
     * (used from ChartMakerController) sets current competition as finished,
     * generates the results, then opens the Results tab and saves tournament to
     * a file
     */
    void setResults() {
        CurrentTournament.getCurrentCompetition().setFinished(true);
        CurrentTournament.getCurrentCompetition().setResults();
        rootController.displayResults();

        CurrentTournament.getTournament().saveToFile();
    }

    public void setRootController(RootLayoutController c) {
        rootController = c;

    }
}
