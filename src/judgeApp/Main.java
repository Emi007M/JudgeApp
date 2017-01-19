/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import judgeApp.model.CurrentTournament;
import judgeApp.model.Dictionary;
import judgeApp.presentation.RootLayoutController;
import serializable.model.Serializator;
import serializable.model.Tournament;

/**
 *
 * @author Emilia
 */
public class Main extends Application {

    private Stage primaryStage;
    public BorderPane rootLayout;
    private RootLayoutController mainController;

    public static void main(String[] args) throws IOException {
        //needed for buiding .exe file
        Application.launch(Main.class, (java.lang.String[]) null);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Internationalization        
        new Dictionary();

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Dictionary.getString("window-title"));

        this.primaryStage.getIcons().add(new Image("file:W:/Netbeans Projects/judgeApp/src/judgeApp/presentation/resources/images/ico128.png"));

        initRootLayout();
        initBoard();

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Saving changes");
                alert.setHeaderText("Saving changes");
                alert.setContentText("Are you sure, you want to save all the changes?");
                ButtonType buttonTypeOne = new ButtonType("Save and close");
                ButtonType buttonTypeTwo = new ButtonType("Close without saving");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne) {
                    if (CurrentTournament.getTournament() != null) {
                        CurrentTournament.getTournament().saveToFile();
                    }
                } else if (result.get() == buttonTypeTwo) {

                } else {
                    // ... user chose CANCEL or closed the dialog
                    we.consume();
                }
            }
        });

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

    public void initBoard() {

        //get tournaments from folder
        ArrayList<Serializable> objs;
        objs = Serializator.readAllFromFolder("tournaments/current");
        Tournament t = null;
        if (objs.size() > 0) {
            t = (Tournament) objs.get(0);
        }

        if (t != null) {
            CurrentTournament.setTournament(t);
        } else {
            System.out.println("no tournament on local drive");
        }

        mainController.init();

    }

}
