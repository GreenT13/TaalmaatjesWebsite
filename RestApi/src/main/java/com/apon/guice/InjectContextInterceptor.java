package com.apon.guice;

import com.apon.database.jooq.DbContext;
import com.apon.log.MyLogger;
import com.apon.service.IService;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class InjectContextInterceptor extends AbstractModule implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        MyLogger.logDebug("Start invocation of " + methodInvocation.getThis().getClass().getName() + "."
                + methodInvocation.getMethod().getName());
        IService service = (IService) methodInvocation.getThis();

        try {
            // Initialize the dataSource.
            DataSource dataSource = (DataSource) ((javax.naming.Context) new InitialContext().lookup("java:comp/env"))
                    .lookup("jdbc/Taalmaatjes-db");

            // Set dbContext based on the source on the service.
            DbContext dbContext = new DbContext(dataSource);
            service.setContext(dbContext);
        } catch (NamingException e) {
            MyLogger.logError("Could not initialize context.", e);
            throw e;
        }

        // Execute the service.
        Object object;
        try {
            object = methodInvocation.proceed();
        } catch (Exception e) {
            MyLogger.logError("Invocation of " + methodInvocation.getThis().getClass().getName() + "."
                    + methodInvocation.getMethod().getName() + " failed." , e);

            // Something went wrong, so we want to rollback.
            service.getContext().rollback();

            // Still throw the exception.
            throw e;
        }

        MyLogger.logDebug("End invocation of " + methodInvocation.getThis().getClass().getName() + "."
                + methodInvocation.getMethod().getName());
        return object;
    }

    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(InjectContext.class), this);
    }
}
