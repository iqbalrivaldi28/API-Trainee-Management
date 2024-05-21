package com.paradigm.tech.app.utlls;

public enum EStatus {
    BACKEND("Backend"),
    FRONTEND("Frontend"),
    MOBILE("Mobile"),
    FINISHED_TRAINING("Finished Training"),
    APPLIED("Applied"),
    STAGE_1_TESTED("Stage 1 Tested"),
    STAGE_1_PASSED("Stage 1 Passed"),
    STAGE_2_TESTED("Stage 2 Tested"),
    FINAL_STAGE_PASSED("Final Stage Passed"),
    WAITING_ON_BOARD("Waiting on Board"),
    ON_BOARD("On Board"),
    FAILED("Failed"),
    OPEN("Open"),
    CLOSED("Closed");

    private final String statusName;

    EStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}
