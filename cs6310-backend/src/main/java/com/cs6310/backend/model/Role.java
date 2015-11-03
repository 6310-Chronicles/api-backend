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
        @NamedQuery(name = "Role.getAll", query = "select obj from Role obj"),
        @NamedQuery(name = "Role.getByUUID", query = "select obj from Role obj where obj.uuid = :uuid"),
        @NamedQuery(name = "Role.getByName", query = "select obj from Role obj where obj.name = :name")
})
@Entity
public class Role implements Serializable {


    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue
    private int id;

    @Expose
    @Column(unique = true)
    private String uuid;

    @Expose
    @Column(unique = true)
    private String name;

    @ManyToMany(targetEntity = Administrator.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Administrator> administrator;


    @ManyToMany(targetEntity=Privilege.class, cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
    private List<Privilege> privileges;

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Role() {
    }

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

    public List<Administrator> getAdministrator() {
        return administrator;
    }

    public void setAdministrator(List<Administrator> administrator) {
        this.administrator = administrator;
    }
}
