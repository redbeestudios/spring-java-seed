package com.redbee.msseed.application.exception;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public class BusinessException extends GenericException {

    public BusinessException(ErrorCode errorCode){
        super(errorCode);
    }
}
