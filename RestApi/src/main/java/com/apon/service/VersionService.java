package com.apon.service;

import com.apon.service.valueobject.StringValueObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@SuppressWarnings("unchecked")
@Produces(MediaType.APPLICATION_JSON)
@Path("version")
public class VersionService {
    private final String VERSION = "0.3";

    /**
     * Get the current version of the application.
     * @return StringValueObject
     */
    @GET
    public StringValueObject getVersion() {
        return new StringValueObject(VERSION);
    }

    /**
     * Get the current version of the application, but you must be logged into get it.
     * @return StringValueObject
     */
    @GET
    @Path("secured")
    public StringValueObject getSecuredVersion() {
        return new StringValueObject(VERSION);
    }
}
