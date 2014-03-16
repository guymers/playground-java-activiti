package me.guymer.activiti.groups;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import me.guymer.activiti.users.User;

@Data
@Entity
@Table(name = "groups")
public class Group implements Serializable {

	private static final long serialVersionUID = -2265535653947368048L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private int id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "users_groups", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private Set<User> users;

}
