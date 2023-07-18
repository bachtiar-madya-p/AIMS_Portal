package id.ic.aims.rest.service;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import id.ic.aims.manager.PropertyManager;
import id.ic.aims.util.property.Constant;
import id.ic.aims.util.property.Property;
import id.ic.aims.rest.model.ServiceResponse;
import id.ic.aims.util.helper.MyInfoCountiesMap;
import id.ic.aims.util.helper.MyInfoNationalityMap;
import id.ic.aims.util.log.AppLogger;

public class BaseService {

    @Context
    protected HttpServletRequest httpServletRequest;

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

    protected void setTrackingID() {
        setSessionAttribute(Constant.SESSION_TRACKING_ID, UUID.randomUUID().toString());
    }

    protected String mapNationality (String nationality) {
        String methodName = "mapNationality";
        start(methodName);
        String result = MyInfoNationalityMap.mapMyInfoNationality(nationality);
        log.debug(methodName, "Mapping MyInfo nationality : "+nationality+" to "+result);
        completed(methodName);
        return result;
    }

    protected String mapCountry(String country) {
        String methodName = "mapCountry";
        start(methodName);
        String result = MyInfoCountiesMap.mapMyInfoCountry(country);
        log.debug(methodName, "Mapping MyInfo country : "+country+" to "+result);
        completed(methodName);
        return result;
    }

    protected String trimString(String key, String str) {
        String methodName = "trimString";
        start(methodName);
        String result = str;
        boolean counterMaxLength = validateLength(key, str);
        int maxLength = getMaxLength(key);
        log.debug(methodName,key + " length ("+ str.length() + ") counter max length" + " ("+ maxLength + ") : " + counterMaxLength);

        if (counterMaxLength) {
            log.debug(methodName, "Trim max length to " + maxLength);
            result = str.substring(0, (maxLength - 3)) + "...";
        }

        completed(methodName);
        return result;
    }
    protected boolean validateLength(String key, String str) {
        int maxLength = getMaxLength(key);
        return str.length() > maxLength;
    }

    protected int getMaxLength(String key) {
        int result = 0;
        switch (key) {
            case Constant.KEY_FIRSTNAME:
                result = getIntProperty(Property.MAX_USER_FIRSTNAME_LENGTH);
                break;
            case Constant.KEY_LASTNAME:
                result = getIntProperty(Property.MAX_USER_LASTNAME_LENGTH);
                break;
            case Constant.KEY_FULLNAME:
                result = getIntProperty(Property.MAX_USER_FULLNAME_LENGTH);
                break;
            default:
                break;
        }
        return result;
    }
}
