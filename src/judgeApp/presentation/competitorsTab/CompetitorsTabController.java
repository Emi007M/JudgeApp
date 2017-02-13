package judgeApp.presentation.competitorsTab;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import judgeApp.model.CurrentTournament;
import serializable.model.Person;

/**
 *
 * @author Emilia
 */
public class CompetitorsTabController implements Initializable {

    @FXML
    private BorderPane rootPane;
    @FXML
    private ListView list;

    private ObservableList<String> items;
    private ArrayList<Boolean> presence;

    @FXML
    private Button sendBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.setPadding(new Insets(0, 10, 0, 10));

        items = FXCollections.observableArrayList();
        list.setItems(items);

        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                    //Use ListView's getSelected Item
                    int index = list.getSelectionModel().getSelectedIndex();

                    presence.set(index, !presence.get(index));
                    System.out.println(".handle() " + index);
                    updateRow(index);
                }
            }
        });

    }

    public void init() {
        items.clear();
        presence = new ArrayList<>();
        sendBtn.setVisible(false);

        if (!CurrentTournament.isCurrentCompetitionSet()) {
            return;
        }

        for (Person p : CurrentTournament.getCurrentCompetition().getCompetitors()) {
            items.add("✔ " + p.toString());
            presence.add(true);
        }

    }

    private void updateRow(int i) {
        String tmp = items.get(i);
        String sign = "✔";
        if (!presence.get(i)) {
            sign = "✘";
        }
        tmp = sign + tmp.substring(1);
        items.set(i, tmp);

        this.updateSendBtn();
    }

    private void updateSendBtn() {
        for (Boolean b : presence) {
            if (!b) {
                sendBtn.setVisible(true);
                return;
            }
        }
        sendBtn.setVisible(false);
    }

    private void handleSendBtn() {
        //TODO send list of absent to admin
    }

}
