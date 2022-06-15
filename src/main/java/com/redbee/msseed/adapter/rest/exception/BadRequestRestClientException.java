package com.redbee.msseed.adapter.rest.exception;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public final class BadRequestRestClientException extends GenericException {

    public BadRequestRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
