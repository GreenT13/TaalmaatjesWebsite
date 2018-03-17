package com.apon.shiro;

import com.apon.database.generated.tables.pojos.LogonuserPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.jooq.DbContextUtil;
import com.apon.database.mydao.LogonUserMyDao;
import com.apon.log.MyLogger;
import com.apon.util.DateTimeUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.sql.SQLException;

public class DatabaseRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // No clue what to do with this functin. I only use authentication and not authorization, so probably just nothing.
        return null;
    }

    /**
     * Check if the user inputted is valid. The user can login if holds:
     * 1. Password is correct. (if not, nrOfLogonAttempts++)
     * 2. LogonUser.nrOfLogonAttemps is less than 3
     * 3. LogonUser.dateEndValid is null or >= today.
     * @param authenticationToken Token with basic information.
     * @return SimpleAuthenticationInfo
     * @throws AuthenticationException Whenever the user cannot login.
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws UnknownAccountException, IncorrectCredentialsException, LockedAccountException, ExpiredCredentialsException {
        // Connect with the database.
        DbContext context = null;
        try {
            context = DbContextUtil.getContextFromTomcat();
            // Lookup user in the database.
            LogonUserMyDao logonUserMyDao = new LogonUserMyDao(context);
            LogonuserPojo logonuserPojo = logonUserMyDao.fetchOneByUsername(((UsernamePasswordToken) authenticationToken).getUsername());
            if (logonuserPojo == null) {
                throw new UnknownAccountException("Could not find user.");
            }

            // Check password
            String plainTextPassword = new String(((UsernamePasswordToken) authenticationToken).getPassword());
            if (!BCryptUtil.checkPassword(plainTextPassword, logonuserPojo.getPassword())) {
                // We will note this event.
                logonuserPojo.setNroflogonattempts(logonuserPojo.getNroflogonattempts() + 1);
                logonUserMyDao.update(logonuserPojo);
                context.commit();
                throw new IncorrectCredentialsException("Incorrect password.");
            }

            // Check nrOfLogonAttempts
            if (logonuserPojo.getNroflogonattempts() >= 3) {
                throw new LockedAccountException("Cannot login anymore.");
            }

            // Check date
            if (logonuserPojo.getDateendvalid() != null && DateTimeUtil.isBeforeToday(logonuserPojo.getDateendvalid())) {
                throw new ExpiredCredentialsException("Account is expired.");
            }

            // User is valid, so return some info.
            return new SimpleAuthenticationInfo(logonuserPojo.getUsername(), plainTextPassword, getClass().getName());
        } catch (SQLException e) {
            MyLogger.logError("Could not connect to user database.", e);
            throw new AuthenticationException("Could not connect to databse.");
        } finally {
            if (context != null) {
                try {
                    context.getConnection().close();
                } catch (SQLException e) {
                    MyLogger.logError("Could not close connection", e);
                }
            }
        }
    }
}
