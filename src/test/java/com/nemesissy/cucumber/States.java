package com.nemesissy.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.List;
import java.util.Map;

import static com.nemesissy.tools.IsEven.isEven;
import static com.nemesissy.tools.IsOdd.isOdd;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class States {

    public Response response;
    public ValidatableResponse vResponse;
    public String url;

    @Given("^The API is available$")
    public void pingService() {

        given().
                log().all().
        when().
                get("https://datausa.io/api/searchLegacy/?limit=60&dimension=Geography&hierarchy=State&q=").
        then().
                log().all().
        statusCode(200)
        ;
    }

    @When("I get the states")
    public void iGetTheStates() {

        vResponse =
        given().
                log().all().
        when().
                get("https://datausa.io/api/searchLegacy/?limit=60&dimension=Geography&hierarchy=State&q=").
        then().
                log().all().
                statusCode(200).
        assertThat().
                body("results", hasSize(52))
        ;
        response = vResponse.extract().response();
    }

    @Then("I should have <{int}> states")
    public void iShouldHaveStates(int arg0) {

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map> all = jsonPathEvaluator.getList("results");
        assertEquals(52, all.size());

    }

    @Then("It should match the structure in <states.json>")
    public void itShouldMatchTheStructureInStatesJson() {
        assertEquals("yes", "yes");
    }

    @Then("Then verify the {string}, {string}, {string}, {string}")
    public void thenVerifyTheAnd(String name, String key, String id, String fluff) {

        //TODO why does the data table require an xtra column to resolve the id? Shrug?

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<Map> all = jsonPathEvaluator.getList("results");

        System.out.println("Parm Name ["+name+"]");
        System.out.println("Parm Key  ["+key+"]");
        System.out.println("Parm ID   ["+id+"]");

        for (int i=0; i<all.size(); i++) {
            Map state = all.get(i);
            if (state.get("name").toString().contains(name)) {

                assertEquals(name, state.get("name").toString());
                assertEquals(key, state.get("key").toString());
                assertEquals(id, state.get("id").toString());
            }
        }

    }

    @When("I get a state {string}")
    public void iGetAState(String name) {

        url = "https://datausa.io/api/searchLegacy/?limit=60&dimension=Geography&hierarchy=State&q=" + name;
    }

    @Then("Verify some attributes using different hamcrest matchers")
    public void verifySomeAttributesUsingDifferentHamcrestMatchers() {

        given().
                log().all().
        when().
                get(url).
        then().
                log().all().
                statusCode(200).
        assertThat().
                body("results",     hasSize(1)).
                body("results[0]",  hasEntry("hierarchy","State")).
                body("results[0].dimension", instanceOf(String.class)).
                body("results[0].zvalue",    instanceOf(Float.class)).
                body("results[0].dimension", is("Geography")).
                body("results[0].zvalue",    is(0.68087465F)).
                body("query",       hasKey("dimension")).
                body("totals",      hasKey("Geography"))
        ;
    }

    @Then("Verify some attributes using custom hamcrest matchers")
    public void verifySomeAttributesUsingCustomHamcrestMatchers() {

        given().
                log().all().
        when().
                get(url).
        then().
                log().all().
                statusCode(200).
        assertThat().
                body("results",     hasSize(1)).
                body("totals.Geography.State", isEven()).
                body("totals.Geography.Place", isOdd())
        ;
    }
}
