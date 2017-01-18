/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package judgeApp.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import serializable.model.Competition;
import serializable.model.Message;
import serializable.model.Tournament;

/**
 * This class implements java socket client
 *
 * @author pankaj
 *
 */
public class SocketClient {

    private static InetAddress host = null;
    
    private static void setDefaultAddress(){
        
        try {
            host = InetAddress.getByName("10.9.141.124");
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    /**
     *
     * @param address if null, localhost is set
     */
    public static void updateTournamentViaServer(InetAddress address) {

        Message m = new Message();
        m.askForTournament();
        Message answer = askServer(m, address);

        if (answer.getTopic() == Message.Topic.SendTournament) {
            Tournament t = (Tournament) answer.getObject();
            updateTournament(t);
        }

    }

    /**
     *
     * @param address if null, localhost is set
     */
    public static void sendResultsToServer(Competition c, InetAddress address) {
        
        if(host!= null) address=host;
        
        Message m = new Message();
        m.sendCompetition(c);
        Message answer = askServer(m, address);

        if (answer.getTopic() == Message.Topic.SendTournament) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Results sent");
            alert.setHeaderText("Results sent");
            alert.setContentText("Results have been succesfully sent to the server.");
            alert.showAndWait();
            
            Tournament t = (Tournament) answer.getObject();
            updateTournament(t);
        }

    }

    private static void updateTournament(Tournament t) {
        //if null tournament from message
        if (t == null) {
            System.out.println("null tournament from the server.");
            return;
        }
        //if tournament was not set
        if (CurrentTournament.getTournament() == null) {
            CurrentTournament.setTournament(t);

        } else {
            //if tournament was set, update not locked competitions
//        
//        for(Competition c : CurrentTournament.getTournamentCompetitions()){
//            if(!c.isLocked() && !c.isFinished()){
//                Competition toOverride = null;
//                for(Competition tmp : t.getCompetitions()){
//                    if(tmp.getID()==c.getID()){
//                        toOverride = tmp;
//                        break;
//                    }
//                }
//                CurrentTournament.updateCompetition(toOverride);
//                        }

            //  }
            CurrentTournament.setTournament(t);
            System.out.println("tournament updated.");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Data downlooaded");
        alert.setHeaderText("Data succesfully downloaded");
        alert.setContentText("All the data has been succesfully synchronized with the server.");
        alert.showAndWait();
    }

    /**
     *
     * @param query prepared Message for the server
     * @param address if null, localhost is set
     * @return answer from the server in form of Message
     */
    private static Message askServer(Message query, InetAddress address) {
        Message received = null;

        try {
            if (address == null) {
              ///  host = InetAddress.getLocalHost();
                setDefaultAddress();
            } else {
                host = address;
            }

            Socket socket = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;
            //for(int i=0; i<5;i++){
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            // if(i==4)oos.writeObject("exit");
            // else oos.writeObject(""+i);
            oos.writeObject(query);

            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            received = (Message) ois.readObject();
            System.out.println("Message received");

            //close resources
            ois.close();
            oos.close();

        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return received;
    }
}
