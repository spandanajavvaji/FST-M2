import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class github {
	
	
	//Declare request specification
	RequestSpecification requestSpec;
	//Declare request specification
		ResponseSpecification responseSpec;
	// Global properties
	String sshKey;
	int sshKeyId;
	
	@BeforeClass
	public void setUp() {
		//Create request specification
		requestSpec = new RequestSpecBuilder()
		//set content type
		.setContentType(ContentType.JSON)
		.addHeader("Authorization", "token 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")
		//set base URL
		.setBaseUri("https://github.com")
		// Build request specification
		.build();
		
		sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDI3kkDiDDaYJYLMlsV+cR2RtUu/qYcbGDH2mkY3wCCX2W8WAitz2K2H40wv60Hivq6QbsiObJSVBfJdzdmIvYaXmfe+gr1ApAwKcCsJ4Dbsefm5JRQZRh8E79ucEiltW6MjTE4ScCy4e6Kss05Wk8uF2uom3y/lvG23uQFUjzk8PuuC2naZhNk2JAyJyBo/PJnJjArlrZgruK6yPDuj6ByGRqFieo56V2rUCbbD0SJE/R2jvNzLc8jeEAkG+h9H0Tq1NWN4/6WVNRZJP5CJHY+TnrIGT6z7igv/HUdPpFfbDCxxSs11mlQTls8aKwcmYXDzpShrFCQAQ60CzoQmmOf5bo21d3FbIwZic3IP3rYetelSfYB5t17+aTksix7VkvYRd5NWXt93+Ddc3m2A7ZWcD9AHUuG2y5H8IN5cB+E4MSXVe3eNMnFAqkPc/2ON07plMZ1Kdbz5gzJfvuMub8EC/pYPHlOfyYAnkFBPRR0hh67JHzYIfxfW20GWI2PdQU";
		
		
	}
	
		
	@Test(priority=1)
    public void addKeys () {
       
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\" : \"" + sshKey + "\"}";
 
        Response response = given().spec(requestSpec) 
        	.body(reqBody)
            .when().post("/user/keys"); 
       
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        System.out.println(response.getStatusCode());
        sshKeyId = response.then().extract().path("id");
        
        response.then().statusCode(201);
        
      }
    
    @Test(priority=2)
    public void getKeys() {
        
        Response response = 
            given().spec(requestSpec) 
           // .pathParam("key", "sshKeyId") 
            .when().get("/user/keys");
        
        // Get response body
        String resBody = response.getBody().asPrettyString();
 
        System.out.println(resBody);
        System.out.println(response.getStatusCode());
        
        response.then().statusCode(200);
    }
    
    @Test(priority=3)
    public void deleteKeys() {
        Response response = 
            given().spec(requestSpec) 
            .pathParam("keyId", sshKeyId)
            .when().delete("/user/keys/{keyId}");
        
        String resBody = response.getBody().asPrettyString();
        
        System.out.println(resBody);
        System.out.println(response.getStatusCode());
 
        // Assertion
        response.then().statusCode(204);
        
    }

}
