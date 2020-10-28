package client;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Lenovo
 */
public class Package implements Serializable{
    private Student student;
    private String rq;
    private String cmt;
    private String typeSearch;
    private Vector vector;
    public Package() {
    }

    
    public Package(Student student, String rq) {
        this.student = student;
        this.rq = rq;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getRq() {
        return rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public String getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
    }
    
}
