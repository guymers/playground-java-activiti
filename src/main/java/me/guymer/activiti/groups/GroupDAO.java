package me.guymer.activiti.groups;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDAO {

	@Inject
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public Group getGroupById(int id) {
		String sql = "SELECT id, name FROM blah_groups WHERE id = :id";
		Map<String, Integer> paramMap = Collections.singletonMap("id", id);
		
		return jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<Group>(Group.class));
	}

	public List<Group> getGroupsByUserId(int id) {
		String sql = "SELECT blah_groups.id, blah_groups.name FROM blah_groups ";
		sql += "JOIN blah_users_groups ON blah_users_groups.group_id = blah_groups.id ";
		sql += "WHERE blah_users_groups.user_id = :id";
		Map<String, Integer> paramMap = Collections.singletonMap("id", id);
		
		return jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<Group>(Group.class));
	}
}
