package cmpe282.lab.services;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import cmpe282.lab.bean.Product;
import cmpe282.lab.bean.ShoppingCart;
import cmpe282.lab.bean.User;



@Path("/userservice")
public class MyRest {
	 /*
	  * if Post need Response obj to response
	  */
	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	public Response signUp(String data) throws JsonParseException, JsonMappingException, IOException, JSONException  {
		System.out.println(data);
		//JSONArray jsonA = new JSONArray(data);
		JSONObject json = new JSONObject(data);
//		System.out.println("json-->"+ jsonA.toString());
		
//		ObjectMapper mapper = new ObjectMapper();  
//		UserBean user = mapper.readValue(data, UserBean.class);

		System.out.println("json-->"+ json.toString());
		
		return Response.ok(json.toString()).build();
	}
	
	public void signIn(User user){}
	public void signUp(User user){}
	public void singOut(User user){}
	public void createProductCatalogs(String name){}
	public void addNewProducts(String catalog){}
	public void showProductsByCatalog(String catalog){}
	public void getProductsFromSC(User user){}
	public void addProductIntoSC(Product product){}
	public void removeProductOutOfSC(Product product){}
	public void Checkout(ShoppingCart sc){}
}

class UserBean{
	
	public String lastname;
	public String firstname;
	public String email;
	public String password;
}
