package com.apon.service;

import com.apon.log.MyLogger;
import com.apon.service.valueobject.StringValueObject;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@SuppressWarnings("unchecked")
@Produces(MediaType.APPLICATION_JSON)
@Path("version")
public class VersionService {
    @GET
    public StringValueObject getVersion() throws NamingException, SQLException {
        DataSource dataSource1 = (DataSource) ((javax.naming.Context) new InitialContext()
                .lookup("java:comp/env"))
                .lookup("jdbc/Taalmaatjes-db");
        dataSource1.getConnection().close();
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
