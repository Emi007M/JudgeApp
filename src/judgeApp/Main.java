/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import judgeApp.model.CurrentTournament;
import judgeApp.model.Dictionary;
import serializable.model.Tournament;
import serializable.model.Serializator;
import judgeApp.presentation.RootLayoutController;

/**
 *
 * @author Emilia
 */
public class Main extends Application {

    private Stage primaryStage;
    public BorderPane rootLayout;
    private RootLayoutController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Internationalization        
        new Dictionary();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Dictionary.getString("window-title"));

        initRootLayout();
        initBoard(0);

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
        
        //get tournaments from folder
        ArrayList<Serializable> objs;
        objs = Serializator.readAllFromFolder("tournaments/current");
        Tournament t = (Tournament) objs.get(0);
        
        if(t!=null) {
           // CurrentTournament.setTournament( t);
        }
        else
            System.out.println("no tournament on local drive");

        //CurrentTournament.setTournament((Tournament) Serializator.readFromFile("tournaments/t_2016-06-20"));
  //      CurrentTournament.setTournament((Tournament) Serializator.readFromFile("tournaments/t_2017-01-20"));

        mainController.init();

    }

   

}
