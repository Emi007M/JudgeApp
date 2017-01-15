
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializable.model;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Emilia
 */
public class Competition implements Serializable {

    private static final long serialVersionUID = -4556193073366416374L;

    private Integer ID;
    private transient SimpleIntegerProperty idp;
    private String title;
    private transient SimpleStringProperty titlep;
    private String descr;

    private transient SimpleStringProperty progress;
    private transient SimpleIntegerProperty contestants;

    private transient SimpleStringProperty sent;
    private boolean isSent;

    //private Athletes athletes;
    private ArrayList<Person> competitors;
 //   private ArrayList<Person> prerankedCompetitors;
    boolean twoThirdPlaces;

    private Chart chart;

    private boolean locked; //if locked, any changes to the chart will not affect positions of players
    private boolean notStarted;
    private boolean finished;

    private int boardID;

    public Competition() {
        title = "Default title";
        descr = "Default description on the competition";
        titlep = new SimpleStringProperty("Default title");

        progress = new SimpleStringProperty("0/0");
        // updateProgress();
        contestants = new SimpleIntegerProperty(0);

        sent = new SimpleStringProperty();
        setSent(false);
        isSent = false;

        competitors = new ArrayList<>();
     //   prerankedCompetitors = new ArrayList<>();
        twoThirdPlaces = true;

        ID = -1;
        idp = new SimpleIntegerProperty(-1);
        boardID = -1;

        locked = false;
        notStarted = true;
        finished = false;

    }

    public void initProperties() {
        titlep = new SimpleStringProperty();
        titlep.setValue(title);
        idp = new SimpleIntegerProperty();
        idp.setValue(ID);

        progress = new SimpleStringProperty("xx/yy");
        updateProgress();

        contestants = new SimpleIntegerProperty(0);
        updateContestants();

        sent = new SimpleStringProperty();
        setSent(false);
    }

//    public String getTitlep(){
//        return titlep.getValue();
//    }
    public Integer getIdp() {
        return idp.get();
    }

    public void setIdp(Integer v) {
        idp.set(v);
    }

    public IntegerProperty idpProperty() {
        return idp;
    }

    public String getTitlep() {
        return titlep.get();
    }

    public void setTitlep(String v) {
        titlep.set(v);
    }

    public StringProperty titlepProperty() {
        return titlep;
    }

    public String getProgress() {
        return progress.get();
    }

    public void setProgress(String v) {
        progress.set(v);
    }

    public StringProperty progressProperty() {
        return progress;
    }

    //used by TournamentTabController
    public void updateProgress() {
        try {
            if (chart != null) {
                setProgress(chart.getPlayedMatchesAmount() + "/"
                        + chart.getTotalMatchesAmount());
            }
        } catch (NullPointerException e) {
            setProgress("null");
        }
    }

    public Integer getContestants() {
        return contestants.get();
    }

    public void setContestants(Integer v) {
        contestants.set(v);
    }

    public IntegerProperty contestantsProperty() {
        return contestants;
    }

    public void updateContestants() {
        System.out.println("updating contestants");
        setContestants(competitors.size());
    }

    public String getSent() {
        return sent.get();
    }

    public void setSent(String v) {
        sent.set(v);
    }

    public StringProperty sentProperty() {
        return sent;
    }

    public void setSent(Boolean v) {
        if (v) {
            sent.set("v");
        } else {
            sent.set("x");
        }
    }

    public boolean addPlayer(Person p) {
        if (!notStarted) {
            return false;
        }
        if (!competitors.contains(p)) {
            competitors.add(p);
            System.out.println("player added");
            updateContestants();
            return true;
        }
        return false;
    }

    public void addPlayers(ArrayList<Person> list) {
        if (!notStarted) {
            return ;
        }

        int i = 0;
        for (Person p : list) {
//            if (!competitors.contains(p)) {
//                competitors.add(p);
//                i++;
//            }
            if (competitors.indexOf(p)==-1) {
                competitors.add(p);
                i++;
            }
        }
        System.out.println("added " + i + " players");
        updateContestants();
        
        System.out.println("serializable.model.Competition.addPlayers() finished succesfully");
    }

    public boolean removePlayer(Person p) {
        if (!notStarted) {
            return false;
        }
        if (competitors.contains(p)) {
            competitors.remove(p);
            System.out.println("player removed");
            updateContestants();
            return true;
        }
        return false;
    }

    public boolean setPlayers(ArrayList<Person> list) {
        competitors = list;
        updateContestants();
        return true;
    }

//    public void setPreRankedPlayers(ArrayList<Person> list) {
//        prerankedCompetitors = list;
//    }

    public boolean isTwoThirdPlaces() {
        return twoThirdPlaces;
    }

    public void setTwoThirdPlaces(boolean is) {
        twoThirdPlaces = is;
    }

    /**
     * generates new chart with the use of previously given properties
     */
    public void initChart() {
       // int preranked = prerankedCompetitors.size();

//        for (Person p : prerankedCompetitors) { //TODO gadza się tylko jeśli wszyscy podani jako preranked mają być wpisani
//            if (competitors.contains(p)) {
//                competitors.remove(p);
//            }
//        }
//        ArrayList all = new ArrayList(prerankedCompetitors);
//        all.addAll(competitors);

        if(!isLocked()){
            chart = new Chart(competitors, twoThirdPlaces);
        }
        else {
            //todo
        }
        

        progress = new SimpleStringProperty();
        setProgress("chart done, 0/0");
    }

    public Integer getPlayersAmount() {//TODO  zgadza się tylko jeśli wszyscy preranked należą do competitors 
        return competitors.size();
    }

    public int getTotalMatchesAmount() {
        return chart.getTotalMatchesAmount();
    }

    public int getPlayedMatchesAmount() {
        return chart.getPlayedMatchesAmount();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

//    public Athletes getAthletes() {
//        return athletes;
//    }
//
//    public void setAthletes(Athletes athletes) {
//        this.athletes = athletes;
//    }
    public ArrayList<Person> getCompetitors() {
        return competitors;
    }

    public void setCompetitors(ArrayList<Person> competitors) {
        this.competitors = competitors;
    }

//    public ArrayList<Person> getPrerankedCompetitors() {
//        return prerankedCompetitors;
//    }
//
//    public void setPrerankedCompetitors(ArrayList<Person> prerankedCompetitors) {
//        this.prerankedCompetitors = prerankedCompetitors;
//    }

    public Chart getChart() {
        if(chart==null)
            initChart();
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isNotStarted() {
        return notStarted;
    }

    public void setNotStarted(boolean notStarted) {
        this.notStarted = notStarted;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Integer getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    private ArrayList<String> results;

    public ArrayList<String> getResults() {
        return results;
    }

    public void setResults() {
        results = new ArrayList<>();
        results.add("1. " + chart.winner.getWinner().getAthlete().toString());
        results.add("2. " + chart.winner.getLooser().getAthlete().toString());

        if (isTwoThirdPlaces()) {
            results.add("3. " + chart.winner.getWinner().getLooser().getAthlete().toString());
            results.add("3. " + chart.winner.getLooser().getLooser().getAthlete().toString());
        } else { //to nie jest tak -.- dodatkowy mecz wtedy powinien być
            results.add("3. " + chart.winner.getWinner().getLooser().getAthlete().toString());
            results.add("4. " + chart.winner.getLooser().getLooser().getAthlete().toString());

        }

    }

    public Boolean hasResults() {
        return results != null;
    }

}
