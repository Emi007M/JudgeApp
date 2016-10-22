/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import matchescreation.view.ChartMakerController;

/**
 *
 * @author Emilia
 */
public class Main extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    public Chart currentChart;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showChartMaker();
        

        currentChart = new Chart();
    }
    
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            rootLayout = (BorderPane) FXMLLoader.load(getClass().getResource("view/RootLayout.fxml"));

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
            loader.setLocation(Main.class.getResource("view/ChartMaker.fxml"));
            AnchorPane chartMaker = (AnchorPane) loader.load();
           // AnchorPane chartMaker = (AnchorPane) FXMLLoader.load(getClass().getResource("view/ChartMaker.fxml"));

            // Set it into the center of root layout.
            rootLayout.setCenter(chartMaker);
            
            // Give the controller access to the main app.
        ChartMakerController controller = loader.getController();
        controller.setMainApp(this);
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

    public String getMatches() {
        return "1-2 3-4 5-6";
    }
    
}
