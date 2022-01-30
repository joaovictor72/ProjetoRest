package testes;
import global.Global;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;


public class SchemaTest extends Global {
    @Test
    public void deveValidarSchemaJson() {
        given()
                    .log().all()
                .when()
                    .get(getBaseURL() + "/users")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"));
    }
}
