package com.apon.guice;

import com.apon.log.MyLogger;
import com.apon.service.IService;
import com.apon.database.jooq.DbContextUtil;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class InjectContextInterceptor extends AbstractModule implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        MyLogger.logDebug("Start invocation of " + methodInvocation.getThis().getClass().getName() + "."
                + methodInvocation.getMethod().getName());

        // Set the context.
        IService service = (IService) methodInvocation.getThis();
        service.setContext(DbContextUtil.getContextFromTomcat());

        // Execute the service.
        Object object;
        try {
            object = methodInvocation.proceed();
        } catch (Exception e) {
            MyLogger.logError("Invocation of " + methodInvocation.getThis().getClass().getName() + "."
                    + methodInvocation.getMethod().getName() + " failed." , e);

            // Still throw the exception.
            throw e;
        } finally {
            service.getContext().rollback();
            service.getContext().close();
        }

        MyLogger.logDebug("End invocation of " + methodInvocation.getThis().getClass().getName() + "."
                + methodInvocation.getMethod().getName());
        return object;
    }

    protected void configure() {
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(InjectContext.class), this);
    }
}
