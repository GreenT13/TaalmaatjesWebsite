package com.apon.database.jooq;

import com.apon.log.MyLogger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DbContextUtil {

    public static DbContext getContextFromTomcat() throws SQLException {
        try {
            DataSource dataSource = (DataSource) ((javax.naming.Context) new InitialContext()
                    .lookup("java:comp/env"))
                    .lookup("jdbc/Taalmaatjes-db");
            return new DbContext(dataSource);
        } catch (NamingException e) {
            MyLogger.logError("Something went horribly wrong!", e);
            return null;
        }
    }
}
