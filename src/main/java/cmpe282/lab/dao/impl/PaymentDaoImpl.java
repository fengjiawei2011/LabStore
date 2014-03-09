package cmpe282.lab.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cmpe282.lab.dao.PaymentDao;
import cmpe282.lab.database.AmazonStoreSchema;
import cmpe282.lab.database.MySQL;

public class PaymentDaoImpl implements PaymentDao {

	@Override
	public int insertPaymentRecord( int pid, int uid, String c_num) {
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into "
					+ AmazonStoreSchema.TABLE_PAYMENT
					+ " (product_id,user_id,credit_card_num) value(?,?,?)");
			ps.setInt(1, pid);
			ps.setInt(2, uid);
			ps.setString(3, c_num);
			ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} finally {
			MySQL.closeAllConnection(null, ps, con);
		}
		
		return 1;
	}


}
