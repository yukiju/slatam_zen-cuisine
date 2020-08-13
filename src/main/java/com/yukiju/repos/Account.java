package com.yukiju.repos;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(schema = "zencuisine", name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
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
		Account other = (Account) obj;
		if (id != other.id)
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", lastLogin=" + lastLogin + "]";
	}

	public Account(int id, LocalDateTime lastLogin) {
		super();
		this.id = id;
		this.lastLogin = lastLogin;
	}

	public Account(int id, LocalDateTime lastLogin, User user) {
		super();
		this.id = id;
		this.lastLogin = lastLogin;
		this.user = user;
	}

	public Account() {
		super();
	}

}
