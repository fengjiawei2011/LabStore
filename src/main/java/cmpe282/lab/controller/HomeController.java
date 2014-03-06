package cmpe282.lab.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;

import cmpe282.lab.bean.Product;
import cmpe282.lab.bean.ShoppingCart;
import cmpe282.lab.bean.User;
import cmpe282.lab.dao.ProductDao;
import cmpe282.lab.dao.UserDao;
import cmpe282.lab.dao.impl.ProductDaoImpl;
import cmpe282.lab.dao.impl.UserDaoImpl;

import com.sun.jersey.api.view.Viewable;


@Path("/home")
public class HomeController {
	
	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	public Response signUp(@FormParam("firstname") String firstname, 
			@FormParam("lastname") String lastname , 
			@FormParam("password") String password,
			@FormParam("email") String email) throws JsonParseException, JsonMappingException, IOException, JSONException  {
	
		UserDao ud = new UserDaoImpl();
		User user = new User();
		user.setEmail(email);
		user.setFirst_name(firstname);
		user.setLast_name(lastname);
		user.setPassword(password);
		ud.insertUser(user);
		
		return Response.ok().entity("successfully Sign up !").build();
	}

	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public Response signIn(@Context HttpServletRequest request,
			@FormParam("firstname") String firstname, 
			@FormParam("lastname") String lastname , 
			@FormParam("password") String password,
			@FormParam("email") String email) throws Exception{
		//other method to get parameter from html page with  MultivaluedMap< K, V>
		//List<String> firstname = form.get("firstname");
		System.out.println("in singin");
		UserDao userDao = new UserDaoImpl();
		ProductDao productDao = new ProductDaoImpl();
		User user = userDao.findUser(lastname, firstname, email, password);
		
		Viewable view = null ;
		if(user == null){
			request.setAttribute("illegalUser", " wrong user information, please try again! ");
			view = new Viewable("/login.html",null);
			return Response.ok().entity(view).build();
		}
		
		System.out.println("user_id = "+user.getUser_id());
//		request.setAttribute("firstname", firstname);
//		request.setAttribute("lastname", lastname);
//		request.setAttribute("password", password);
//		request.setAttribute("email", email);
		request.setAttribute("legalUser", user);
		request.setAttribute("sc_num",productDao.getProducts_num(user.getUser_id()));
		
		request.setAttribute( "products", productDao.findAllProduct());
		view = new Viewable("/main.jsp",null);
		return Response.ok().entity(view).build();
	}
	
	@Path("/home")
	public Response home(){
		System.out.println("i am in home");
		Viewable view = new Viewable("/login.html",null);
		return  Response.ok().entity(view).build();
	}
	
	@GET
	@Path("/register")
	public Response register(){
		System.out.println("i am in register");
		Viewable view = new Viewable("/signup.jsp",null);
		return  Response.ok().entity(view).build();
	}
	  
	
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
