package com.yukiju.repos;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(schema = "zencuisine", name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Must provide a role name")
	@Column(unique = true, nullable = false)
	private String role;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	private Set<User> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}

	public Role(int id, @NotBlank(message = "Must provide a role name") String role) {
		super();
		this.id = id;
		this.role = role;
	}

	public Role(int id, @NotBlank(message = "Must provide a role name") String role, Set<User> users) {
		super();
		this.id = id;
		this.role = role;
		this.users = users;
	}

	public Role() {
		super();
	}

}
