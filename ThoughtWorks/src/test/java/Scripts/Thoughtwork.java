package Scripts;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;

import java.util.Collections;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;

public class Thoughtwork {
  
  private String apiUrl="";
  private String userName="michael.lawson@reqres.in";
  private String password="pistol";
  
  @BeforeClass
  public void beforeClass() {
    RestAssured.baseURI = "https://reqres.in/api";
  }
  
  
  @Test
  void registerUser_Positive() {
    
    JSONObject requestParams = new JSONObject();
    requestParams.put("email", userName); 
    requestParams.put("password", password);
    
   Response response= given().contentType(ContentType.JSON)
     .body(requestParams.toJSONString())
     .post("/register")
     .then()
     .extract().response();
   
    Assert.assertEquals(response.statusCode(), 200);
    Assert.assertEquals(response.jsonPath().get("id"),7);
    Assert.assertNotNull(response.jsonPath().get("token"));
  }
  
  @Test
  void login_Positive() {
    JSONObject requestParams = new JSONObject();
    requestParams.put("email", userName); 
    requestParams.put("password", password);
    
   Response response= given().contentType(ContentType.JSON)
     .body(requestParams.toJSONString())
     .post("/login")
     .then()
     .extract().response();
    Assert.assertEquals(response.statusCode(), 200); 
    Assert.assertNotNull(response.jsonPath().get("token"));
  }
  
  @Test
  void createuser_Postive() {
    JSONObject requestParams = new JSONObject();
    requestParams.put("name", "mahesh"); 
    requestParams.put("job", "leader");
    
   Response response= given().contentType(ContentType.JSON)
     .body(requestParams.toJSONString())
     .post("/users")
     .then()
     .extract().response();
   Assert.assertEquals(response.statusCode(), 201);
   Assert.assertEquals(response.jsonPath().get("name"), "mahesh");
   Assert.assertEquals(response.jsonPath().get("job"),"leader");
   Assert.assertNotNull(response.jsonPath().get("createdAt"));
   Assert.assertNotNull(response.jsonPath().get("id"));
  }
  
  
  @Test
  void userList_Postive() {
    
    
   Response response= given().contentType(ContentType.JSON)
     .param("page", 1)
     .get("/users")
     .then()
     .extract().response();
   Assert.assertEquals(response.statusCode(), 200);
   Assert.assertEquals(response.jsonPath().get("page"), 1);
   Assert.assertEquals(response.jsonPath().get("per_page"),6);
   Assert.assertNotNull(response.jsonPath().get("total"));
   Assert.assertNotNull(response.jsonPath().get("total_pages"));
  }

  
  @Test
  void deleteuser_Postive() {
    
   Response response= given().contentType(ContentType.JSON)
     .delete("/users/7")
     .then()
     .extract().response();
   
   Assert.assertEquals(response.statusCode(), 204);
   Assert.assertTrue(response.body().asString().isEmpty());
  
  }
}



