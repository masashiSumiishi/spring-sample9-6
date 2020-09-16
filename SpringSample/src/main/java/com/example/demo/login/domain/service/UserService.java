package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao dao;

	//insertメソッド
	public boolean insert(User user) {
		int rowNumber = dao.insertOne(user);
		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	//カウントメソッド
	public int count() {
		return dao.count();
	}

	//全件用取得メソッド
	public List<User> selectMany() {
		return dao.selectMany();
	}

	//1件取得用メソッド
	public User selectOne(String userId) {
		return dao.selectOne(userId);
	}

	//1件更新メソッド
	public boolean updateOne(User user) {
		int rowNumber = dao.updateOne(user);
		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	//1件削除
	public boolean deleteOne(String userId) {
		int rowNumber = dao.deleteOne(userId);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
}
