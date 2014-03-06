package cmpe282.lab.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import cmpe282.lab.bean.Product;
import cmpe282.lab.dao.ProductDao;
import cmpe282.lab.dao.impl.ProductDaoImpl;

@Path("/sc")
public class ShoppingCartController {
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(String data) throws Exception{
		JSONObject json = new JSONObject(data);
		System.out.println("json :" + json.toString());
		String name = json.getString("product_name");
		String price = json.getString("product_price");
		String des = json.getString("product_description");
		String quatity = json.getString("product_quantity");
		String product_id = json.getString("product_id");
		String buyer_id = json.getString("user_id");
		String owner_id = json.getString("owner_id");
		String catalg_name = json.getString("catalog_name");
		
		Product p = new Product(); 
		p.setProduct_description(des);
		p.setProduct_id(Integer.parseInt(product_id));
		p.setProduct_name(name);
		p.setProduct_price(Float.parseFloat(price));
		p.setProduct_quantity(Integer.parseInt(quatity));
		p.setOwner_id(Integer.parseInt(owner_id));
		p.setCatalog_name(catalg_name);
		
		System.out.println("name = " + name + " price = "+ price +" des= "+des + " quatity= " + quatity);
		ProductDao pdao = new ProductDaoImpl();
		pdao.insertProductsIntoShoppingCart(Integer.parseInt(buyer_id), p);
		return Response.status(200).build();
	}

}
