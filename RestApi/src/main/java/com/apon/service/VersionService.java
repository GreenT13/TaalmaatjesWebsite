package com.apon.service;

import com.apon.service.valueobject.StringValueObject;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@SuppressWarnings("unchecked")
@Produces(MediaType.APPLICATION_JSON)
@Path("version")
public class VersionService {
    @GET
    public StringValueObject getVersion() {
        return new StringValueObject("v0.1-unsecured");
    }

    @GET
    @Path("secured")
    public StringValueObject getSecuredVersion() {
        return new StringValueObject("v0.1-secured");
    }

    @DELETE
    @Path("error")
    public Object throwError() throws Exception {
        throw new Exception("Catch this!");
    }
}
