package rascunho;
import global.Global;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


public class OlaMundoTest extends Global {
    @Test
    public void testOlaMundo() {
        Response response = request(Method.GET, getBaseURL() +"/ola");
        Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        Assert.assertTrue(response.getStatusCode() == 200);
        Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 200);
        Assert.assertEquals(200, response.statusCode());


        //throw new RuntimeException();
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void deveConhecerOutrasFormasRestAssured() {
        given()
                .when()
                    .get(getBaseURL() + "/ola")
                .then()
                    .statusCode(200);
    }

    @Test
    public void devoConhecerMatchersHamcrest() {
        Assert.assertThat("Maria", is("Maria"));
        Assert.assertThat(128, is(128));
        Assert.assertThat(128, isA(Integer.class));
        Assert.assertThat(128d, isA(Double.class));
        Assert.assertThat(128d, greaterThan(120d));
        Assert.assertThat(128d, lessThan(130d));

        List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
        assertThat(impares, hasSize(5));
        assertThat(impares, contains(1, 3, 5, 7, 9));
        assertThat(impares, containsInAnyOrder(1, 3, 5, 9, 7));
        assertThat(impares, hasItem(3));
        assertThat(impares, hasItems(3, 5));
        assertThat("Maria", not("João"));
        assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
    }

    @Test
    public void devoValidarBody() {
        given()
                .when()
                    .get(getBaseURL() + "/ola")
                .then()
                    .statusCode(200)
                    .body(is("Ola Mundo!"))
                    .body(containsString("Mundo"))
                    .body(is(not(nullValue())));
    }
}
