/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author Emilia
 */
public class Chart {

       
    class Node {
        public Node(){
            this.athleteID = -1;
        }
        public Node(Integer id){
            this.athleteID = id;
        }
        Node aka;
        Node shiro;
        Node parent;
        
        Integer athleteID;
        Integer chartLvl;

        @Override
        public String toString() {
            //String ret = new String("");
            String a = (this.aka   == null || this.aka.athleteID == -1 ? " " : Integer.toString(this.aka.athleteID));
            String b = (this.shiro == null || this.shiro.athleteID==-1 ? " " : Integer.toString(this.shiro.athleteID));
            
            return a+"-"+b; //To change body of generated methods, choose Tools | Templates.
        }
        
        public Node hasOneAthlete(){
            if(!(aka==null||aka.athleteID==-1||shiro==null||shiro.athleteID==-1))
                return null;
            if(aka!=null&&aka.athleteID!=-1)
                return aka;
            else return shiro;
        }
        
    }
    
    Node winner;
    LinkedList<Node> matches;
    
    public Chart(){
        this(8);
    }
    public Chart(int x){
        ArrayList <Node> athletes = new ArrayList<>();
        int amount = x;
        for(int i=1; i<= amount; i++)
            athletes.add(new Node(i));
        
        //list should be already mixed with priviliged athletes on top positions
        InitializeMatches(athletes);
    }
    
    private void InitializeMatches(ArrayList<Node> athletes){
        double log = log(athletes.size(),2);
        int height = (int) Math.ceil(log); // deepest lvl of the tree (2^height >= athletes' amount)
        System.out.println(athletes.size());
        System.out.println(log);
        System.out.println(height);
        
        int firstDepthMatches = (int) Math.pow(2,(height-1)); //for 5-8 athletes there are 4 matches in a first row
        int roundedAthletes = (int) Math.pow(2,(height));    //for 9-16 athletes rounded value is 16
        
        if(roundedAthletes > athletes.size()){ //add empty players to the size of the power of 2
            int emptys = roundedAthletes-athletes.size();
            for(int i=0;i<emptys;i++)
                athletes.add(new Node());
        }
        
        
        
        //create not ordered first matches
        ArrayList<Node> firstMatches = new ArrayList<>();
        for(int i =0;i<firstDepthMatches;i++){
            Node match = new Node();
            match.aka = athletes.get(i);
            match.shiro = athletes.get(roundedAthletes-i-1);
            match.chartLvl = height-1;
            firstMatches.add(match);    
        }
        
        //order matches
        firstMatches = orderMatches(firstMatches);
        this.matches = new LinkedList<>();
        this.matches.addAll(firstMatches);
      
        
        

        
        
        for(int i=0;i<firstDepthMatches;i++)
            System.out.println(firstMatches.get(i).toString());
        
        
        //create the rest of a chart
        this.matches.addAll(generateWholeChart(firstMatches));
        this.winner = this.matches.getLast();
        
        //fulfill empty matches
        winEmptyMatches();
        
        
        
        System.out.println(this.toString());
        
    }
    
    
    
    private ArrayList<Node> orderMatches(ArrayList<Node> matches) {
        
        if(matches.size() == 2) return matches;
        
        ArrayList<Node> a = new ArrayList<>();
        ArrayList<Node> b = new ArrayList<>();
        
        for(int i=0; i < matches.size();i+=2){
            a.add(matches.get(i));
            b.add(matches.get(i+1));
        }
        
        //java 8 stream join two subarrays into one
        return Stream.concat(orderMatches(a).stream(), orderMatches(b).stream()).collect(Collectors.toCollection(ArrayList::new));
        
    }
    
    /**
     * Recursively generates consecutive depths of a tree chart
     * @param matches previous depth matches
     * @return complete higher part of a tree
     */
    private Collection<? extends Node> generateWholeChart(ArrayList<Node> matches) {
        ArrayList<Node> nextMatches = new ArrayList<>();
        
        for(int i=0; i < matches.size();i+=2){
            Node match = new Node();
            match.aka   = matches.get(i);
            match.shiro = matches.get(i+1);
            match.chartLvl = matches.get(i).chartLvl-1;
            nextMatches.add(match);
            
            matches.get(i).parent   = match;
            matches.get(i+1).parent = match;
        }
        if(nextMatches.get(0).chartLvl>0)
            nextMatches.addAll(generateWholeChart(nextMatches));
        
        return nextMatches;
    }
    
    
    private void winEmptyMatches() {
        if(this.matches.isEmpty()) return;
        
        int size = (int) Math.pow(2, this.matches.get(0).chartLvl);
        for(int i=0; i<size; i++){
            if(this.matches.get(i).hasOneAthlete()!=null)
                this.matches.get(i).athleteID = this.matches.get(i).hasOneAthlete().athleteID;
        }
    }
    
    
    
    
    static double log(int x, int base){
        return (Math.log(x) / Math.log(base));
    }

    @Override
    public String toString() {
        String ret = "";
        if(matches.isEmpty()) return ret;
        
        int lvl = matches.get(0).chartLvl;
        
        for(int i=0;i<matches.size();i++){
            if(matches.get(i).chartLvl<lvl){
                lvl--;
                ret+="---\n";
            }
            ret+= matches.get(i).toString()+"\n";
            
            
        }
        return ret;
    }
    
    public String getLvl(int lvl){
        String ret = "";
        for(int i=0;i<matches.size();i++){
            if(matches.get(i).chartLvl==lvl)
                ret+= matches.get(i).toString()+"\n";
            else if(matches.get(i).chartLvl<lvl)  
                return ret;
        }
        
        return ret;
    }
    
    public Integer getMaxLvl(){
        if(matches.isEmpty()) return null;
        return matches.get(0).chartLvl;
    }
    
    
    
    
}
