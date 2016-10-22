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
public class Person {
    
    
    
    private Integer id;
    private String name;
    private String surname;
    private Character gender;
    private Integer year;
    private Integer degree; //1kyu=1, 1Dan=-1
    
    public Person(String _name, String _surname, Character _gender, Integer _year, Integer _degree){
        name    = _name;
        surname = _surname;
        gender  = _gender;
        year    = _year;
        degree  = _degree;
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
    
    
    
    
}
