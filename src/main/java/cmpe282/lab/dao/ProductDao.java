package cmpe282.lab.dao;

import java.util.List;

import cmpe282.lab.bean.Product;

public interface ProductDao {
	public int insertProduct(Product p);
	public int insertProductCatalog(String type);
	public List<Product> findAllProduct();
	public List<Product> findProductByUser(String user_id); 
	public List<Product> findProductByCatalog(String type);
	public int updateProductQuantity(String pid, int quantity);
	
	
	public int deleteProductFromShoppingCart(String product_id);
	public List<Product> findProductFromShoppingCart(String user_id);
	public int insertProductsIntoShoppingCart(Product p);
}
