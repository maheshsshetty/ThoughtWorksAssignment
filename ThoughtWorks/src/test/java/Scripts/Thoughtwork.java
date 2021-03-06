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
  
  
  /** 
   * Testcase to validate positive scenario User registration positive scenario
   */
  @Test
  void registerUser_Positive() {

    JSONObject requestParams = new JSONObject();
    requestParams.put("email", userName);
    requestParams.put("password", password);

    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/register").then()
      .extract().response();

    // Assertion of response

    Assert.assertEquals(response.statusCode(), 200);
    Assert.assertEquals(response.jsonPath().get("id"), 7);
    Assert.assertNotNull(response.jsonPath().get("token"));
  }
  
  /** 
   * Testcase to validate Negative scenario User registration without email
   */
  @Test
  void registerUserEmailMissing_Negative() {

    JSONObject requestParams = new JSONObject();
    requestParams.put("password", password);

    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/register").then()
      .extract().response();

    // Assertion of response

    Assert.assertEquals(response.statusCode(), 400);
    Assert.assertEquals(response.jsonPath().get("error"), "Missing email or username");
  }
  
  
  /** 
   * Testcase to validate Negative scenario User registration without password
   */
  @Test
  void registerUserPasswordMissing_Negative() {

    JSONObject requestParams = new JSONObject();
    requestParams.put("email", userName);

    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/register").then()
      .extract().response();

    // Assertion of response

    Assert.assertEquals(response.statusCode(), 400);
    Assert.assertEquals(response.jsonPath().get("error"), "Missing password");
  }
  
  
  /** 
   * Testcase to validate Negative scenario User registration without body
   */
  @Test
  void registerUserNoBody_Negative() {

    JSONObject requestParams = new JSONObject();
    requestParams.put("email", userName);

    Response response = given().contentType(ContentType.JSON)
      .post("/register").then()
      .extract().response();

    // Assertion of response

    Assert.assertEquals(response.statusCode(), 400);
    Assert.assertEquals(response.jsonPath().get("error"), "Missing email or username");
  }
  
  /** 
   * Testcase to validate positive scenario User registration positive scenario
   */
  @Test
  void registerUserWrongEmail_Negative() {

    JSONObject requestParams = new JSONObject();
    requestParams.put("email", "mahesh@gmail.com");
    requestParams.put("password", password);

    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/register").then()
      .extract().response();

    // Assertion of response

    Assert.assertEquals(response.statusCode(), 400);
    Assert.assertEquals(response.jsonPath().get("error"), "Note: Only defined users succeed registration");
  }
  
  /**
   *  Testcase to verify login api  
   */
  @Test
  void login_Positive() {
    JSONObject requestParams = new JSONObject();
    requestParams.put("email", userName);
    requestParams.put("password", password);

    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/login").then()
      .extract().response();

    // Assertion of response
    
    Assert.assertEquals(response.statusCode(), 200);
    Assert.assertNotNull(response.jsonPath().get("token"));
  }

  
  /**
   *  Testcase to verify login api without username 
   */
  @Test
  void loginWithoutUsername_Negative() {
    JSONObject requestParams = new JSONObject();
    requestParams.put("password", password);

    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/login").then()
      .extract().response();

    // Assertion of response
    
    Assert.assertEquals(response.statusCode(),400);
    Assert.assertEquals(response.jsonPath().get("error"),"Missing email or username");
  }

  /**
   *  Testcase to verify login api without password 
   */
  @Test
  void loginWithoutPassword_Negative() {
    JSONObject requestParams = new JSONObject();
    requestParams.put("email", "eve.holt@reqres.in");
    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/login").then()
      .extract().response();

    // Assertion of response
    
    Assert.assertEquals(response.statusCode(),400);
    Assert.assertEquals(response.jsonPath().get("error"),"Missing password");
  }
  
  /**
   *  Testcase to verify login api with wrong username 
   */
  @Test
  void loginWithwrongUsername_Negative() {
    JSONObject requestParams = new JSONObject();
    requestParams.put("email", "mahesh@gmail.com");
    requestParams.put("password", password);
    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/login").then()
      .extract().response();

    // Assertion of response
    
    Assert.assertEquals(response.statusCode(),400);
    Assert.assertEquals(response.jsonPath().get("error"),"user not found");
  }
  
  /**
   * Testcase to verify createuser api positve scenario
   */
  @Test
  void createuser_Postive() {
    JSONObject requestParams = new JSONObject();
    requestParams.put("name", "mahesh");
    requestParams.put("job", "leader");

    Response response = given().contentType(ContentType.JSON).body(requestParams.toJSONString()).post("/users").then()
      .extract().response();
    
    // Assertion for response
    
    Assert.assertEquals(response.statusCode(), 201);
    Assert.assertEquals(response.jsonPath().get("name"), "mahesh");
    Assert.assertEquals(response.jsonPath().get("job"), "leader");
    Assert.assertNotNull(response.jsonPath().get("createdAt"));
    Assert.assertNotNull(response.jsonPath().get("id"));
  }
  
  
  /**
   * Testcase verification of userlist page number 1 is considere as prameter
   */
  @Test
  void userList_Postive() {
    
    Response response = given().contentType(ContentType.JSON).param("page", 1).get("/users").then().extract().response();
    
    // Assertion of response
    
    Assert.assertEquals(response.statusCode(), 200);
    Assert.assertEquals(response.jsonPath().get("page"), 1);
    Assert.assertEquals(response.jsonPath().get("per_page"), 6);
    Assert.assertNotNull(response.jsonPath().get("total"));
    Assert.assertNotNull(response.jsonPath().get("total_pages"));
  }

  
  /**
   * Testcase verifcation of delted user api
   */
  @Test
  void deleteuser_Postive() {

    Response response = given().contentType(ContentType.JSON).delete("/users/7").then().extract().response();

    //Assertion of response
    
    Assert.assertEquals(response.statusCode(), 204);
    Assert.assertTrue(response.body().asString().isEmpty());

  }
}



