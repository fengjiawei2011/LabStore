package cmpe282.lab.dao;

import java.util.List;

import cmpe282.lab.bean.Product;

public interface ProductDao {
	public int insertProduct(Product p);
	public int insertProductCatalog(String type);
	public List<Product> findAllProduct();
	public List<Product> findProductByUser(int user_id); 
	public List<Product> findProductByCatalog(int catalog_id);
	public int updateProductQuantity(int pid, int quantity);
	
	
	public int deleteProductFromShoppingCart(int product_id, int user_id);
	public List<Product> findProductFromShoppingCart(int user_id);
	public int insertProductsIntoShoppingCart(int buyer_id ,Product p);
	public int getProducts_num(int user_id);
	public int doesProductExist(int uid, int pid);
	public int updateProductNumInSC(int uid, int pid, int num);
}
