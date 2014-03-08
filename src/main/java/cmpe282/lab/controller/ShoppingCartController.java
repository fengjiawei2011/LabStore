package cmpe282.lab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import cmpe282.lab.bean.Product;
import cmpe282.lab.dao.ProductDao;
import cmpe282.lab.dao.UserDao;
import cmpe282.lab.dao.impl.ProductDaoImpl;
import cmpe282.lab.dao.impl.UserDaoImpl;

import com.sun.jersey.api.view.Viewable;

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
		//String quatity = json.getString("product_quantity");
		String product_id = json.getString("product_id");
		String buyer_id = json.getString("user_id");
		String owner_id = json.getString("owner_id");
		String catalg_name = json.getString("catalog_name");
		
		Product p = new Product(); 
		p.setProduct_description(des);
		p.setProduct_id(Integer.parseInt(product_id));
		p.setProduct_name(name);
		p.setProduct_price(Float.parseFloat(price));
		p.setProduct_quantity(1);
		p.setOwner_id(Integer.parseInt(owner_id));
		p.setCatalog_name(catalg_name);
		
		
		
		//System.out.println("name = " + name + " price = "+ price +" des= "+des + " quatity= " + quatity);
		ProductDao pdao = new ProductDaoImpl();
		
		if(pdao.doesProductExist(Integer.parseInt(buyer_id), Integer.parseInt(product_id)) != 0){
			pdao.updateProductNumInSC(Integer.parseInt(buyer_id), Integer.parseInt(product_id), 1);
		}else{
			pdao.insertProductsIntoShoppingCart(Integer.parseInt(buyer_id), p);
		}
	
		return Response.status(200).build();
	}
	
	@GET
	@Path("/get-sc/{userid}")
	public Response getProductsFromSC(@Context HttpServletRequest request, 
			@PathParam("userid") String userid,
			@QueryParam("lastname") String lastname,
			@QueryParam("firstname") String firstname
			) throws Exception{
		ProductDao pdao = new ProductDaoImpl();
		UserDao user = new UserDaoImpl();
		List<Product> products = new ArrayList<Product>();
		products = pdao.findProductFromShoppingCart(Integer.parseInt(userid));
		
		request.setAttribute( "userid", userid);
		request.setAttribute( "lastname", lastname);
		request.setAttribute( "firstname", firstname);
		request.setAttribute( "products", products);
		Viewable view = new Viewable("/sc.jsp",null);
		return Response.ok().entity(view).build();
		
	}

}
