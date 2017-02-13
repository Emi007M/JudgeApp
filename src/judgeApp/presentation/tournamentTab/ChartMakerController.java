package judgeApp.presentation.tournamentTab;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import judgeApp.model.CurrentTournament;
import judgeApp.model.Dictionary;
import judgeApp.model.ZoomHandler;
import serializable.model.Chart;
import serializable.model.Node;

/**
 *
 * @author Emilia
 */
public class ChartMakerController implements Initializable {

    @FXML
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
    private Text currentShiro;
    @FXML
    private Text currentAka;
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

    //Main mainApp;
    Chart currentChart;
    TournamentTabController tournamentController;

    public ChartMakerController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //click enter to fire the applyBtn
        applyBtn.setDefaultButton(true);

        //box.getChildren().add(new Button("Java Button"));
        //  bracketScrollPane.addEventFilter(ScrollEvent.ANY, new ZoomHandler(bracketZoomGroup,));
        bracketScrollPane.addEventFilter(ScrollEvent.ANY, new ZoomHandler(bracketZoomGroup));

    }

    public void setTournamentController(TournamentTabController c) {
        tournamentController = c;
    }

    @FXML
    private void handleShiroBtnAction(javafx.event.ActionEvent event) {
        System.out.println("SHIRO");
        int score = Integer.parseInt(this.shiroBtn.getText());
        this.shiroBtn.setText(Integer.toString(++score));
    }

    @FXML
    private void handleAkaBtnAction(javafx.event.ActionEvent event) {
        System.out.println("AKA");
        int score = Integer.parseInt(this.akaBtn.getText());
        this.akaBtn.setText(Integer.toString(++score));
    }

    @FXML
    private void handleShiroBtnSubtract(MouseEvent event) {
        if (event.getButton() != MouseButton.SECONDARY) {
            return;
        }
        System.out.println("SHIRO");
        int score = Integer.parseInt(this.shiroBtn.getText());
        score = (score == 0) ? 0 : score - 1;
        this.shiroBtn.setText(Integer.toString(score));
    }

    @FXML
    private void handleAkaBtnSubtract(MouseEvent event) {
        if (event.getButton() != MouseButton.SECONDARY) {
            return;
        }
        System.out.println("AKA");
        int score = Integer.parseInt(this.akaBtn.getText());
        score = (score == 0) ? 0 : score - 1;
        this.akaBtn.setText(Integer.toString(score));
    }

    @FXML
    private void handleNextBtnAction(javafx.event.ActionEvent event) {
        System.out.println("saving and going to next match");
        int scoreAka = Integer.parseInt(this.akaBtn.getText());
        int scoreShiro = Integer.parseInt(this.shiroBtn.getText());

        if (scoreAka == scoreShiro) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle(Dictionary.getString("t3.draw.title"));
            alert.setHeaderText(Dictionary.getString("t3.draw.info"));
            alert.setContentText(Dictionary.getString("t3.draw.text"));

            alert.showAndWait();
            return;
        }

        Node match = currentChart.getFirstMatch();
        match.setScoreAka(scoreAka);
        match.setScoreShiro(scoreShiro);
        if (scoreAka > scoreShiro) {
            match.setAthlete(match.getAka().getAthlete());
        } else {
            match.setAthlete(match.getShiro().getAthlete());
        }

        CurrentTournament.getCurrentCompetition().setLocked(true);

        //if end of the chart
        if (currentChart.getWinnerNode().getAthlete() != null) {
            System.out.println("competition finished!");
            tournamentController.setResults();

        }

        reloadChart();
        updateCurrentMatch();
        updateMatchesBox();
    }

    public void reloadChart() {

//        if(currentChart==null)
//            currentChart = new Chart();
//        
        int lvls = currentChart.getMaxLvl();

        showBrackets();
        updateMatchesBox();

    }

    /**
     * updates the Score View (both current and next match)
     */
    public void updateCurrentMatch() {
        Node match = currentChart.getFirstMatch();
        if (match != null) {

            this.currLvl.setText(currentChart.getLvlToString(match.getChartLvl()));

            //if there are both athletes
            if (match.hasOneAthlete() == null) {
                this.akaBtn.setText("0");
                this.shiroBtn.setText("0");

                this.currentAka.setText(match.getAka().getAthlete().getFullName().toUpperCase());
                this.currentShiro.setText(match.getShiro().getAthlete().getFullName().toUpperCase());
                this.currentAkaClub.setText(match.getAka().getAthlete().getClub().toUpperCase());
                this.currentShiroClub.setText(match.getShiro().getAthlete().getClub().toUpperCase());

                updateNextMatch();

            } else {//if there is only one athlete
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

    /**
     * used from UpdateCurrentMatch updates part of the Score View
     */
    private void updateNextMatch() {
        Node match = currentChart.getWaitingMatch();
        if (match != null) {

            this.nextLvl.setText(currentChart.getLvlToString(match.getChartLvl()));

            //if there are both athletes
            if (match.hasOneAthlete() == null) {

                this.nextAka.setText(match.getAka().getAthlete().getFullName().toUpperCase());
                this.nextShiro.setText(match.getShiro().getAthlete().getFullName().toUpperCase());
                this.nextAkaClub.setText(match.getAka().getAthlete().getClub().toUpperCase());
                this.nextShiroClub.setText(match.getShiro().getAthlete().getClub().toUpperCase());
            } else {//if there is only one athlete
                this.nextAka.setText("");
                this.nextShiro.setText("");
                this.nextAkaClub.setText("");
                this.nextShiroClub.setText("");
            }

        } else {//if there is no match
            this.nextAka.setText("");
            this.nextShiro.setText("");
            this.nextAkaClub.setText("");
            this.nextShiroClub.setText("");

            this.nextLvl.setText("");
        }

    }

    /**
     * initializes/updates the list of matches view
     */
    public void updateMatchesBox() {
        ArrayList<Node> matches = currentChart.getMatches();
        ArrayList<Control> elements = new ArrayList<>();

        Node current = currentChart.getFirstMatch();

        int lvl = -1;

        for (Node m : matches) {
            if (lvl != m.getChartLvl()) {
                lvl = m.getChartLvl();
                Label l = new Label(currentChart.getLvlToString(lvl));
                l.getStyleClass().add("lvl");
                elements.add(new Label());
                elements.add(l);
            }
            Label l = new Label(m.toString());
            if (m.equals(current)) {
                l.getStyleClass().add("current");
            }
            elements.add(l);
        }
        elements.add(new Label());

        this.matchesBox.getChildren().setAll(elements);
    }

    public void showBrackets() {

//        bracketScrollPane.addEventFilter(ScrollEvent.ANY, new ZoomHandler(bracketZoomGroup));
//        
        ArrayList<Node> matches = currentChart.getBracketMatches();

        Node current = currentChart.getFirstMatch();

        BracketView chart = new BracketView(matches, current);

        chartBox.getChildren().setAll(chart);

        chart.setScrollFixed(bracketScrollPane, bracketScrollContent, bracketZoomGroup);

    }

}
