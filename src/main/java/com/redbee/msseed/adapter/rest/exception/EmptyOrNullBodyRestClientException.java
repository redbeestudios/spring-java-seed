package com.redbee.msseed.adapter.rest.exception;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public final class EmptyOrNullBodyRestClientException extends GenericException {

    public EmptyOrNullBodyRestClientException(ErrorCode errorCode) {
        super(errorCode);
    }

}
