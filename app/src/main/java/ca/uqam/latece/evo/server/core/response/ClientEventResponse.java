package ca.uqam.latece.evo.server.core.response;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ClientEventResponse {
    private ObjectNode response;

    private ClientEvent clientEvent = ClientEvent.UNSPECIFIED;

    private boolean success = false;

    public ClientEventResponse() {
        ObjectMapper objectMapper = new ObjectMapper();
        this.response = objectMapper.createObjectNode();
    }

    public ClientEventResponse(ClientEvent clientEvent) {
        this();
        this.clientEvent = clientEvent;
    }

    public ClientEventResponse(ClientEvent clientEvent, ObjectNode response) {
        this(clientEvent);
        this.response = response;
    }

    public ObjectNode getResponse() {
        return response;
    }

    public void setResponse(ObjectNode response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
        if (!this.response.isEmpty()) {
            response.put("success", this.success);
        }
    }

    /**
     * Updates the response attribute with information from an ActivityInstance in JSON format.
     *
     * @param activityType the Subtype of the ActivityInstance
     * @param activityId the id of the ActivityInstance
     * @param status the ExecutionStatus of the ActivityInstance
     * @param failedEntryConditions the failed entry conditions of the ActivityInstance
     * @param failedExitConditions the failed exit conditions of the ActivityInstance
     */
    public void addResponse(String activityType, Long activityId, ExecutionStatus status,
               String failedEntryConditions, String failedExitConditions) {
        ObjectMapper objectMapper = new ObjectMapper();

        if (this.response.isEmpty()) {
            this.response.put("clientEvent", clientEvent.toString());
            this.response.put("success", success);
        }

        ArrayNode entity = objectMapper.createArrayNode();
        ObjectNode information = objectMapper.createObjectNode();
        information.put("id", activityId);
        information.put("status", status.toString());
        information.put("failedEntryConditions", failedEntryConditions);
        information.put("failedExitConditions", failedExitConditions);

        entity.add(information);
        this.response.set(activityType, entity);
    }
}
