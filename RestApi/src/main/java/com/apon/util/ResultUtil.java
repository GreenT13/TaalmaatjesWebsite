package com.apon.util;

import com.apon.exceptionhandler.ErrorResponse;
import com.apon.log.MyLogger;
import com.apon.exceptionhandler.ResultObject;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResultUtil {

    public static ResultObject createErrorResult(String messageCode) {
        return createErrorResult(messageCode, null);
    }

    public static ResultObject createErrorResult(String messageCode, Exception e) {
        MyLogger.logError(messageCode, e);
        return new ResultObject(messageCode, e);
    }

    public static Response createResponseFromResult(ResultObject resultObject) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTitle(MessageResource.getInstance().getValue(resultObject.getMessageCode(),
                (Object[]) resultObject.getMessageArguments()));
        if (resultObject.getE() != null) {
            errorResponse.setDetail(ExceptionUtils.getStackTrace(resultObject.getE()));
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
