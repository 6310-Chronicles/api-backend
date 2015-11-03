package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by nelson on 10/14/15.
 */
@Cacheable
@NamedQueries({
        @NamedQuery(name = "Semester.getAll", query = "select obj from Semester obj"),
        @NamedQuery(name = "Semester.getByUUID", query = "select obj from Semester obj where obj.uuid = :uuid"),
        @NamedQuery(name = "Semester.getByName", query = "select obj from Semester obj where obj.name = :name")
})
@Entity
public class Semester implements Serializable {


    private static final long serialVersionUID = 1L;

    @Expose
    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column(unique = true)
    private String uuid;

    @Expose
    private String name;

    @Expose
    private String year;

    @ManyToMany(targetEntity = Course.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Course> courseList;


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

