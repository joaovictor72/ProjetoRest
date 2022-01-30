package testes;
import org.junit.jupiter.api.Test;
import global.Global;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class JWT extends Global {
    @Test
    public void deveFazerAutenticacaoTokenJWT() {
        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "joaovictor72@hotmail.com");
        login.put("senha", "creu123");
        //login
        String token = given()
                            .log().all()
                            .body(login)
                            .contentType(getApplication() + "/json")
                        .when()
                            .post(getSeuBarrigaAPI() + "/signin")
                        .then()
                            .log().all()
                            .statusCode(200)
                            .extract().path("token");;
        // obter contas
                         given()
                            .log().all()
                            .header("Authorization", "JWT " + token)
                        .when()
                            .get(getSeuBarrigaAPI() + "/contas")
                        .then()
                            .log().all()
                            .statusCode(200)
                            .body("nome", hasItem("jvictor72"));
    }
}
