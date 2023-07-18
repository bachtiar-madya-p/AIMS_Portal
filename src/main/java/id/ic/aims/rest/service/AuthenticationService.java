package id.ic.aims.rest.service;

import id.ic.aims.controller.RoleController;
import id.ic.aims.controller.UserAuthenticationController;
import id.ic.aims.controller.UserController;
import id.ic.aims.controller.UserRoleController;
import id.ic.aims.manager.EncryptionManager;
import id.ic.aims.model.AuthenticateUser;
import id.ic.aims.model.Role;
import id.ic.aims.model.User;
import id.ic.aims.model.UserRole;
import id.ic.aims.rest.model.LoginRequest;
import id.ic.aims.rest.validator.LoginValidator;
import id.ic.aims.util.date.DateHelper;
import id.ic.aims.util.json.JsonHelper;
import id.ic.aims.util.property.Constant;
import id.ic.aims.util.property.Property;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Singleton
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationService extends BaseService {

    @Inject
    UserController userController;

    @Inject
    UserAuthenticationController authenticationController;

    @Inject
    UserRoleController userRoleController;

    @Inject
    RoleController roleController;

    private LoginValidator loginValidator;

    public AuthenticationService() {
        log = getLogger(this.getClass());
        loginValidator = new LoginValidator();
    }

    @POST
    @Path("login")
    public Response login(LoginRequest request) {
        final String methodName = "login";
        log.debug(methodName, "POST /auth/login");
        start(methodName);
        Response response = buildBadRequestResponse();
        if (loginValidator.validate(request)) {
            boolean emailExist = userController.validateEmail(request.getEmail());
            log.debug(methodName, "Validate email : " + emailExist);
            if (emailExist) {
                User user = userController.getUserInfoByEmail(request.getEmail());

                if (user.isActive()) {
                    String salt = authenticationController.getUserSalt(user.getUserid());
                    String hashPassword = EncryptionManager.getInstance().hash(request.getPassword(), salt);

                    AuthenticateUser authenticate = authenticationController.authenticateUser(user.getUserid(), hashPassword);
                    if (authenticate != null) {
                        if(!authenticate.isLocked()) {
                            user.setLocked(authenticate.isLocked());
                            user.setLastLogin(authenticate.getLastLogin());
                            setSessionAttribute(Constant.SESSION_USER_DETAILS, user);

                            List<UserRole> userRoles = userRoleController.getUserRoles(user.getUserid());
                            StringBuilder roles = new StringBuilder();
                            for(UserRole userRole : userRoles)
                            {
                                Role role = roleController.getRoles(userRole.getRoleId());
                                roles.append(role.getRole());
                                roles.append("-");
                            }
                            log.debug(methodName, "User role : " + roles);

                            LocalDateTime ldt = LocalDateTime.now();
                            ldt = ldt.plusMinutes(getIntProperty(Property.USER_MAX_IDLE_TIMEOUT));
                            String maxIdleTimeOut = DateHelper.formatDateTime(ldt);
                            setSessionAttribute(Constant.SESSION_MAX_IDLE_TIMEOUT, maxIdleTimeOut);

                            authenticationController.updateLoginTimestamp(user.getUserid());

                            UriBuilder builder = UriBuilder.fromPath(httpServletRequest.getContextPath());
                            builder.path("index");
                            Cookie cookie = new Cookie("roles", Base64.getEncoder().encodeToString(roles.toString().getBytes()));
                            cookie.setPath(Constant.BASE_DOMAIN);
                            cookie.setHttpOnly(false);
                            httpServletResponse.addCookie(cookie);
                            response = Response.seeOther(builder.build()).build();

                        }else {
                            response = buildBadRequestResponse("Locked user, please contact to Administrator");
                        }
                    } else {
                        response = buildBadRequestResponse("Invalid password");
                    }
                } else {
                    response = buildBadRequestResponse("Inactive user, please contact to Administrator");
                }
            }
        }
        completed(methodName);
        return response;
    }

    @GET
    @Path("user")
    @PermitAll
    public Response getUserLoginInfo() {
        final String methodName = "getUserLoginInfo";
        log.debug(methodName, "GET /auth/user");
        start(methodName);

        User user = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);
        log.debug(methodName, JsonHelper.toJson(user));

        completed(methodName);
        return buildSuccessResponse(user);
    }

    @GET
    @Path("logout")
    @PermitAll
    public Response logout() {
        final String methodName = "logout";
        log.debug(methodName, "GET /auth/logout");
        start(methodName);

        Response response = buildBadRequestResponse();

        User user = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);
        if (user != null) {
            HttpSession session = getSession();
            if (session != null) {
                session.invalidate();
            }
            response = buildSuccessResponse();
        }

        /*String idToken = getSessionAttribute(Constant.SESSION_ID_TOKEN, String.class);

        String auditMsg = buildAuditMessage("logout", getSessionUid(), getSessionUsername());
        auditUser(AuditConstant.LOGOUT, AuditConstant.LOGOUT, auditMsg, true);

        clearSession();
        // Redirect to end_session
        Response response = Response
                .temporaryRedirect(
                        URI.create(getProperty(Property.OAUTH_URL) + getProperty(Property.OAUTH_LOGOUT_URL)
                                + "?id_token_hint=" + idToken + "&post_logout_redirect_uri="
                                + getProperty(Property.APP_CONTEXT_FQDN) + getProperty(Property.APP_CONTEXT_PATH)))
                .build();
*/
        completed(methodName);
        return response;
    }
}
