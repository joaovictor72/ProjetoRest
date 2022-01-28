import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static org.junit.Assert.*;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserJsonTest extends Global {

    public static RequestSpecification reqSpec;
    public static ResponseSpecification resSpec;

    @Autowired
    public static RequestSpecBuilder reqBuilder;

    @Autowired
    public static ResponseSpecBuilder resBuilder;

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

    @Test
    public void deveVerificarPrimeiroNivel() {
        given()
                .log().all()
                .when()
                    .get(getBaseURL() + "/users/1")
                .then()
                    .statusCode(200)
                    .body("id", is(1))
                    .body("name", containsString("Silva"))
                    .body("age", greaterThan(18));

    }

    @Test
    public void deveVerificarPrimeiroNivelOutrasFormas(){
        Response response = RestAssured.request(Method.GET,"https://restapi.wcaquino.me/users/1");

        //path
        assertEquals(Integer.valueOf(1), response.path("id"));

        //jsonpath
        JsonPath jpath = new JsonPath(response.asString());
        assertEquals(1, jpath.getInt("id"));

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        assertEquals(1, id);
    }

    @Test
    public void deveVerificarSegundoNivel(){
        given()
                .when()
                    .get(getBaseURL() + "/users/2")
                .then()
                    .statusCode(200)
                    .body("name", containsString("Joaquina"))
                    .body("endereco.rua", is("Rua dos bobos"));
    }

    @Test
    public void deveVerificarLista(){
        given()
                .when()
                    .get(getBaseURL() + "/users/3")
                .then()
                    .statusCode(200)
                    .body("name", containsString("Ana Júlia"))
                    .body("filhos", hasSize(2))
                    .body("filhos[0].name", is("Zezinho"))
                    .body("filhos[1].name", is("Luizinho"))
                    .body("filhos.name", hasItems("Zezinho", "Luizinho"));
    }

    @Test
    public void deveRetornarErroUsuarioInexistente(){
        given()
                .when()
                    .get(getBaseURL() + "/users/4")
                .then()
                    .statusCode(404)
                    .body("error", is("Usuário inexistente"));
    }

    @Test
    public void deveVerificarListaRaiz() {
        given()
                .when()
                    .get(getBaseURL() + "/users")
                .then()
                    .statusCode(200)
                    .body("$", hasSize(3))
                    .body("name", hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
                    .body("age[1]", is(25))
                    .body("filhos.name", hasItem(Arrays.asList("Zezinho", "Luizinho")))
                    .body("salary", contains(1234.5678f, 2500, null));

    }
    
    @Test
    public void devoFazerVerificacoesAvancadas(){
        given()
                .when()
                    .get(getBaseURL() + "/users")
                .then()
                    .statusCode(200)
                    .body("$", hasSize(3))
                    .body("age.findAll{it <= 25}.size()", is(2))
                    .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                    .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
                    .body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
                    .body("find{it.age <= 25}.name", is("Maria Joaquina"))
                    .body("findAll{it.name.contains('n')}.name", hasItems("Maria Joaquina", "Ana Júlia"))
                    .body("findAll{it.name.length() > 10}.name", hasItems("João da Silva", "Maria Joaquina"))
                    .body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
                    .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
                    .body("name.findAll{it.startsWith('Maria')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("MARIA JOAQUINA"), arrayWithSize(1)))
                    .body("age.collect{it * 2}", hasItems(60,50,40))
                    .body("id.max()", is(3))
                    .body("salary.min()", is(1234.5678f))
                    .body("salary.findAll{it != null}.sum()", is(closeTo(3734.5678f,0.001)))
                    .body("salary.findAll{it != null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)));
    }

    @Test
    public void devoUnirJsonPathComJAVA() {
        ArrayList<String> names =
        given()
                .when()
                    .get(getBaseURL() + "/users")
                .then()
                    .statusCode(200)
                    .extract().path("name.findAll{it.startsWith('Maria')}");
        assertEquals(1,names.size());
        assertTrue(names.get(0).equalsIgnoreCase("mAria JoAQUINA"));
        assertEquals(names.get(0).toUpperCase(), "mAria JoAQUINA".toUpperCase());
    }


}
