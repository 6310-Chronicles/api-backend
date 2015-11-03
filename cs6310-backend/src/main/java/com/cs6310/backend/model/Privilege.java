package com.cs6310.backend.model;

import org.apache.openjpa.persistence.jdbc.Unique;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Cacheable
@NamedQueries({
		@NamedQuery(name = "Privilege.getAll", query = "select obj from Privilege obj"),
		@NamedQuery(name = "Privilege.getByUUID", query = "select obj from Privilege obj where obj.uuid = :uuid"),
		@NamedQuery(name = "Privilege.getByName", query = "select obj from Privilege obj where obj.name = :name")
})
@Entity
public class Privilege implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Privilege() {
	}
	
	@Id
	@GeneratedValue
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(unique=true, nullable=true)
	private String uuid;
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	@Unique
	@Column
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(targetEntity=Role.class,
			fetch=FetchType.LAZY, 
			cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
