import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
    public void deveRemoverUsuario() {
        given()
                    .log().all()
                .when()
                    .delete(getBaseURL() + "/users/1")
                .then()
                    .log().all()
                    .statusCode(204);

    }

    @Test
    public void naoDeveRemoverUsuarioInexistente() {
        given()
                .log().all()
                .when()
                .delete(getBaseURL() + "/users/5000")
                .then()
                .log().all()
                .statusCode(400)
                .body("error", is("Registro inexistente"));

    }

    @Test
    public void deveSalvarUsuarioMap() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", "Usuario via map");
        param.put("age", 25);

        given()
                    .log().all()
                    .contentType(getApplication() + "/json")
                    .body(param)
                .when()
                    .post(getBaseURL() + "/users")
                .then()
                    .log().all()
                    .statusCode(201)
                    .body("id", is(notNullValue()))
                    .body("name", is("Usuario via map"))
                    .body("age", is(25));
    }

    @Test
    public void deveSalvarUsuarioObjeto() {
        User user = new User("Usuario via objeto", 35);

        given()
                    .log().all()
                    .contentType(getApplication() + "/json")
                    .body(user)
                .when()
                    .post(getBaseURL() + "/users")
                .then()
                    .log().all()
                    .statusCode(201)
                    .body("id", is(notNullValue()))
                    .body("name", is("Usuario via objeto"))
                    .body("age", is(35));
    }

    @Test
    public void deveDeserializarSalvarUsuario() {
        User user = new User("Usuario deserializado", 35);

        User usuarioInserido
                =               given()
                                    .log().all()
                                    .contentType(getApplication() + "/json")
                                    .body(user)
                                .when()
                                    .post(getBaseURL() + "/users")
                                .then()
                                    .log().all()
                                    .statusCode(201)
                                    .extract().body().as(User.class);

        System.out.println(usuarioInserido);
        assertThat(usuarioInserido.getId(), notNullValue());
        assertEquals("Usuario deserializado", usuarioInserido.getName());
        assertThat(usuarioInserido.getAge(), is(35));
    }

    @Test
    public void deveSalvarUsuarioXMLusandoObjeto() {
        User user = new User("Usuario XML", 40);
        given()
                    .log().all()
                    .contentType(getApplication() + "/xml")
                    .body(user)
                .when()
                    .post(getBaseURL() + "/usersXML")
                .then()
                    .log().all()
                    .statusCode(201)
                    .body("user.@id", is(notNullValue()))
                    .body("user.name", is("Usuario XML"))
                    .body("user.age", is("40"));
    }



    @Test
    public void deveDeserializarSalvarUsuarioXML() {
        User user = new User("Usuario XML", 40);
        User usuarioInserido =
        given()
                    .log().all()
                    .contentType(getApplication() + "/xml")
                    .body(user)
                .when()
                    .post(getBaseURL() + "/usersXML")
                .then()
                    .log().all()
                    .statusCode(201)
                    .extract().body().as(User.class);
        assertThat(usuarioInserido.getId(), notNullValue());
        assertThat(usuarioInserido.getName(), is("Usuario XML"));
        assertThat(usuarioInserido.getAge(), is(40));
    }

}


