package id.ic.aims.rest.service;

import id.ic.aims.manager.PropertyManager;
import id.ic.aims.rest.model.ServiceResponse;
import id.ic.aims.util.log.AppLogger;
import id.ic.aims.util.property.Constant;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class BaseService {

    @Context
    protected HttpServletRequest httpServletRequest;

    @Context
    protected HttpServletResponse httpServletResponse;

    protected AppLogger log;

    @Inject
    protected ExecutorService executor;

    public BaseService() {
        // Empty Constructor
    }

    protected AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }

    protected Response buildResponse(Status status) {
        return Response.status(status).entity(new ServiceResponse(status)).build();
    }

    protected Response buildResponse(Status status, String description) {
        return buildResponse(status, new ServiceResponse(status, description));
    }

    protected Response buildResponse(Status status, Object entity) {
        return Response.status(status).entity(entity).build();
    }

    protected Response buildRedirectResponse(String url) {
        return Response.seeOther(URI.create(url)).build();
    }

    protected Response buildBadRequestResponse() {
        return buildResponse(Status.BAD_REQUEST);
    }

    protected Response buildBadRequestResponse(String message) {
        return buildResponse(Status.BAD_REQUEST, message);
    }

    protected Response buildSuccessResponse() {
        return buildResponse(Status.OK);
    }

    protected Response buildConflictResponse() {
        return buildResponse(Status.CONFLICT);
    }

    protected Response buildConflictResponse(String message) {
        return buildResponse(Status.CONFLICT, message);
    }

    protected Response buildForbiddenResponse() {
        return buildResponse(Status.FORBIDDEN);
    }

    protected Response buildForbiddenResponse(String message) {
        return buildResponse(Status.FORBIDDEN, message);
    }

    protected Response buildSuccessResponse(NewCookie cookie) {
        return Response.ok().cookie(cookie).build();
    }

    protected Response buildSuccessResponse(Object obj) {
        return buildResponse(Status.OK, obj);
    }

    protected Response buildErrorResponse() {
        return buildResponse(Status.INTERNAL_SERVER_ERROR);
    }

    protected Response buildUnauthorizedResponse() {
        return buildResponse(Status.UNAUTHORIZED);
    }

    protected Response buildRedirectResponseWithErrorMsg(String url, String message) {
        String methodName = "buildRedirectResponseWithErrorMsg";
        String urlWithMsg = url;
        try {
            String encodedMsg = URLEncoder.encode(message, "UTF-8").replace("+", "%20");
            log.debug(methodName, "URL: " + encodedMsg);
            urlWithMsg = url + "?message=" + encodedMsg;
        } catch (UnsupportedEncodingException e) {
            log.debug(methodName, "message: " + message);
            log.error(methodName, "URL Encoding failed, redirecting back to index without message");
        }
        return buildRedirectResponse(urlWithMsg);

    }

    protected boolean hasSession() {
        return getSession() != null;
    }

    protected boolean hasSessionAttribute(String key) {
        return getSession() != null && getSession().getAttribute(key) != null;
    }

    protected HttpSession getSession() {
        return httpServletRequest.getSession(false);
    }

    protected void setSessionAttribute(String key, Object value) {
        HttpSession session;
        if (!hasSession()) {
            session = httpServletRequest.getSession(true);
        } else {
            session = getSession();
        }
        session.setAttribute(key, value);
    }

    protected <T> T getSessionAttribute(String key, Class<T> clazz) {
        if (hasSession() && getSession().getAttribute(key) != null) {
            return clazz.cast(getSession().getAttribute(key));
        }
        return null;
    }

    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    protected boolean getBooleanProperty(String key) {
        return PropertyManager.getInstance().getBooleanProperty(key);
    }

    protected int getIntProperty(String key) {
        return PropertyManager.getInstance().getIntProperty(key);
    }

    protected String getTrackingID() {
        return getSessionAttribute(Constant.SESSION_TRACKING_ID, String.class);
    }

    protected void setTrackingID(String trackingId) {
        setSessionAttribute(Constant.SESSION_TRACKING_ID, UUID.randomUUID().toString());
    }

    protected String generateTrackingID() {
        return UUID.randomUUID().toString();
    }
}
