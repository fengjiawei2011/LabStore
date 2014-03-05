package cmpe282.lab.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmpe282.lab.bean.Product;
import cmpe282.lab.dao.ProductDao;
import cmpe282.lab.database.AWSDynamoDB;
import cmpe282.lab.database.AmazonStoreSchema;
import cmpe282.lab.database.MySQL;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class ProductDaoImpl implements ProductDao {
	
	
	
	public ProductDaoImpl() throws Exception{
		AWSDynamoDB.init();
	}
	
	
	public static void main(String[] arg0) throws Exception{
		ProductDaoImpl pd = new ProductDaoImpl();
		Product p = new Product();
		p.setCatalog_id(1);
		p.setOwner_id(2);
		p.setProduct_description("bad");
		p.setProduct_name("apple");
		p.setProduct_price(11.3f);
		p.setProduct_quantity(10);
		p.setCatalog_name("computer");
		p.setProduct_id(2);
		
		//pd.insertProductsIntoShoppingCart(3, p); 
		//pd.findProductFromShoppingCart(1);
		//pd.updateProductQuantity(1,8);
		pd.deleteProductFromShoppingCart(0);

//		if(pd.insertProduct(p) == 1){
//			System.out.println("succ");
//		}else{
//			System.out.println("error");
//		}
//		
//		
//		if(pd.insertProductCatalog("computer") == 1){
//			System.out.println("succ");
//		}else{
//			System.out.println("error");
//		}
//		
//		pd.findAllProduct();
//		pd.findProductByUser(1);
//		pd.findProductByCatalog(1);
		
	}
	
	@Override
	public int insertProduct(Product p) {
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into "+AmazonStoreSchema.TABLE_PRODUCT+"(" + "product_name,"
					+ "product_description," + "product_price,"
					+ "product_quantity," + "catalog_id," + "owner_id ) "
					+ "values(?,?,?,?,?,?)");
			ps.setString(1, p.getProduct_name());
			ps.setString(2, p.getProduct_description());
			ps.setFloat(3, p.getProduct_price());
			ps.setInt(4, p.getProduct_quantity());
			ps.setInt(5, p.getCatalog_id());
			ps.setInt(6, p.getOwner_id());
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

	@Override
	public int insertProductCatalog(String type) {
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("insert into "+AmazonStoreSchema.TABLE_CATALOG+" (catalog_name) value(?)");
			ps.setString(1, type);
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

	@Override
	public List<Product> findAllProduct() {
		List<Product> products = null;
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			ps = con.prepareStatement("select * from "+AmazonStoreSchema.TABLE_PRODUCT);
			rs = ps.executeQuery();
			products = getProducts(rs);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
			return null;
		}finally{
			MySQL.closeAllConnection(rs,ps,con);
		}
		//System.out.println("succ");
		return products;
	}
	
	

	@Override
	public List<Product> findProductByUser(int user_id) {
		List<Product> products = null;
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			ps = con.prepareStatement("select * from "+AmazonStoreSchema.TABLE_PRODUCT + " where owner_id="+user_id);
			rs = ps.executeQuery();
			products = getProducts(rs);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
			return null;
		}finally{
			MySQL.closeAllConnection(rs,ps,con);
		}
		System.out.println("findProductByUser ---- >succ");
		return products;
	}

	@Override
	public List<Product> findProductByCatalog(int catalog_id) {
		List<Product> products = null;
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			ps = con.prepareStatement("select * from "+AmazonStoreSchema.TABLE_PRODUCT + " where catalog_id ="+catalog_id);
			rs = ps.executeQuery();
			products = getProducts(rs);			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
			return null;
		}finally{
			MySQL.closeAllConnection(rs,ps,con);
		}
		System.out.println("findProductByCatalog ---- >succ");
		return products;
	}

	@Override
	public int updateProductQuantity(int pid, int quantity) {
		MySQL mysql = new MySQL();
		Connection con = mysql.connectDatabase();
		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("update product set product_quantity = ? where product_id = ?");
			ps.setInt(1, quantity);
			ps.setInt(2, pid);
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
			return 0;
		}finally{
			MySQL.closeAllConnection(null,ps,con);
		}
		return 1;
	}

	@Override
	public int deleteProductFromShoppingCart(int product_id) {
		HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue> ();
		key.put("product_id", new AttributeValue().withN(Integer.toString(product_id)));
		      	
		DeleteItemRequest deleteItemRequest = new DeleteItemRequest()
		    .withTableName(AWSDynamoDB.table_name)
		    .withKey(key);
		      	
		DeleteItemResult deleteItemResult = AWSDynamoDB.dynamoDB.deleteItem(deleteItemRequest);
		
		System.out.println("delete item succefully");
		return 1;
	}

	@Override
	public List<Product> findProductFromShoppingCart(int user_id) {
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
        Condition condition = new Condition()
            .withComparisonOperator(ComparisonOperator.EQ.toString())
            .withAttributeValueList(new AttributeValue().withN(Integer.toString(user_id)));
        scanFilter.put("buyer_id", condition);
        ScanRequest scanRequest = new ScanRequest(AWSDynamoDB.table_name).withScanFilter(scanFilter);
        ScanResult scanResult = AWSDynamoDB.dynamoDB.scan(scanRequest);
        System.out.println("Result: " + scanResult);
        List<Product> products = new ArrayList<Product>();
        for (Map<String, AttributeValue> item : scanResult.getItems()) {
        	products.add(printItem(item));
        	
        }
        
       
        System.out.println(products.get(0).getProduct_description());
		return products;
	}
	 
	 private static Product printItem(Map<String, AttributeValue> attributeList) {
		 	Product p = new Product();
	        for (Map.Entry<String, AttributeValue> item : attributeList.entrySet()) {
	            String attributeName = item.getKey();
	            AttributeValue value = item.getValue();
	            
	            if(attributeName.equals("catalog_name")) p.setCatalog_name(value.getS());
	            if(attributeName.equals("product_id")) p.setProduct_id(Integer.parseInt(value.getN()));
	            if(attributeName.equals("product_price")) p.setProduct_price(Float.parseFloat(value.getN()));
	            if(attributeName.equals("product_name")) p.setProduct_name(value.getS());
	            if(attributeName.equals("product_quantity")) p.setProduct_quantity(Integer.parseInt(value.getN()));
	            if(attributeName.equals("owner_id")) p.setOwner_id(Integer.parseInt(value.getN()));
	            if(attributeName.equals("product_description")) p.setProduct_description(value.getS());
//	            System.out.println(attributeName + " "
//	                    + (value.getS() == null ? "" : "S=[" + value.getS() + "]")
//	                    + (value.getN() == null ? "" : "N=[" + value.getN() + "]")
//	                    + (value.getB() == null ? "" : "B=[" + value.getB() + "]")
//	                    + (value.getSS() == null ? "" : "SS=[" + value.getSS() + "]")
//	                    + (value.getNS() == null ? "" : "NS=[" + value.getNS() + "]")
//	                    + (value.getBS() == null ? "" : "BS=[" + value.getBS() + "] \n"));
	        }
	        
	        return p;
	    }

	@Override
	public int insertProductsIntoShoppingCart(int buyer_id , Product p) {
		
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("product_id", new AttributeValue().withN(Integer.toString(p.getProduct_id())));
        item.put("product_name", new AttributeValue().withS(p.getProduct_name()));
        item.put("product_description", new AttributeValue().withS(p.getProduct_description()));
        item.put("product_price", new AttributeValue().withN(Float.toString(p.getProduct_price())));
        item.put("product_quantity", new AttributeValue().withN(Integer.toString(p.getProduct_quantity())));
        item.put("catalog_name", new AttributeValue().withS(p.getCatalog_name()));
        item.put("buyer_id", new AttributeValue().withN(Integer.toString(buyer_id)));
      //  item.put("owner_id", new AttributeValue().withN(Integer.toString(p.getOwner_id())));
        PutItemRequest putItemRequest = new PutItemRequest(AWSDynamoDB.table_name, item);
        PutItemResult putItemResult = AWSDynamoDB.dynamoDB.putItem(putItemRequest);
         System.out.println("Result: " + putItemResult);
		return 1;
	}
	
	public List<Product> getProducts(ResultSet rs){
		List<Product> products = new ArrayList<Product>();
		try {
			while(rs.next()){
				Product p = new Product();
				p.setCatalog_id(rs.getInt("catalog_id"));
				p.setOwner_id(rs.getInt("owner_id"));
				p.setProduct_description(rs.getString("product_description"));
				p.setProduct_id(rs.getInt("product_id"));
				p.setProduct_name(rs.getString("product_name"));
				p.setProduct_price(rs.getFloat("product_price"));
				p.setProduct_quantity(rs.getInt("product_quantity"));
				p.setCatalog_name(rs.getString("catalog_name"));
				
				products.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

}
