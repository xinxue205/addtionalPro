package jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tt_user")
public class UserDemo {
 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //×ÔÔö³¤
	private int id;
	@Column(name="user_name")
	private String username;
	@Column(name="user_pass")
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}