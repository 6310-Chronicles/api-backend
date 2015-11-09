package com.cs6310.backend.model;

import com.google.gson.annotations.Expose;
import org.apache.openjpa.persistence.jdbc.Unique;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Cacheable
@NamedQueries({
		@NamedQuery(name = "com.cs6310.backend.model.Privilege.getAll", query = "select obj from Privilege obj"),
		@NamedQuery(name = "com.cs6310.backend.model.Privilege.getByUUID", query = "select obj from Privilege obj where obj.uuid = :uuid"),
		@NamedQuery(name = "com.cs6310.backend.model.Privilege.getByName", query = "select obj from Privilege obj where obj.name = :name")
})
@Entity
@Table(name = "privilege")
public class Privilege implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	@Expose
	@Column(unique = true, nullable = true)
	private String uuid;

	@Expose
	@Unique
	@Column
	private String name;
	@ManyToMany(targetEntity = com.cs6310.backend.model.Role.class,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "privileges")
	private List<Role> roles = new ArrayList<>();

	public Privilege() {
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
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {

		if (roles == null)
			roles = new ArrayList<>();

		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
