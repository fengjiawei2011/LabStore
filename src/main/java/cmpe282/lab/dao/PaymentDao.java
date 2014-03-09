package cmpe282.lab.dao;

import java.util.List;

import cmpe282.lab.bean.Bill;
import cmpe282.lab.bean.Product;

public interface PaymentDao {
	public int insertPaymentRecord( int pid, int uid, String c_num);

}
