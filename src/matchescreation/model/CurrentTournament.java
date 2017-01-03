/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.model;

import java.util.ArrayList;

/**
 *
 * @author Emilia
 */
public final class CurrentTournament {
    
    private static Tournament tournament;
    private static Integer boardID;
    private static Competition currentCompetition;
    
    private CurrentTournament(){}
    
    public static void setTournament(Tournament t){
        tournament = t;
        currentCompetition = null;
    }
    
    
    public static Tournament getTournament(){
        return tournament;
    }
    public static String getTournamentTitle(){
        return tournament.getTitle();
    }
    public static ArrayList<Competition> getTournamentCompetitions(Integer boardID){
        return tournament.getCompetitionsForBoard(boardID);
    }
    
    
    
    public static void setCurrentCompetition(Competition c){
        //todo save previous progress
        
        //check whether it belongs to boardID, set boardID somewhere
        
        currentCompetition = c;
    }
    
    public static boolean isCurrentCompetitionSet(){
        return currentCompetition!=null;
    }
    
    public static Competition getCurrentCompetition(){
        return currentCompetition;
    }
    
    
    public void setBoardID(Integer i){
        boardID = i;
        currentCompetition = null;
    }
    
    public Integer getBoardID(){
        return boardID;
    }


    
    
    
}
