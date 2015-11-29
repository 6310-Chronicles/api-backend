package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 11/3/15.
 */
@Cacheable
@NamedQueries({
        @NamedQuery(name = "com.cs6310.backend.model.TeachingAssistant.findByStudent", query = "select obj from TeachingAssistant obj where obj.accessCredential = : accessCredential"),
        @NamedQuery(name = "com.cs6310.backend.model.TeachingAssistant.getAll", query = "select obj from TeachingAssistant obj"),
        @NamedQuery(name = "com.cs6310.backend.model.TeachingAssistant.getByStudentId", query = "select obj from TeachingAssistant obj where obj.student.studentId = :studentId"),
        @NamedQuery(name = "com.cs6310.backend.model.TeachingAssistant.getByUUID", query = "select obj from TeachingAssistant obj where obj.uuid =: uuid")

})
@Entity
@Table(name = "teachingassistant")
public class TeachingAssistant implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;
    @Expose
    @Column(unique = true)
    private String uuid;
    @Expose
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Student student;
    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,})
    private List<Course> competency = new ArrayList<>();
    @Expose
    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE,})
    private List<Course> assisting = new ArrayList<>();


    public TeachingAssistant() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Course> getCompetency() {
        if (competency==null)
            competency =  new ArrayList<>();
        return competency;
    }

    public void setCompetency(List<Course> competency) {
        this.competency = competency;
    }

    public List<Course> getAssisting() {
        if (assisting==null)
            assisting =  new ArrayList<>();
        return assisting;
    }

    public void setAssisting(List<Course> assisting) {
        this.assisting = assisting;
    }
}
