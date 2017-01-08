/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.presentation.startingListsTab;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import serializable.model.Competition;
import judgeApp.model.CurrentTournament;
import judgeApp.presentation.RootLayoutController;

/**
 *
 * @author Emilia
 */
public class StartingListsTabController implements Initializable {

//    @FXML
//    private AnchorPane rootPane;
//    @FXML
//    private Label titleLabel;
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
        //  titleLabel.setText(CurrentTournament.getTournamentTitle());
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
//        header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                header.setReordering(false);
//            }
//        });
//        columnNo.setCellValueFactory(new PropertyValueFactory<Competition,Integer>("idp"));
//        columnCategory.setCellValueFactory(new PropertyValueFactory<Competition,String>("titlep"));
//        columnProgress.setCellValueFactory(new MapValueFactory(new String("colProgress")));
//        columnRefresh.setCellValueFactory(new MapValueFactory(new String("colRefresh")));
//        columnSent.setCellValueFactory(new MapValueFactory(new String("colSent")));
//        columnNo.setCellValueFactory(new PropertyValueFactory("IDp"));
//        columnCategory.setCellValueFactory(new PropertyValueFactory("titlep"));
//        columnProgress.setCellValueFactory(new MapValueFactory(new String("colProgress")));
//        columnRefresh.setCellValueFactory(new MapValueFactory(new String("colRefresh")));
//        columnSent.setCellValueFactory(new MapValueFactory(new String("colSent")));
//    TableColumn<Competition, Integer> firstNameCol = new TableColumn<>("First Name");
//    firstNameCol.setCellValueFactory(new PropertyValueFactory<Competition, Integer>("IDp"));
//    TableColumn<Competition, String> lastNameCol = new TableColumn<>("Last Name");
//    lastNameCol.setCellValueFactory(new PropertyValueFactory<Competition, String>("titlep"));
//    table.getColumns().setAll(firstNameCol, lastNameCol);
//     TableColumn<InvoiceEntry, Integer> firstNameCol = new TableColumn<>("First Name");
// //   firstNameCol.setCellValueFactory(c->c.getValue().IDpProperty());
//    TableColumn<InvoiceEntry, String> lastNameCol = new TableColumn<>("Last Name");
        //lastNameCol.setCellValueFactory(c->c.getValue().titlepProperty());
//       firstNameCol.setCellValueFactory(new PropertyValueFactory<>("idp"));
//        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("titlep"));
//firstNameCol.setCellValueFactory(
//    new PropertyValueFactory<InvoiceEntry, Integer>("itemId")
//);
//lastNameCol.setCellValueFactory(
//    new PropertyValueFactory<InvoiceEntry,String>("itemName")
//);
//
//table.getColumns().setAll(firstNameCol, lastNameCol);
        //   fillInTable();
    }

    public void fillInTable() {
           ArrayList<Competition> competitions = CurrentTournament.getTournamentCompetitions(0);
           for(Competition c : competitions)
            c.initProperties();
//        
////        Map<String, String> dataRow = new HashMap<>();
////        ObservableList<Map> allData = table.getItems();
//        for(Competition c : competitions){
//            c.initProperties();
////            dataRow.put("colNo", c.getID().toString());
////            dataRow.put("colCategory", c.getTitle());
////            dataRow.put("colProgress", c.getPlayedMatchesAmount()+"/"+c.getTotalMatchesAmount());
////            dataRow.put("colRefresh", "refresh");
////            dataRow.put("colSent", "is sent");
////            allData.add(dataRow);
//System.out.println("c"+ c.getTitle());
//System.out.println("cp"+ c.getTitlep());
//System.out.println("");
//        }
//        

        dataArray.addAll(competitions);

        //binding list to table
//        dataArray = FXCollections.observableArrayList();
//        InvoiceEntry i = new InvoiceEntry();
//        i.setFirstName("aa");
//        i.setLastName("bbb");
//        dataArray.add(i);
//
//        InvoiceEntry i2 = new InvoiceEntry();
//        i2.setFirstName("2aa");
//        i2.setLastName("2bbb");
//        dataArray.add(i2);
//        
        
       

   //     dataArray.add(new InvoiceEntry());

        //   table.setItems(dataArray);
//     
//        table.refresh();
//        
//        Competition c1 = new Competition();
//
//        System.out.println(c1.getIdp());
//        System.out.println(c1.getTitlep());

//        list.add(new Competition());
//        list.add(new Competition());
//        list.add(new Competition());
//        list.add(new Competition());
        //list.add(c1);
        //  list.addAll(competitions);
    }

    @FXML
    private void handleDownloadButtonAction() {
        System.out.println("donwloading...");

        fillInTable();
    }

    
    @FXML
    private void onRowChosen(Competition c){
        descrBox.getChildren().clear();
        
        Text header = new Text("Description\n");
        Text descr = new Text(c.getDescr());
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
