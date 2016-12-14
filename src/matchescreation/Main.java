/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation;

import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.IOException;
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
import matchescreation.model.Dictionary;
import matchescreation.presentation.ChartMakerController;

/**
 *
 * @author Emilia
 */
public class Main extends Application {
    
    private Stage primaryStage;
    public BorderPane rootLayout;
    
    public Chart currentChart;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //Internationalization        
        new Dictionary();
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Dictionary.getString("window-title"));

        initRootLayout();

        showChartMaker();
        

        //currentChart = new Chart();
        //updateCurrentMatch();
    }
    
    public void initRootLayout() {
      
        
        try {
            
            Font.loadFont(Main.class.getResource("presentation/resources/Toxia_FRE.ttf").toExternalForm(), 10);
            Font.loadFont(Main.class.getResource("presentation/resources/phagspa.ttf").toExternalForm(), 10);
            Font.loadFont(Main.class.getResource("presentation/resources/phagspab.ttf").toExternalForm(), 10);
            // Load root layout from fxml file.
            rootLayout = (BorderPane) FXMLLoader.load(getClass().getResource("presentation/RootLayout.fxml"));

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showChartMaker() {
        try {
            // Load chart maker.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("presentation/ChartMaker.fxml"));
            AnchorPane chartMaker = (AnchorPane) loader.load();
           // AnchorPane chartMaker = (AnchorPane) FXMLLoader.load(getClass().getResource("presentation/ChartMaker.fxml"));
           

            // Set it into the center of root layout.
            //rootLayout.setCenter(chartMaker);
            AnchorPane tournamentTab = (AnchorPane) rootLayout.lookup("#tournamentTab");
            AnchorPane.setTopAnchor(chartMaker, 10.0); 
            tournamentTab.getChildren().add(chartMaker);
            
            // Give the controller access to the main app.
        ChartMakerController controller = loader.getController();
        controller.setMainApp(this);
        controller.setChartRoot(chartMaker);
        controller.reloadChart();
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
       
    }

}
