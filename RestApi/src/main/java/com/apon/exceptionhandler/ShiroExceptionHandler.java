package com.apon.exceptionhandler;

import com.apon.resteasy.EH;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@EH
public class ShiroExceptionHandler implements ExceptionMapper<ShiroException> {
    @Override
    public Response toResponse(ShiroException e) {
        return Response.status(e instanceof UnauthenticatedException ? Response.Status.UNAUTHORIZED : Response.Status.FORBIDDEN)
                .entity(e.getMessage())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
