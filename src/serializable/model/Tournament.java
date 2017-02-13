package serializable.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import serializable.model.data.Serializator;

/**
 *
 * @author Emilia
 */
public class Tournament implements Serializable {

    private static final long serialVersionUID = 4400681424579801084L;

    private String title;
    private LocalDate date;
    private String place;
    private ArrayList<Competition> competitions;

    public Tournament(String title, LocalDate date, String place) {
        this.title = title;
        this.date = date;
        this.place = place;
        competitions = new ArrayList<>();
    }

    public void setCompetitions(ArrayList<Competition> competitions) {
        this.competitions = competitions;
    }

    public void addCompetition(Competition c) {
        competitions.add(c);
    }

    public ArrayList<Competition> getNotStartedCompetitions() {
        return (ArrayList) competitions.stream()
                .filter(c -> c.isNotStarted())
                .collect(Collectors.toList());
    }

    public ArrayList<Competition> getNotFinishedCompetitions() {
        return (ArrayList) competitions.stream()
                .filter(c -> !c.isFinished())
                .collect(Collectors.toList());
    }

    public ArrayList<Competition> getFinishedCompetitions() {
        return (ArrayList) competitions.stream()
                .filter(c -> c.isFinished())
                .collect(Collectors.toList());
    }

    public ArrayList<Competition> getCompetitionsForBoard(Integer boardId) {
        return (ArrayList) competitions.stream()
                .filter((Competition c) -> c.getBoardID().equals(boardId))
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    public void saveToFile() {
        Serializator.writeToFile(this, "tournaments/current/t_" + this.getDate());
    }

}
