package com.apon.service;

import org.apache.shiro.SecurityUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@SuppressWarnings("unchecked")
@Produces(MediaType.APPLICATION_JSON)
@Path("user")
public class LogonUserService {

    /**
     * Logout current user.
     */
    @GET
    @Path("logout")
    public void logout() {
        SecurityUtils.getSubject().logout();
    }
}
