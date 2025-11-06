package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.lang.Nullable;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * The class AbstractControllerTest implemented to supporting the test controller implementation.
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@WebMvcTest
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    abstract void setUp();


    abstract void testCreate() throws Exception;


    abstract void testUpdate() throws Exception;


    abstract void testDeleteById() throws Exception;


    abstract void testFindById() throws Exception;


    abstract void testFindAll() throws Exception;

    /**
     * Perform a GET request to test the controller.
     * @param urlTemplate URL template; the resulting URL will be encoded.
     * @param expression the JSON path expression. For example:
     *                   - $.name to retrieve the nome value in the entity;
     *                   - $.id to retrieve the id value in the entity;
     *                   - $.email to retrieve the email value in the entity;
     *                   - $[0].type to retrieve the type properties inside the JSON array. Uses it if your
     *                   get method return a collection like List.
     * @param expectedValue the expected result.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performGetRequest(@NotNull String urlTemplate, @NotNull String expression, @Nullable Object expectedValue) throws Exception {
        // Perform a GET request to test the controller.
        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk()) // HttpStatus OK (200).
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(expression).value(expectedValue)); // Used to check the data in the JSON.
    }

    /**
     * Perform a GET request to test the controller.
     * @param urlTemplate URL template; the resulting URL will be encoded.
     * @param evoModel The model that will be searched for in the database.
     * @param expression the JSON path expression. For example:
     *                   - $.name to retrieve the nome value in the entity;
     *                   - $.id to retrieve the id value in the entity;
     *                   - $.email to retrieve the email value in the entity;
     *                   - $[0].type to retrieve the type properties inside the JSON array. Uses it if your
     *                   get method return a collection like List.
     * @param expectedValue the expected result.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performGetRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel, @NotNull String expression, @Nullable Object expectedValue) throws Exception {
        // Perform a GET request to test the controller.
        mockMvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON).content(evoModel.toString()))
                .andExpect(status().isOk()) // HttpStatus OK (200).
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(expression).value(expectedValue)); // Used to check the data in the JSON.
    }

    /**
     * Perform a GET request not found to test the controller.
     * @param urlTemplate URL template; the resulting URL will be encoded.
     * @param expression the JSON path expression. For example:
     *                   - $.name to retrieve the nome value in the entity;
     *                   - $.id to retrieve the id value in the entity;
     *                   - $.email to retrieve the email value in the entity;
     *                   - $[0].type to retrieve the type properties inside the JSON array. Uses it if your
     *                   get method return a collection like List.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performGetRequestNotFound(@NotNull String urlTemplate, @NotNull String expression) throws Exception {
        // Perform a GET request to test the controller.
        mockMvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // HttpStatus OK (404).
                .andExpect(jsonPath(expression).doesNotExist()); // Used to check the data in the JSON.
    }

    /**
     * Perform a POST request to test the controller.
     * @param urlTemplate URL template; the resulting URL will be encoded.
     * @param evoModel The model that will be inserted in the database.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performCreateRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel) throws Exception {
        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(evoModel.toString()))
                .andExpect(status().isCreated()); // Check if the insert was performed via the HttpStatus OK (201).
    }

    /**
     * Perform a POST request with bad request to test the controller.
     * @param urlTemplate URL template; the resulting URL will be encoded.
     * @param evoModel The model that will be inserted in the database.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performCreateRequestBadRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel) throws Exception {
        mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(evoModel.toString()))
                .andExpect(status().isBadRequest()); // Check if the insert was performed via the HttpStatus OK (400).
    }

    /**
     * Perform a PUT request to test the controller.
     * @param urlTemplate URL template; the resulting URL will be encoded.
     * @param newEvoModel The model that will be updated in the database.
     * @param expression the JSON path expression. For example:
     *                   - $.name to retrieve the nome value in the entity;
     *                   - $.id to retrieve the id value in the entity;
     *                   - $.email to retrieve the email value in the entity;
     *                   - $[0].type to retrieve the type properties inside the JSON array. Uses it if your
     *                     get method return a collection like List.
     * @param expectedValue the expected result.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performUpdateRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel newEvoModel, @NotNull String expression, @Nullable Object expectedValue) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate, newEvoModel.getId())
                        .content(newEvoModel.toString()) // Get the json generated by the entity.
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HttpStatus OK (200).
                .andExpect(jsonPath(expression).value(expectedValue)); // Used to check if the data was updated.
    }

    /**
     * Perform a DELETE request to test the controller.
     * @param urlTemplate URL template; the resulting URL will be encoded.
     * @param evoModel The model that will be deleted in the database.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performDeleteRequest(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate, evoModel.getId())) // Build the delete command.
                .andExpect(status().isNoContent()); // HttpStatus NO_CONTENT (204).
    }

    /**
     * Perform a GET request with an object in the request to test the controller.
     * @param urlTemplate URL template. The resulting URL will be encoded.
     * @param evoModel The model that will be searched for in the database.
     * @param expression the JSON path expression. For example:
     *                   - $.name to retrieve the nome value in the entity;
     *                   - $.id to retrieve the id value in the entity;
     *                   - $.email to retrieve the email value in the entity;
     *                   - $[0].type to retrieve the type properties inside the JSON array. Uses it if your
     *                   get method return a collection like List.
     * @param expectedValue the expected result.
     * @throws Exception An exception will be throws if the test fail.
     */
    protected void performGetRequestWithObject(@NotNull String urlTemplate, @NotNull AbstractEvoModel evoModel, @NotNull String expression, @Nullable Object expectedValue) throws Exception {
        // Perform a GET request with the evoModel to test the controller.
        mockMvc.perform(get(urlTemplate).contentType(MediaType.APPLICATION_JSON).content(evoModel.toString()))
                .andExpect(status().isOk()) // HttpStatus OK (200).
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(expression).value(expectedValue)); // Used to check the data in the JSON.
    }
}