package me.guymer.activiti.users;

import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	@Inject
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public User getUserById(int id) {
		String sql = "SELECT id, first_name, surname, email, password FROM blah_users WHERE id = :id";
		Map<String, Integer> paramMap = Collections.singletonMap("id", id);
		
		return jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<User>(User.class));
	}
}
