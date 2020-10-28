package client;

import java.io.Serializable;


/**
 *
 * @author Lenovo
 */
public class Student implements Serializable{
    private int ID;
    private String name;
    private int yearOfBirth;
    private String placeOfBirth;
    private String classroom;

    public Student() {
    }

    public Student(String name, int yearOfBirth, String placeOfBirth, String classroom) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.classroom = classroom;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
    
}
