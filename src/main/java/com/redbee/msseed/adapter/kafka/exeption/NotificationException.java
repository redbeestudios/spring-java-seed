package com.redbee.msseed.adapter.kafka.exeption;


import com.redbee.msseed.config.ErrorCode;
import com.redbee.msseed.config.GenericException;

public class NotificationException extends GenericException {

    public NotificationException(ErrorCode ec){
        super(ec);
    }
}
