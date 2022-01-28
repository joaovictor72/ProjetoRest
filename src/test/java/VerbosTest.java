import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class VerbosTest extends Global {
    @Test
    public void deveSalvarUsuario() {
        given()
                    .log().all()
                    .contentType(getApplication() + "/json")
                    .body("{ \"name\": \"Jose\", \"age\": 50 }")
                .when()
                    .post(getBaseURL() + "/users")
                .then()
                    .log().all()
                    .statusCode(201)
                    .body("id", is(notNullValue()))
                    .body("name", is("Jose"))
                    .body("age", is(50));
    }

    @Test
    public void naoDeveSalvarUsuarioSemNome() {
        given()
                    .log().all()
                    .contentType(getApplication() + "/json")
                    .body("{\"age\": 50 }")
                .when()
                    .post(getBaseURL() + "/users")
                .then()
                    .log().all()
                    .statusCode(400)
                    .body("id", is(nullValue()))
                    .body("error", is("Name é um atributo obrigatório"));
    }

    @Test
    public void deveSalvarUsuarioXML() {
        given()
                    .log().all()
                    .contentType(getApplication() + "/xml")
                    .body("<user><name>Jose</name><age>50</age></user>")
                .when()
                    .post(getBaseURL() + "/usersXML")
                .then()
                    .log().all()
                    .statusCode(201)
                .body("user.@id", is(notNullValue()))
                .body("user.name", is("Jose"))
                .body("user.age", is("50"));
    }

    @Test
    public void deveArrumarUsuario() {
        given()
                    .log().all()
                    .contentType(getApplication() + "/json")
                    .body("{ \"name\": \"Usuario Alterado\", \"age\": 80 }")
                .when()
                    .put(getBaseURL() + "/users/1")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("name", is("Usuario Alterado"))
                    .body("age", is(80));
    }

    @Test
    public void deveRemoverUsuario(){
        given()
                    .log().all()
                .when()
                    .delete(getBaseURL() + "/users/1")
                .then()
                    .log().all()
                    .statusCode(204);

    }

    @Test
    public void naoDeveRemoverUsuarioInexistente(){
        given()
                    .log().all()
                .when()
                    .delete(getBaseURL() + "/users/5000")
                .then()
                    .log().all()
                    .statusCode(400)
                    .body("error", is("Registro inexistente"));

    }
}


