package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 10/14/15.
 */
@Cacheable
@NamedQueries({
        @NamedQuery(name = "com.cs6310.backend.model.Semester.getAll", query = "select obj from Semester obj"),
        @NamedQuery(name = "com.cs6310.backend.model.Semester.getByUUID", query = "select obj from Semester obj where obj.uuid = :uuid"),
        @NamedQuery(name = "com.cs6310.backend.model.Semester.getByName", query = "select obj from Semester obj where obj.name = :name")
})
@Entity
@Table(name = "semester")
public class Semester implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column(unique = true)
    private String uuid;

    @Expose
    @Column(unique = true)
    private String name;

    @Expose
    private String year;


    @ManyToMany(targetEntity = com.cs6310.backend.model.Course.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "offeredInSemester")
    private List<Course> courses = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String itemId) {
        this.uuid = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

