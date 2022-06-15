package com.redbee.msseed.adapter.rest.exception;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public final class NonTargetRestClientException extends GenericException {

    public NonTargetRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }

}
