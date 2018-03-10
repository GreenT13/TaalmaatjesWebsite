package jbcrypt;

import com.apon.database.generated.tables.pojos.LogonuserPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.mydao.LogonUserMyDao;
import com.apon.shiro.BCryptUtil;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class AddLogonUser {

    @Test
    public void addLogonUser() {
        // Create connection with the database.
        String url = "jdbc:mysql://localhost:3306/taalmaatjes";

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try {
            Connection connection = DriverManager.getConnection(url, "root", "rootie");
            DbContext context = new DbContext(connection);

            LogonUserMyDao logonUserMyDao = new LogonUserMyDao(context);
            LogonuserPojo logonuserPojo = new LogonuserPojo();
            logonuserPojo.setLogonuserid(1);
            logonuserPojo.setUsername("root");
            logonuserPojo.setPassword(BCryptUtil.hashPassword("rootie"));
            logonuserPojo.setNroflogonattempts(0);

            assertEquals(BCryptUtil.checkPassword("rootie", logonuserPojo.getPassword()), true);

            logonUserMyDao.insert(logonuserPojo);
            context.commit();
            context.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
