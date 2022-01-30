package testes;
import global.Global;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest extends Global {
    @Test
    public void deveAcessarOutraAPI() {
        given()
                    .log().all()
                .when()
                    .get(getBaseStarWars() + "/api/people/1")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("name", is("Luke Skywalker"));
    }

    // API do site: https://home.openweathermap.org/ necessário fazer conta
    @Test
    public void deveObterClima() {
        given()
                    .log().all()
                    .queryParam("q", "Maringá,BR")
                    .queryParam("appid", "d54f2f356400870a38c2ef4185095a29")
                    .queryParam("units", "metric")
                .when()
                    .get(getBaseClima())
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("name", is("Maringá"))
                    .body("coord.lon", is(-51.9386f))
                    .body("main.temp", greaterThan(25f));
    }
    // CERTIFICADO DO SITE DA API TESTADA EXPIROU DIA 29/01/2022
    @Test
    public void naoDeveAcessarSemSenha() {
        given()
                    .log().all()
                .when()
                    .get(getBaseURL() + "/basicauth")
                .then()
                    .log().all()
                    .statusCode(400);
    }
    // CERTIFICADO DO SITE DA API TESTADA EXPIROU DIA 29/01/2022
    @Test
    public void deveFazerAutenticacaoBasica(){
        given()
                    .log().all()
                    .auth().basic("admin", "senha")
                .when()
                    .get(getBaseURL() + "/basicauth")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("status", is("logado"));
    }
    // CERTIFICADO DO SITE DA API TESTADA EXPIROU DIA 29/01/2022
    @Test
    public void deveFazerAutenticacaoBasicaComChallenge(){
        given()
                    .log().all()
                    .auth().preemptive().basic("admin", "senha")
                .when()
                    .get(getBaseURL() + "/basicauth2")
                .then()
                    .log().all()
                    .statusCode(200)
                    .body("status", is("logado"));
    }
}
