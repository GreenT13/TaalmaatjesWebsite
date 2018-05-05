package com.apon.filter;

import com.apon.log.MyLogger;
import com.apon.resteasy.Filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

@SuppressWarnings("unused")
@Filter
public class RequestFilter implements ContainerRequestFilter {

    public void filter(ContainerRequestContext containerRequestContext) {
        //MyLogger.logDebug("Look at my filter woop.");
    }
}
