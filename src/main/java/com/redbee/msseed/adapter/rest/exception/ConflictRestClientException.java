package com.redbee.msseed.adapter.rest.exception;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public class ConflictRestClientException extends GenericException {
    public ConflictRestClientException(ErrorCode ec) {
        super(ec);
    }
}
