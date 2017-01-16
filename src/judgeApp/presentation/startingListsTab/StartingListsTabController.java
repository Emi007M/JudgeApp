/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.presentation.startingListsTab;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import serializable.model.Competition;
import judgeApp.model.CurrentTournament;
import judgeApp.model.SocketClient;
import judgeApp.presentation.RootLayoutController;

/**
 *
 * @author Emilia
 */
public class StartingListsTabController implements Initializable {

//    @FXML
//    private AnchorPane rootPane;
    @FXML
    private Label titleLabel;
    @FXML
    private TableView<Competition> table;

    @FXML
    private TableColumn<Competition, Integer> colID, colContestants;
    @FXML
    private TableColumn<Competition, String> colName, colProgress, colSent;
    //, columnProgress, columnRefresh, columnSent;
    
    @FXML
    private TextFlow descrBox;

    @FXML
    private ObservableList<Competition> dataArray;
    
    
    private RootLayoutController rootController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
          
        Button dwnBtn = new Button();   
        dwnBtn.setId("downloadBtn");
        dwnBtn.setOnAction(e -> this.handleDownloadButtonAction());
        table.setPlaceholder(dwnBtn);
        
        dwnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                handleDownloadButtonAction();
            }
        });

        
        table.setColumnResizePolicy(p -> true);
        table.setSortPolicy(e -> false);

//        TableColumn<InvoiceEntry, String> firstNameCol = new TableColumn<InvoiceEntry, String>("First Name");
//        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
//        TableColumn<InvoiceEntry, String> lastNameCol = new TableColumn<InvoiceEntry, String>("Last Name");
//        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));

     //   table.getColumns().setAll(firstNameCol, lastNameCol);
     
        dataArray = FXCollections.observableArrayList();
        table.setItems(dataArray);
        
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            this.onRowChosen(newSelection);
        }
        
});
      
    //    fillInTable();

    }

    public void init() {
        titleLabel.setText(CurrentTournament.getTournamentTitle());
        
        System.out.println("\033[32m" + CurrentTournament.getTournamentTitle() + "\033[0m");
//        System.out.println("\033[0m BLACK");
//        System.out.println("\033[31m RED");
//        System.out.println("\033[32m GREEN");
//        System.out.println("\033[33m YELLOW");
//        System.out.println("\033[34m BLUE");
//        System.out.println("\033[35m MAGENTA");
//        System.out.println("\033[36m CYAN");
//        System.out.println("\033[37m WHITE");

          TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
        header.setReordering(false);
            header.reorderingProperty().addListener(e->header.setReordering(false));

    }

    public void fillInTable() {
           ArrayList<Competition> competitions = CurrentTournament.getTournamentCompetitions(CurrentTournament.getBoardID());
           for(Competition c : competitions)
            c.initProperties();

        dataArray.clear();
        dataArray.addAll(competitions);

    }

    @FXML
    private void handleDownloadButtonAction() {
        System.out.println("downloading...");

        handleReloadButtonAction();
    }
    
    @FXML
    private void handleReloadButtonAction() {
        System.out.println("reload...");
        
        String ip = rootController.getIP();
        if(ip!=null){ //connect with ip given in textfield
            InetAddress ipaddr;
            try {
                ipaddr = InetAddress.getByName(ip);
                SocketClient.updateTournamentViaServer(ipaddr);
            } catch (UnknownHostException ex) {
                Logger.getLogger(StartingListsTabController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else { //connect with localhost
            SocketClient.updateTournamentViaServer(null);
        }
        
        CurrentTournament.getTournament().saveToFile();
        init();
        fillInTable();
    }
    

    
    @FXML
    private void onRowChosen(Competition c){
        descrBox.getChildren().clear();
        
        Label header = new Label("Description");
        Text descr = new Text("\n"+c.getDescr()+"\n\n");
        header.getStyleClass().add("descr-header");
        descr.getStyleClass().add("descr-text");
        
        Button btn = new Button("Choose");
        btn.setOnAction(i->onChooseBtn(c));
        
        descrBox.getChildren().addAll(header, descr, btn);
        
        
    }
    
    private void onChooseBtn(Competition c){
        
        rootController.chooseCompetition(c);
        
        System.out.println("Competition chosen");

    }
    
    
    public void setRootController(RootLayoutController r) {
        rootController = r;
    }
}
