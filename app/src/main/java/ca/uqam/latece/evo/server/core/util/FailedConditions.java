package ca.uqam.latece.evo.server.core.util;

public class FailedConditions {
    String failedEntryConditions = "";

    String failedExitConditions = "";

    public FailedConditions() {}

    public FailedConditions(String failedEntryConditions, String failedExitConditions) {
        this.failedEntryConditions = failedEntryConditions;
        this.failedExitConditions = failedExitConditions;
    }

    public String getFailedEntryConditions() {
        return failedEntryConditions;
    }

    public void setFailedEntryConditions(String failedEntryConditions) {
        this.failedEntryConditions = failedEntryConditions;
    }

    public String getFailedExitConditions() {
        return failedExitConditions;
    }

    public void setFailedExitConditions(String failedExitConditions) {
        this.failedExitConditions = failedExitConditions;
    }
}
