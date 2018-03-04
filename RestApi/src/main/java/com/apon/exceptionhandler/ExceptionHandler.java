package com.apon.exceptionhandler;

import com.apon.resteasy.EH;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@EH
public class ExceptionHandler implements ExceptionMapper<Exception> {

    // TODO: Handle generic responses of exceptions (exceptions that we don't know) with some kind of filter?
    public Response toResponse(Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse(e.getMessage(), ExceptionUtils.getStackTrace(e)))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}
