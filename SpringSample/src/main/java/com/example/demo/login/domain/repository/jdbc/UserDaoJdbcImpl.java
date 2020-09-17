package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbc;

	//Userテーブルの件数
	@Override
	public int count() throws DataAccessException {
		int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user",
				Integer.class);
		return count;
	}

	//1件insert
	@Override
	public int insertOne(User user) throws DataAccessException {
		int rowNumber = jdbc.update("INSERT INTO m_user(user_id,"
				+ " password," + " user_name," + " birthday,"
				+ " age," + " marriage," + " role)" + " VALUES(?,?,?,?,?,?,?)"
				, user.getUserId(), user.getPassword(), user.getUserName(),
				user.getBirthday(), user.getAge(), user.isMarriage(), user.getRole());
		return rowNumber;
	}

	@Override
	public User selectOne(String userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user" +
				" WHERE user_id = ?", userId);
		User user = new User();
		user.setUserId((String)map.get("user_id"));
		user.setUserName((String) map.get("user_name"));
		user.setPassword((String) map.get("password"));
		user.setBirthday((Date) map.get("birthday"));
		user.setAge((Integer) map.get("age"));
		user.setMarriage((Boolean) map.get("marriage"));
		user.setRole((String) map.get("role"));
		return user;
	}

	//全データを取得
	@Override
	public List<User> selectMany() throws DataAccessException {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");
		List<User> userList = new ArrayList<>();
		for(Map<String, Object> map : getList) {
			User user = new User();
			user.setUserId((String)map.get("user_id"));
			user.setPassword((String)map.get("password"));
			user.setUserName((String)map.get("user_name"));
			user.setBirthday((Date)map.get("birthday"));
			user.setAge((Integer)map.get("age"));
			user.setMarriage((Boolean)map.get("marriage"));
			user.setRole((String)map.get("role"));
			userList.add(user);
		}
		return userList;
	}

	//userテーブル1件を更新
	@Override
	public int updateOne(User user) throws DataAccessException {
		int rowNumber = jdbc.update("UPDATE m_user" + " SET" + " password = ?,"
				+ " user_name = ?," + " birthday = ?," + " age = ?," + " marriage = ?"
				+ " WHERE user_id = ?" , user.getPassword(), user.getUserName(),
				user.getBirthday(), user.getAge(), user.isMarriage(), user.getUserId());
		//トランザクション確認のためわざとエラー
		//if (rowNumber > 0) {
			//throw new DataAccessException("トランザクションテスト") {};
		//}
		return rowNumber;
	}

	//userテーブル1件削除
	@Override
	public int deleteOne(String userId) throws DataAccessException {
		int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);
		return rowNumber;
	}

	//SQL取得結果をサーバーにCSVで保存する
	@Override
	public void userCsvOut() throws DataAccessException {
		String sql = "SELECT * FROM m_user";
		UserRowCallbackHandler handler = new UserRowCallbackHandler();
		jdbc.query(sql, handler);
	}
}
