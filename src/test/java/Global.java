import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Global {
    public String getBaseURL() {
        return "https://restapi.wcaquino.me";
    }

    public String getApplication () {
        return "application";
    }

    //public static RequestSpecification reqSpec;
    //public static ResponseSpecification resSpec;

    //@Autowired
    //public static RequestSpecBuilder reqBuilder;

    //@Autowired
    //public static ResponseSpecBuilder resBuilder;

//    @BeforeClass
//    public static void setup() {
//        reqBuilder.log(LogDetail.ALL);
//        reqSpec = reqBuilder.build();
//
//        resBuilder.expectStatusCode(200);
//        resSpec = resBuilder.build();
//
//        RestAssured.requestSpecification = reqSpec;
//        RestAssured.responseSpecification = resSpec;
//
//    }
}
