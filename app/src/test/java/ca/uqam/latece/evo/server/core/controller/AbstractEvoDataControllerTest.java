package ca.uqam.latece.evo.server.core.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import ca.uqam.latece.evo.server.core.config.EvoDataLoader;
import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;
import jakarta.validation.constraints.NotNull;

import java.util.OptionalLong;


/**
 * The AbstractEvoDataControllerTest class is an abstract class developed to test controller classes with data loaded
 * into the database by the {@link EvoDataLoader} class. This class provides utility methods to facilitate common test
 * scenarios, such as performing HTTP requests, validating responses, and testing CRUD operations. Subclasses should
 * implement the abstract methods to define specific test cases for creating, updating, deleting, and find entities.
 * <p>
 * Fields:
 * - port: Represents the port on which the application is running during the test.
 * <p>
 * Annotations:
 * - {@code @SpringBootTest}: Enables Spring Boot's testing support.
 * - {@code @LocalServerPort}: Injects the port assigned to the running test server instance.
 * - {@code @BeforeEach}: Executes code before each test execution.
 * - {@code @Test}: Marks methods as test cases.
 * <p>
 * Methods:
 * - {@code setUp()}: Sets up the environment before each test execution.
 * - Abstract methods:
 *   - {@code testCreate()}: Validates entity creation.
 *   - {@code testCreateRequestBadRequest()}: Handles and validates scenarios causing bad request errors.
 *   - {@code testUpdate()}: Validates updates to the internal state of an entity.
 *   - {@code testDeleteById()}: Validates the functionality of deleting an entity by its ID.
 *   - {@code testFindById()}: Tests entity retrieval by ID.
 *   - {@code testFindAll()}: Tests retrieval of all entities from a data source.
 *   - {@code testNotFound()}: Validates scenarios where a resource or entity cannot be found.
 * - Protected utility methods:
 *   - {@code performGetRequest(String urlTemplate)}: Sends an HTTP GET request and validates the response status.
 *   - {@code performGetRequest(String urlTemplate, Object requestParam, String jsonExpression, Object expectedValue)}: Sends
 *     an HTTP GET request with parameters and asserts the response contains the expected JSON value.
 *   - {@code performGetRequest(String urlTemplate, Object requestParam, String jsonExpression, Long value)}: Overloaded
 *     GET request method to validate JSON responses with long values.
 *   - {@code performGetRequest(String urlTemplate, String jsonExpression, Long value, Object... requestParam)}: Sends
 *     a GET request, validates JSON content, and handles additional parameters.
 *   - {@code performCreateRequest(String urlTemplate, AbstractEvoModel evoModel)}: Sends a POST request to create an entity
 *     and returns the created resource ID.
 *   - {@code performUpdateRequest(String urlTemplate, AbstractEvoModel evoModel)}: Sends a PUT request to update an entity
 *     and validates the response.
 *   - {@code performUpdateRequest(String urlTemplate, AbstractEvoModel evoModel, Long id)}: Overloaded PUT request method to
 *     update a resource and validate its ID.
 *   - {@code performDeleteRequest(String urlTemplate, Long id)}: Sends a DELETE request to remove an entity and validates
 *     the response.
 *   - {@code performCreateRequestWithBadRequest(String urlTemplate, AbstractEvoModel evoModel)}: Sends a POST request
 *   for entity creation expecting a 400 (Bad Request) response.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractEvoDataControllerTest extends EvoDataLoader {

    /**
     * The port number used for the local server during testing. Annotated with {@code @LocalServerPort} to
     * automatically inject the port assigned to the running test server instance.
     */
    @LocalServerPort
    private Integer port;


    @BeforeEach
    public void setUp(){
         // Set the base URI for the REST Assured requests.
        RestAssured.baseURI = RestAssured.DEFAULT_URI + ":" + port;
    }


    /**
     * This abstract method is designed to be implemented by subclasses to perform testing that validate entity creation.
     */
    abstract void testCreate();

    /**
     * Tests the behavior of the system when a create request results in a bad request error. This test is intended to
     * validate that the system appropriately handles invalid input or scenarios that cause a bad request status.
     */
    abstract void testCreateRequestBadRequest();

    /**
     * This abstract method is designed to be implemented by subclasses to perform testing that validate the internal state
     * of an object after an update.
     */
    abstract void testUpdate();

    /**
     * Abstract method to test the deletion functionality by an entity's unique identifier.
     */
    abstract void testDeleteById();

    /**
     * Tests the functionality of finding an entity by its identifier.
     */
    abstract void testFindById();

    /**
     * Tests the behavior and functionality of the method responsible for retrieving all instances or entities from a
     * given data source or repository.
     */
    abstract void testFindAll();

    /**
     * This abstract method is intended to be implemented by subclasses to perform testing or validation when a specific
     * resource or entity is not found.
     */
    abstract void testNotFound();


    /**
     * Performs an HTTP GET request to the specified URL template and asserts that the response has a status code of 200.
     * <b>Note:<b/> Uses this method to test the findAll method implemented in a controller class.
     * @param urlTemplate the URL template to send the GET request to. The urlTemplate Example:
     *                    - Get all data: "/roles"
     */
    protected void performGetRequest(@NotNull String urlTemplate) {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(urlTemplate)
        .then()
        .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value());
    }

    /**
     * Performs an HTTP GET request to the specified URL template using the provided parameters, and asserts that the
     * response body contains the expected value at the specified JSON expression. The response is also validated to
     * have a JSON content type and an HTTP status code of 200 (OK).
     * @param urlTemplate the URL template to send the GET request to. The urlTemplate Example:
     *                    - Gets by name: "/roles/find/name/{name}"
     * @param requestParam the parameter to populate the URL template.
     * @param jsonExpression the JSON path expression to be evaluated in the response body.
     * @param expectedValue the value expected to be present at the specified JSON expression in the response body.
     */
    protected void performGetRequest(@NotNull String urlTemplate,
                                     @NotNull Object requestParam,
                                     @NotNull String jsonExpression,
                                     @NotNull Object expectedValue) {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(urlTemplate, requestParam)
        .then()
                .using()
                .defaultParser(Parser.JSON)
                .statusCode(HttpStatus.OK.value())
        .assertThat()
                .body(jsonExpression, hasItem(expectedValue));
    }

    /**
     * Performs an HTTP GET request to the specified URL template using the provided parameters, and asserts that the
     * response body contains the expected value at the specified JSON expression. The response is also validated to have
     * a JSON content type and an HTTP status code of 200 (OK).
     * @param urlTemplate the URL template to send the GET request to. The urlTemplate Example:
     *                    - Gets by id: "/roles/find/{id}"
     * @param requestParam the parameter to populate the URL template.
     * @param jsonExpression the JSON path expression to be evaluated in the response body.
     * @param value the value expected to be present at the specified JSON expression in the response body.
     */
    protected void performGetRequest(@NotNull String urlTemplate,
                                     @NotNull Object requestParam,
                                     @NotNull String jsonExpression,
                                     @NotNull Long value) {
        this.performGetRequest(urlTemplate, jsonExpression, value, requestParam);

    }

    /**
     * Executes an HTTP GET request to the specified URL with the given parameters and validates the response JSON body.
     * @param urlTemplate the URL template for the request, which may include placeholders for parameters
     * @param jsonExpression the JSON path expression used to extract and validate a specific value in the response body
     * @param value the value to compare against the extracted value from the JSON response body.
     * @param requestParam optional additional parameters to be replaced in the URL template.
     */
    protected void performGetRequest(@NotNull String urlTemplate,
                                     @NotNull String jsonExpression,
                                     @NotNull Long value,
                                     @NotNull Object... requestParam) {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(urlTemplate, requestParam)
        .then()
                .using()
                .defaultParser(Parser.JSON)
                .statusCode(HttpStatus.OK.value())
        .assertThat()
                .body(jsonExpression, equalTo(value.intValue()));
    }

    /**
     * The method sends the given model as the request body in JSON format, verifies the response, and extracts the ID
     * of the created resource.
     * @param urlTemplate the URL template to which the POST request is sent. The urlTemplate Example:
     *                    - "/roles"
     *                    - "/behaviorchangeinterventionphase"
     * @param evoModel the model to be sent in the request body.
     * @return the ID of the created resource as a Long value.
     */
    protected Long performCreateRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel) {
        Integer id = 0;

        id = given()
                .contentType(ContentType.JSON)
                .body(evoModel)
        .when()
                .post(urlTemplate)
        .then()
                .statusCode(HttpStatus.CREATED.value())
        .assertThat()
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
        .extract()
                .path("id");

        return OptionalLong.of(id.longValue()).orElse(0L);
    }

    /**
     * Sends an HTTP PUT request to update an existing resource using the given URL template and a new model object.
     * Validates that the response has a status code of 200 (OK), a JSON content type, and that the response body
     * contains the correct ID matching the one from the provided model.
     * @param urlTemplate the URL template where the PUT request is sent.
     * @param evoModel the new model object to be sent as the request body; must not be null and must contain a valid ID.
     */
    protected void performUpdateRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel) {
        Long id = evoModel.getId();
        given()
                .contentType(ContentType.JSON)
                .body(evoModel)
        .when()
                .put(urlTemplate)
        .then()
                .statusCode(HttpStatus.OK.value())
        .assertThat()
                .contentType(ContentType.JSON)
                .body("id", equalTo(id.intValue()));
    }

    /**
     * Sends an HTTP PUT request to update an existing resource using the specified URL template and the provided model
     * object. Validates that the response has a status code of 200 (OK), a JSON content type, and that the response
     * body contains the expected ID.
     * @param urlTemplate the URL template where the PUT request is sent. The urlTemplate Example:
     *                    - Update an EvoModel: "/behaviorchangeinterventionphaseinstance/changeCurrentBlock/{phaseId}/currentBlock"
     *                    - Update an EvoModel: "/changeModuleStatusToFinished/{phaseId}/moduleToFinished"
     * @param evoModel the new model object to be sent as the request body; must not be null and must conform to the
     *                    expected structure.
     * @param id the ID of the resource being updated.
     */
    protected void performUpdateRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel, @NotNull Long id) {
        given()
                .contentType(ContentType.JSON)
                .body(evoModel)
        .when()
                .put(urlTemplate, id)
        .then()
                .statusCode(HttpStatus.OK.value())
        .assertThat()
                .contentType(ContentType.JSON)
                .body("id", equalTo(id.intValue()));
    }

    /**
     * Performs an HTTP DELETE request to the specified URL template with the provided ID.
     * Validates that the response status code is 204 (No Content).
     * @param urlTemplate the URL template where the DELETE request is sent. The urlTemplate Example:
     *                    - "/roles/{id}"
     *                    - "/behaviorchangeinterventionphase/{id}"
     * @param id the ID of the resource to be deleted.
     */
    protected void performDeleteRequest(@NotNull String urlTemplate, @NotNull Long id) {
        given()
        .when()
                .delete(urlTemplate, id)
        .then()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    /**
     * Executes a POST request with the provided URL template and model, and asserts that the response status code is
     * 400 (Bad Request).
     * @param urlTemplate the URL template to which the POST request will be sent. The urlTemplate Example:
     *                    - "/roles"
     *                    - "/behaviorchangeinterventionphase"
     * @param evoModel the model object to be sent in the request body.
     */
    protected void performCreateRequestWithBadRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel) {
        given()
                .contentType(ContentType.JSON)
                .body(evoModel)
        .when()
                .post(urlTemplate)
        .then()
                .using()
                .defaultParser(Parser.JSON)
        .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Performs a GET request to the specified URL template with the given parameter value(s) and asserts that the
     * response has an HTTP status code of 404 (Not Found).
     * @param urlTemplate the URL template to send the GET request to. urlTemplate Example:
     *                    - Gets by id: "/roles/find/{id}"
     *                    - Gets by id: "/behaviorchangeinterventionphase/find/behaviorchangeintervention/{id}"
     * @param paramValue the parameters to be substituted into the URL template.
     */
    protected void performGetRequestNotFound(@NotNull String urlTemplate, @NotNull Long paramValue) {
        this.performGetRequestNotFound(urlTemplate, paramValue.intValue());
    }

    /**
     * Performs a GET request to the specified URL template with the provided parameters and asserts that the response
     * status code is 404 (Not Found).
     * @param urlTemplate the URL template for the GET request. The urlTemplate Example:
     *                    - Gets by name: "/roles/find/name/{name}"
     *                    - Gets by EvoModel: "/behaviorchangeinterventionphase/find/bcimodules"
     *                    - Gets by id and name: "/roles/find/{id}/name/{name}"
     * @param paramValue the parameters to be substituted into the URL template.
     */
    protected void performGetRequestNotFound(@NotNull String urlTemplate, @NotNull Object... paramValue) {
        given()
                .contentType(ContentType.JSON)
        .when()
                .get(urlTemplate, paramValue)
        .then()
                .using()
                .defaultParser(Parser.JSON)
        .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
