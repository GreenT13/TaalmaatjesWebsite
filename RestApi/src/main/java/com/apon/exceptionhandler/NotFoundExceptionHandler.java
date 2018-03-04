package com.apon.exceptionhandler;

import com.apon.resteasy.EH;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@EH
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Could not find the entity you were looking for.", null))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
