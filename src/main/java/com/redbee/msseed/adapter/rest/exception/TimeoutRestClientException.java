package com.redbee.msseed.adapter.rest.exception;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public final class TimeoutRestClientException extends GenericException {

    public TimeoutRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }

}
