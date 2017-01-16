
package serializable.model;

import java.io.Serializable;

/**
 *
 * @author Emilia
 */
public class Message implements Serializable{
       
    private Topic topic;  
    private Serializable object;
    
    public Message(){
        object = null;
    }
    
    
    /*create questions*/
    
    public void askForTournament(){
        topic = Topic.AskForTournament;
    }
    
    public void sendCompetition(Competition c){
        topic = Topic.SendCompetition;
        object = c;
    }
    
    
    /*create answers*/
    
    public void sendTournament(Tournament t){
        topic = Topic.SendTournament;
        object = t;
    }
     
    public void setObject(Serializable o){
        object = o;
    }
    
    
    /*general methods*/
    
    public Serializable getObject(){
        return object;
    }
    
    public Topic getTopic(){
        return topic;
    }
    
    
    
    public static enum Topic {
        AskForTournament,
        SendTournament,
        SendCompetition
    }
    
}
