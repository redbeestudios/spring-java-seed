package com.redbee.msseed.adapter.rest.exception;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public final class NotFoundRestClientException extends GenericException {

    public NotFoundRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
