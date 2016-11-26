/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchescreation.model;

/**
 *
 * @author Emilia
 */
public class Person {
    
    
    
    private Integer id;
    private String name;
    private String surname;
    private Character gender;
    private Integer year;
    private Integer degree; //1kyu=1, 1Dan=-1
    private String club;
    
    public Person(String _name, String _surname, Character _gender, Integer _year, Integer _degree, String _club){
        name    = _name;
        surname = _surname;
        gender  = _gender;
        year    = _year;
        degree  = _degree;
        club    = _club;
    }
    
    public void setId(Integer _id){
        id = _id;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }
    
    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return name+" "+surname+" ("+club+")";
    }

    public String getFullName() {
        return name+" "+surname;
    }
    
    
    
    
}
