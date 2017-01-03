/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 *
 * @author Emilia
 */
public class Tournament implements Serializable{
    
    private String title;
    private Date date;
    private ArrayList<Competition> competitions;
    
    public Tournament(String title, Date date){
        this.title = title;
        this.date = date;
        competitions = new ArrayList<>();
    }
    
    //do testów
    public void init(){
        
        //first starting list
        ArrayList<Person> list1 = new ArrayList<>();
        list1.add(new Person("Karolina", "Pukiel", 'f', 1994, 4, "AKK"));
        list1.add(new Person("Janina", "Przybylska", 'f', 1995, 4, "KK44"));
        list1.add(new Person("Anna", "Kądziela", 'f', 1995, 5, "Sakura"));
        list1.add(new Person("Hanna", "Grobowiec", 'f', 1995, 3, "KK44"));
        list1.add(new Person("Anna", "Pyk", 'f', 1994, 5, "AKK"));
        list1.add(new Person("Natalia", "Wartkowicz", 'f', 1996, 6, "Dragosfera"));
        list1.add(new Person("Katarzyna", "Napieralska", 'f', 1996, 3, "Kachi"));       
        
        Competition c1 = new Competition();
        c1.setTitle("Kata Ind. Kobiet 1994-1999 6-4 kyu");
        c1.setDescr("system chorągiewkowy, bez repasaży, dwa 3cie miejsca. Kata dostosowane do pasa");
        c1.setID(1);
        c1.setBoardID(0);
        
        c1.setTwoThirdPlaces(true);
        c1.setPlayers(list1);
        
        ArrayList<Person> pr1 = new ArrayList<>();
        pr1.add(list1.get(0));
        pr1.add(list1.get(1));
        pr1.add(list1.get(2));
        pr1.add(list1.get(3));
        c1.setPreRankedPlayers(pr1);
        
        c1.initChart();
        
        c1.setLocked(true);
        
        //first starting list
        ArrayList<Person> list2 = new ArrayList<>();
        list2.add(new Person("Adam","Kowalski",'m',2000,7,"KS"));
        list2.add(new Person("Edward","Rogal",'m',2001,6, "ŁKKT"));
        list2.add(new Person("Marian","Nowak",'m',2000,7,"KK"));
        list2.add(new Person("Marek","Koper",'m',2002,7,"KS"));
        list2.add(new Person("Wojciech","Maj",'m',2000,6,"AKK"));
        list2.add(new Person("Adam","Wodny",'m',2001,6,"AKK"));
        list2.add(new Person("Tomasz","Kowalski",'m',2001,7,"Kumade"));
        list2.add(new Person("Tomasz","Wilk",'m',2000,7,"Kumade"));
        
        list2.add(new Person("Marian","Rokita",'m',2002,7,"Kumade"));
        list2.add(new Person("Łukasz","Buc",'m',2002,6,"KK44"));
        list2.add(new Person("Tomasz","Kowalski",'m',2001,7,"Kumade"));
        list2.add(new Person("Tomasz","Wilk",'m',2000,7,"Kumade"));
        list2.add(new Person("Marek","Koper",'m',2002,7,"KS"));
        list2.add(new Person("Wojciech","Maj",'m',2000,6,"AKK"));   
        
        Competition c2 = new Competition();
        c2.setTitle("Kata Ind. Mężczyzn 1994-1999 6-4 kyu");
        c2.setDescr("system chorągiewkowy, bez repasaży, dwa 3cie miejsca. Kata dostosowane do pasa");
        c2.setID(2);
        c2.setBoardID(0);
        
        c2.setTwoThirdPlaces(true);
        c2.setPlayers(list2);
        
        ArrayList<Person> pr2 = new ArrayList<>();
        pr2.add(list2.get(0));
        pr2.add(list2.get(1));
        pr2.add(list2.get(2));
        pr2.add(list2.get(3));
        c2.setPreRankedPlayers(pr2);
        
        c2.initChart();
        
        c2.setLocked(true);
        
        
        ////
        competitions.add(c1);
        competitions.add(c2);
        
        //save competitions to folders according to the boards
        competitions.forEach((c) -> {
            Serializator.writeToFile(c, c.getBoardID().toString()+"/"+c.getID().toString());
        });
        
        
    }
    
    public void setCompetitions(ArrayList<Competition> competitions) {
        this.competitions = competitions;
    }
    
    public void addCompetition(Competition c){
        competitions.add(c);
    }
    
    public ArrayList<Competition> getNotStartedCompetitions(){
        return (ArrayList)competitions.stream()
                .filter(c-> c.isNotStarted())
                .collect(Collectors.toList());
    }
    
    public ArrayList<Competition> getNotFinishedCompetitions(){
        return (ArrayList)competitions.stream()
                .filter(c-> !c.isFinished())
                .collect(Collectors.toList());
    }
    
    public ArrayList<Competition> getFinishedCompetitions(){
        return (ArrayList)competitions.stream()
                .filter(c-> c.isFinished())
                .collect(Collectors.toList());
    }
    
    public ArrayList<Competition> getCompetitionsForBoard(Integer boardId){
        return (ArrayList)competitions.stream()
                .filter((Competition c)-> c.getBoardID().equals(boardId))
                .collect(Collectors.toList());
    }
    
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    
    
    
}
