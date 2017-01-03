/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation;

import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import matchescreation.model.Chart;
import matchescreation.model.CurrentTournament;
import matchescreation.model.Dictionary;
import matchescreation.model.Tournament;
import matchescreation.model.Serializator;
import matchescreation.presentation.RootLayoutController;
import matchescreation.presentation.startingListsTab.StartingListsTabController;
import matchescreation.presentation.tournamentTab.ChartMakerController;

/**
 *
 * @author Emilia
 */
public class Main extends Application {

    private Stage primaryStage;
    public BorderPane rootLayout;

    //public Chart currentChart;
    private RootLayoutController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Internationalization        
        new Dictionary();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Dictionary.getString("window-title"));

        initRootLayout();

        //showChartMaker();
//        Tournament t = new Tournament("Zawody testowe", new Date(2017,2,15));
//        t.init();
//        Serializator.writeToFile(t, "tournaments/tournament1");
        initBoard(0);
        //currentChart = new Chart();
        //updateCurrentMatch();
    }

    public void initRootLayout() {

        try {

            Font.loadFont(Main.class.getResource("presentation/resources/fonts/Toxia_FRE.ttf").toExternalForm(), 10);
            Font.loadFont(Main.class.getResource("presentation/resources/fonts/phagspa.ttf").toExternalForm(), 10);
            Font.loadFont(Main.class.getResource("presentation/resources/fonts/phagspab.ttf").toExternalForm(), 10);

            // Load root layout from fxml file.            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "presentation/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            mainController = loader.getController();


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initBoard(Integer boardId) {
//        Tournament t = new Tournament("tytuł", new Date(2016,12,12));
//        t.init();
//        Serializator.writeToFile(t,"tournaments/tournament2");
        CurrentTournament.setTournament((Tournament) Serializator.readFromFile("tournaments/tournament1"));

//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("presentation/R.fxml"));
//        StartingListsTabController c = loader.getController();
        mainController.init();

    }

//    public void showChartMaker() {
//        try {
//            // Load chart maker.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("presentation/ChartMaker.fxml"));
//            
//            AnchorPane chartMaker = (AnchorPane) loader.load();
//           // AnchorPane chartMaker = (AnchorPane) FXMLLoader.load(getClass().getResource("presentation/ChartMaker.fxml"));
//           
//
//            // Set it into the center of root layout.
//            //rootLayout.setCenter(chartMaker);
//            AnchorPane tournamentTab = (AnchorPane) rootLayout.lookup("#tournamentTab");
//            AnchorPane.setTopAnchor(chartMaker, 10.0); 
//            tournamentTab.getChildren().add(chartMaker);
//            
//            // Give the controller access to the main app.
//        ChartMakerController controller = loader.getController();
////        controller.setMainApp(this);
////        controller.setChartRoot(chartMaker);
//        controller.reloadChart();
//        
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
