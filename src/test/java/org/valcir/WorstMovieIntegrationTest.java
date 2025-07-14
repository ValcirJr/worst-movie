package org.valcir;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class WorstMovieIntegrationTest {

    @Test
    public void testProducerIntervalsEndpoint() {
        RestAssured.given()
                .when().get("/gaps")
                .then()
                .statusCode(200)
                .body("min.size()", is(1))
                .body("max.size()", is(1))
                .body("min[0].producer", equalTo("Joel Silver"))
                .body("min[0].interval", equalTo(1))
                .body("min[0].previousWin", equalTo(1990))
                .body("min[0].followingWin", equalTo(1991))
                .body("max[0].producer", equalTo("Matthew Vaughn"))
                .body("max[0].interval", equalTo(13))
                .body("max[0].previousWin", equalTo(2002))
                .body("max[0].followingWin", equalTo(2015));
    }
}
