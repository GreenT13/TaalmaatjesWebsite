package com.apon.shiro;

import com.apon.log.MyLogger;
import com.apon.util.MessageResource;
import org.apache.shiro.authc.*;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.IOException;

public class MyBasicHttpAuthenticationFilter extends BasicHttpAuthenticationFilter {
    private final static String ERROR_ATTRIBUTE = "ERROR";
    private final static Integer UNKNOWN_ERROR = 0;
    private final static Integer UNKNOWN_USER = 1;
    private final static Integer WRONG_PASSWORD = 2;
    private final static Integer LOCKED_USER = 3;
    private final static Integer EXPIRED_USER = 4;

    /**
     * Change the name of the authentication header to avoid the login popup from browsers. Also set the error message
     * depending on the exception in {@link MyBasicHttpAuthenticationFilter#onLoginFailure}
     * @param request Used to identify error situation.
     * @param response Response that will go back to the user.
     * @return false
     */
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String authcHeader = "my-basic realm=\"" + getApplicationName() + "\"";
        httpServletResponse.setHeader(AUTHENTICATE_HEADER, authcHeader);

        // Determine the error message.
        String errorMessage;
        if (request.getAttribute(ERROR_ATTRIBUTE) == null) {
            errorMessage = MessageResource.getInstance().getValue("Shiro.authenticationError.unkownError");
        } else if (Integer.valueOf(request.getAttribute(ERROR_ATTRIBUTE).toString()).equals(UNKNOWN_ERROR)) {
            errorMessage = MessageResource.getInstance().getValue("Shiro.authenticationError.error");
        } else if (Integer.valueOf(request.getAttribute(ERROR_ATTRIBUTE).toString()).equals(UNKNOWN_USER)) {
            errorMessage = MessageResource.getInstance().getValue("Shiro.authenticationError.unkownUser");
        } else if (Integer.valueOf(request.getAttribute(ERROR_ATTRIBUTE).toString()).equals(WRONG_PASSWORD)) {
            errorMessage = MessageResource.getInstance().getValue("Shiro.authenticationError.unknownPassword");
        } else if (Integer.valueOf(request.getAttribute(ERROR_ATTRIBUTE).toString()).equals(LOCKED_USER)) {
            errorMessage = MessageResource.getInstance().getValue("Shiro.authenticationError.lockedUser");
        } else if (Integer.valueOf(request.getAttribute(ERROR_ATTRIBUTE).toString()).equals(EXPIRED_USER)) {
            errorMessage = MessageResource.getInstance().getValue("Shiro.authenticationError.expiredUser");
        } else {
            errorMessage = MessageResource.getInstance().getValue("Shiro.authenticationError.unkownError");
        }

        // Set the message
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON);
        try {
            httpServletResponse.getWriter().write("{ \"title\":\"" + errorMessage + "\" }");
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
        } catch (IOException e) {
            MyLogger.logError("Cannot write exception for Shiro.", e);
        }

        return false;
    }

    /**
     * {@inheritDoc}
     * I put some error code on the response, on which the response message back is conditioned in
     * {@link MyBasicHttpAuthenticationFilter#sendChallenge}.
     * @param token Unused.
     * @param e Base for case.
     * @param request Used to transfer information.
     * @param response Unused
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        Integer errorCode = -1;
        if (e instanceof UnknownAccountException) {
            errorCode = UNKNOWN_USER;
        } else if (e instanceof IncorrectCredentialsException) {
            errorCode = WRONG_PASSWORD;
        } else if (e instanceof LockedAccountException) {
            errorCode = LOCKED_USER;
        } else if (e instanceof  ExpiredCredentialsException) {
            errorCode = EXPIRED_USER;
        } else if (e != null) {
            errorCode = UNKNOWN_ERROR;
        }

        request.setAttribute(ERROR_ATTRIBUTE, errorCode);
        return false;
    }



}
