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

}
