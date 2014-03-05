package cmpe282.lab.bean;

import java.io.Serializable;
import java.util.List;

public class ShoppingCart implements Serializable {
	
	private static final long serialVersionUID = 1L;
	int buyer_id;
	List<Product> products;
	int quantity;
	public int getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
