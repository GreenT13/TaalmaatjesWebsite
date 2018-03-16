package com.apon.exceptionhandler;

import com.apon.resteasy.EH;
import com.apon.util.MessageResource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@EH
@SuppressWarnings("unused")
public class FunctionalExceptionHandler implements ExceptionMapper<FunctionalException> {

    @Override
    public Response toResponse(FunctionalException e) {
        // Retrieve the messageResource from the exception.
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(MessageResource.getInstance().getValue(e.getMessageResource(), e.getMessageArguments()), null))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
