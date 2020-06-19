package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Paper")
public class Paper implements Serializable {
    private Integer id;
    private String name;
    private String teacher1;
    private String teacher2;
    private Double grade1;
    private Double grade2;
    private Double final_grade;
    private String recheck;

    public Paper(){

    }

    public Paper(Integer id, String name, String teacher1, String teacher2) {
        this.id = id;
        this.name = name;
        this.teacher1 = teacher1;
        this.teacher2 = teacher2;
        this.grade1 = 0.0;
        this.grade2 = 0.0;
        this.final_grade = 0.0;
        this.recheck=null;
    }



    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Teacher1")
    public String getTeacher1() {
        return teacher1;
    }

    public void setTeacher1(String teacher1) {
        this.teacher1 = teacher1;
    }

    @Column(name="Teacher2")
    public String getTeacher2() {
        return teacher2;
    }

    public void setTeacher2(String teacher2) {
        this.teacher2 = teacher2;
    }

    @Column(name="Grade1")
    public Double getGrade1() {
        return grade1;
    }

    public void setGrade1(Double grade1) {
        this.grade1 = grade1;
    }

    @Column(name="Grade2")
    public Double getGrade2() {
        return grade2;
    }

    public void setGrade2(Double grade2) {
        this.grade2 = grade2;
    }

    @Column(name="FinalGrade")
    public Double getFinal_grade() {
        return final_grade;
    }

    public void setFinal_grade(Double final_grade) {
        this.final_grade = final_grade;
    }

    @Column(name="Recheck")
    public String getRecheck() {
        return recheck;
    }

    public void setRecheck(String recheck) {
        this.recheck = recheck;
    }
}
