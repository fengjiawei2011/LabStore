package cmpe282.lab.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cmpe282.lab.bean.User;
import cmpe282.lab.dao.UserDao;
import cmpe282.lab.database.AmazonStoreSchema;
import cmpe282.lab.database.MySQL;

public class UserDaoImpl implements UserDao {
	

	@Override
	public User findUser(String lastname, String firstname, String email,
			String password) {
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			ps = con.prepareStatement("select * from "+AmazonStoreSchema.TABLE_USER+" where last_name = ? and first_name = ? and email = ? and password = ?");
			ps.setString(1, lastname);
			ps.setString(2, firstname);
			ps.setString(3, email);
			ps.setString(4, password);
			rs = ps.executeQuery();
			
			while(rs.next()){
				user = new User();
				user.setUser_id(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_login_time(rs.getString("last_login_time"));
				user.setLast_name(rs.getString("last_name"));
				user.setLogin_status(rs.getString("login_status"));
				user.setPassword(rs.getString("password"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MySQL.closeAllConnection(rs,ps,con);
		}
		
		return user;
	}

	@Override
	public int insertUser(User user) {
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into "+AmazonStoreSchema.TABLE_USER+"(first_name, last_name, email, password ) values(?,?,?,?)");
			ps.setString(1, user.getFirst_name() );
			ps.setString(2, user.getLast_name());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally{
			MySQL.closeAllConnection(null,ps,con);
		}
		return 1;
	}
	


	@Override
	public int updateLoginStatus(int user_id, String status) {
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		Statement s = null;
		try {
			s = con.createStatement();
			s.execute("update "+AmazonStoreSchema.TABLE_USER+" set login_status = '"+status+"' where user_id="+user_id);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally{
			MySQL.closeAllConnection(null,s,con);
		}
		return 1;
	}
	
	
	
	public static void main(String[] arg0){
		UserDaoImpl uerdao = new UserDaoImpl();
		if(uerdao.findUser("feng", "jiawei", "fengjiawei2011@gmail.com", "12345678") != null){
			System.out.println("legal user");
		}else{
			System.out.println("illegal user!");
		}
		
		
		User user = new User();
		user.setEmail("111");
		user.setFirst_name("frank");
		user.setLast_name("feng");
		user.setPassword("111");
		
		if(uerdao.insertUser(user) == 1){
			System.out.println("successful");
			
		}else{
			System.out.println("error");
		}
		
		if(uerdao.updateLoginStatus(1, "1") ==1 ){
			System.out.println("succ");
			
		}else{
			System.out.println("error");
		}
	}
	
	

}
