package cmpe282.lab.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.view.Viewable;


@Path("/home")
public class HomeController {
	
	@POST
	@Path("/signup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	public Response signUp(String data) throws JsonParseException, JsonMappingException, IOException, JSONException  {
		System.out.println(data);
		//JSONArray jsonA = new JSONArray(data);
		JSONObject json = new JSONObject(data);

//		ObjectMapper mapper = new ObjectMapper();  
//		UserBean user = mapper.readValue(data, UserBean.class);

		System.out.println("json-->"+ json.toString());
		
		return Response.ok(json.toString()).build();
	}

	@POST
	@Path("/signin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public Response signIn(@Context HttpServletRequest request,
			@FormParam("firstname") String firstname, 
			@FormParam("lastname") String lastname , 
			@FormParam("password") String password,
			@FormParam("email") String email){
		//other method to get parameter from html page with  MultivaluedMap< K, V>
		//List<String> firstname = form.get("firstname");
		
		request.setAttribute("firstname", firstname);
		request.setAttribute("lastname", lastname);
		request.setAttribute("password", password);
		request.setAttribute("email", email);
		
		Viewable view = new Viewable("/main.jsp",null);
		return Response.ok().entity(view).build();
	}
	
	@Path("/home")
	public String home(){
		
		return "";
	}
	

}
