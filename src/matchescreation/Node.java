/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation;

/**
 *
 * @author Emilia
 */
public class Node {

        public Node(){
            this.athleteID = -1;
        }
        public Node(Integer id){
            this.athleteID = id;
        }
        public Node(Person p){
            this.athlete = p;
        }
        Node aka;
        Node shiro;
        Node parent;
        
        Integer athleteID;
        Person athlete = null; //athlete data / winner from match
        Integer scoreAka = null;
        Integer scoreShiro = null;
        
        Integer chartLvl;

        @Override
        public String toString() {
            //String ret = new String("");
            String a = (this.aka   == null || this.aka.athlete == null ? " " : this.aka.athlete.toString());
            String b = (this.shiro == null || this.shiro.athlete == null ? " " : this.shiro.athlete.toString());
            
            if(this.scoreAka!=null) {
                a = a + " " + this.scoreAka;
                b = this.scoreShiro + " " + b;
            }
            
            return a+" - "+b; //To change body of generated methods, choose Tools | Templates.
        }
        
        public Node hasOneAthlete(){
            if(!(aka==null||aka.athlete==null||shiro==null||shiro.athlete==null))
                return null;
            if(aka!=null&&aka.athlete!=null)
                return aka;
            else return shiro;
        }
        
        public boolean hasTwoAthletes(){
            return !(this.aka == null || this.aka.athlete == null)
                    && !(this.shiro == null || this.shiro.athlete == null);
        }

    public Node getAka() {
        return aka;
    }
//
//    public void setAka(Node aka) {
//        this.aka = aka;
//    }

    public Node getShiro() {
        return shiro;
    }

//    public void setShiro(Node shiro) {
//        this.shiro = shiro;
//    }

    public Node getParent() {
        return parent;
    }

//    public void setParent(Node parent) {
//        this.parent = parent;
//    }

    public Person getAthlete() {
        return athlete;
    }

    public void setAthlete(Person athlete) {
        this.athlete = athlete;
    }

    public Integer getScoreAka() {
        return scoreAka;
    }

    public void setScoreAka(Integer scoreAka) {
        this.scoreAka = scoreAka;
    }

    public Integer getScoreShiro() {
        return scoreShiro;
    }

    public void setScoreShiro(Integer scoreShiro) {
        this.scoreShiro = scoreShiro;
    }

    public Integer getChartLvl() {
        return chartLvl;
    }

//    public void setChartLvl(Integer chartLvl) {
//        this.chartLvl = chartLvl;
//    }
        
        
    
    
}
