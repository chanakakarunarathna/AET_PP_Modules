package com.placepass.batchprocessor.domain;

public enum ProcessStatus {

    SUCCESS("Procees is Success"), NOT_STARTED("Process is Not Started"), ERROR(
            "Process encounter with an Error"), NOT_CHANGED("Status not changed");

    String value;

    private ProcessStatus(String value) {
        this.value = value;
    }
}
