package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class GitHubProject {
    RequestSpecification reqSpec;
    String sshKey;
    int sshKeyId;
    @BeforeClass
    public void setup()
    {
        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();
        sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQDUf+y5HwHfHE4SVHNDLE3pOZCn8zoOxK6jWdxU0x4ntMmiaHP3pXz0ss4TNCL2W2yStDxx9TtCCpD1SejpXjscBauZ9CEi92QV8jKOZ1owHAgedzgllryupSmaz7nco9bRYvt16W4R6Exc1viSni8oH2W/kIErlhyPi+g7rzJD3w==";
    }

    @Test(priority = 1)
    public void addKeys()
    {
        String reqBody= "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
        Response response = given().spec(reqSpec)
                .body(reqBody)
                .when().post("/user/keys");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        sshKeyId = response.then().extract().path("id");
        response.then().statusCode(201);
    }
    @Test(priority = 2)
    public void getKeys()
    {
        Response response = given().spec(reqSpec)
                .when().get("/user/keys");
        String resBody = response.getBody().asPrettyString();
        System.out.println(resBody);
        response.then().statusCode(200);

    }

    @Test(priority = 3)
    public void deleteKeys()
    {
        Response response = given().spec(reqSpec)
                .pathParam("keyId", sshKeyId)
                .when().delete("/user/keys/{keyId}");
        String responseBody = response.getBody().asPrettyString();
        System.out.println(responseBody);

        response.then().statusCode(204);
    }

}
