package cmpe282.lab.dao;

import cmpe282.lab.bean.User;

public interface UserDao {
	
	public User findUser(String lastname, String firstname, String email, String password);
	public int insertUser(User user);
	public int updateLoginStatus(int user_id,String status);

}
