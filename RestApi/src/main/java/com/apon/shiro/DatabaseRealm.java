package com.apon.shiro;

import com.apon.database.generated.tables.pojos.LogonuserPojo;
import com.apon.database.jooq.DbContext;
import com.apon.database.jooq.DbContextUtil;
import com.apon.database.mydao.LogonUserMyDao;
import com.apon.log.MyLogger;
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

    @SuppressWarnings("ConstantConditions")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // Connect with the database.
        DbContext context;
        try {
            context = DbContextUtil.getContextFromTomcat();
        } catch (SQLException e) {
            MyLogger.logError("Could not connect to user database.", e);
            throw new AuthenticationException("Could not connect to databse.");
        }

        // Lookup user in the database.
        LogonUserMyDao logonUserMyDao = new LogonUserMyDao(context);
        LogonuserPojo logonuserPojo = logonUserMyDao.fetchOneByUsername(((UsernamePasswordToken) authenticationToken).getUsername());
        if (logonuserPojo == null) {
            throw new AuthenticationException("Could not find user.");
        }
        String plainTextPassword = new String(((UsernamePasswordToken) authenticationToken).getPassword());
        if (!BCryptUtil.checkPassword(plainTextPassword, logonuserPojo.getPassword())) {
            throw new AuthenticationException("Incorrect password.");
        }

        // User is valid, so return some info.
        return new SimpleAuthenticationInfo(logonuserPojo.getUsername(), plainTextPassword, getClass().getName());
    }
}
