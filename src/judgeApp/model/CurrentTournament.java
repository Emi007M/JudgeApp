package judgeApp.model;

import java.util.ArrayList;
import serializable.model.Competition;
import serializable.model.Tournament;

/**
 *
 * @author Emilia
 */
public final class CurrentTournament {

    private static Tournament tournament = null;
    private static Integer boardID;
    private static Competition currentCompetition;

    private CurrentTournament() {
    }

    public static void setTournament(Tournament t) {
        tournament = t;
        currentCompetition = null;
    }

    public static Tournament getTournament() {
        return tournament;
    }

    public static String getTournamentTitle() {
        if (tournament != null) {
            return tournament.getTitle();
        }
        return "";
    }

    public static ArrayList<Competition> getTournamentCompetitions(Integer boardID) {
        ArrayList<Competition> ret = null;
        ret = tournament.getCompetitionsForBoard(boardID);
        return ret;
    }

    public static ArrayList<Competition> getTournamentCompetitions() {
        return tournament.getCompetitions();
    }

    public static Competition getCompetition(Integer id) {
        for (Competition c : tournament.getCompetitions()) {
            if (c.getID().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public static void setCurrentCompetition(Competition c) {
        //todo save previous progress

        //check whether it belongs to boardID, set boardID somewhere
        currentCompetition = c;
    }

    public static boolean isCurrentCompetitionSet() {
        return currentCompetition != null;
    }

    public static Competition getCurrentCompetition() {
        return currentCompetition;
    }

    public static void setBoardID(Integer i) {
        boardID = i;
        currentCompetition = null;
    }

    public static Integer getBoardID() {
        return boardID;
    }

    /**
     * method for Socket operations probably receives locked or locked and
     * finished competition from Client and not locked from Server
     *
     * @param ID
     * @param c
     */
    public static void updateCompetition(Competition c) {
        Integer ID = c.getID();
        Competition tmp = getCompetition(ID);
        if (tmp.getTitle().equals(c.getTitle())) {

            for (Competition co : tournament.getCompetitions()) {
                if (co.getID() == ID) {
                    co = c;
                    System.out.println("competition updated");
                    System.out.println("winner is " + c.getResults().get(0));
                    return;
                }
            }

        }
        System.out.println("competition couldn't be updated");
    }

}
