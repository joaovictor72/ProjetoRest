package testes;
import global.Global;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FileTest extends Global {
    @Test
    public void deveObrigarEnviarArquivo() {
        given()
                    .log().all()
                .when()
                    .post(getBaseURL() + "/upload")
                .then()
                    .log().all()
                    .statusCode(404)    //deveria ser 400
                    .body("error", is("Arquivo não enviado"));
    }

    @Test
    public void deveFazerUploadArquivo() {
        given()
                    .log().all()
                    .multiPart("arquivo", new File(getResource() + "/users.pdf"))
                .when()
                    .post(getBaseURL() + "/upload")
                .then()
                    .log().all()
                    .statusCode(200)    //deveria ser 400
                    .body("name", is("users.pdf"));
    }

    // apenas arquivos acima de 1,1MB
//    @Test
//    public void naoDeveFazerUploadArquivoGrande() {
//        given()
//                    .log().all()
//                    .multiPart("arquivo", new File("src/main/resources/users.pdf"))
//                .when()
//                    .post(getBaseURL() + "/upload")
//                .then()
//                    .log().all()
//                .time(lessThan(5000L))
//                .statusCode(413);
//    }

    @Test
    public void deveBaixarArquivo() throws IOException {
        byte[] image = given()
                            .log().all()
                        .when()
                            .get(getBaseURL() + "/download")
                        .then()
                            .statusCode(200)
                            .extract().asByteArray();
        File imagem = new File(getResource() + "/rosas.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();
        assertThat(imagem.length(), lessThan(100000L));
    }
}
