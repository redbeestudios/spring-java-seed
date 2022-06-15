package com.redbee.msseed.config;

public enum ErrorCode {

    BAD_REQUEST(105,"105", "La request esta mal formateada", "MS-SEED-BAD_REQUEST"),
    INVALID_PARAMETERS_ERROR(110,"110", "{}", "MS-SEED-INVALID_PARAMETERS"),
    WEB_CLIENT_GENERIC(103,"103", "Unexpected rest client error", "MS-SEED-INTERNAL_SERVER_ERROR"),
    KAFKA_EXCEPTION(109,"109", "Error interno de kafka", "MS-SEED-KAFKA_EXCEPTION"),
    INTERNAL_ERROR(108,"108","Internal Error","MS-SEED-INTERNAL_ERROR"),
    INVALID_FILTERS_ERROR(109,"107", "Invalid filters", "MS-SEED-INVALID_FILTERS"),
    DATA_ACCESS_ERROR(111,"111", "Unable to access Account data", "MS-SEED-DATA_ACCESS_ERROR"),
    ESTABLISHMENT_SERVICE_ERROR(112,"112", "Establishment service error", "MS-SEED-ESTABLISHMENT_SERVICE_ERROR"),
    AMOUNT_INVALID(113,"113", "Establishment service error", "MS-SEED-ESTABLISHMENT_SERVICE_ERROR");

    private final int value;
    private final String status;
    private final String detail;
    private final String code;

    ErrorCode(int value,String status, String detail, String code) {
        this.value = value;
        this.status = status;
        this.detail = detail;
        this.code = code;
    }

    public int value() {
        return this.value;
    }

    public  String getStatus(){return this.status;}

    public String getDetail() {
        return this.detail;
    }

    public String getCode() {
        return this.code;
    }
}