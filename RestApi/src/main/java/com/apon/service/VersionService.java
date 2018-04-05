package com.apon.service;

import com.apon.service.valueobject.StringVO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@SuppressWarnings("unchecked")
@Produces(MediaType.APPLICATION_JSON)
@Path("version")
public class VersionService {
    private final String VERSION = "0.5";

    /**
     * Get the current version of the application.
     * @return StringVO
     */
    @GET
    public StringVO getVersion() {
        return new StringVO(VERSION);
    }

    /**
     * Get the current version of the application, but you must be logged into get it.
     * @return StringVO
     */
    @GET
    @Path("secured")
    public StringVO getSecuredVersion() {
        return new StringVO(VERSION);
    }
}
