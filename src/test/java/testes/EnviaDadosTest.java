package testes;
import global.Global;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EnviaDadosTest extends Global {

    @Test
    public void deveEnviarDadosQuery(){
        given()
                    .log().all()
                .when()
                    .get(getBaseURL() + "/v2/users?format=xml")
                .then()
                    .log().all()
                    .statusCode(200)
                    .contentType(ContentType.XML);
    }

    @Test
    public void deveEnviarDadosQueryViaParam(){
        given()
                    .log().all()
                    .queryParam("format", "xml")
                .when()
                    .get(getBaseURL() + "/v2/users")
                .then()
                    .log().all()
                    .statusCode(200)
                    .contentType(ContentType.XML)
                    .contentType(containsString(("utf-8")));
    }
    
    @Test
    public void deveEnviarViaHeader(){
        given()
                    .log().all()
                    .accept(getApplication() + "/xml")
                .when()
                    .get(getBaseURL() + "/v2/users")
                .then()
                    .log().all()
                    .statusCode(200)
                    .contentType(ContentType.XML);

    }
}
