package id.ic.aims.controller;

import id.ic.aims.model.AuthenticateUser;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

@Controller
public class UserAuthenticationController extends BaseController {

    private static final String BASE_SELECT_QUERY = "SELECT userid, salt, password, locked, createDt, lastLogin " +
            "FROM aims_db.users_authentication ";

    public UserAuthenticationController() {
        log = getLogger(this.getClass());
    }

    public String getUserSalt(String userid) {
        final String methodName = "getUserSalt";
        start(methodName);
        String result = null;

        String sql = "SELECT salt FROM users_authentication WHERE userid = :userid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("userid", userid);
            result = q.mapTo(String.class).one();

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public AuthenticateUser authenticateUser(String userid, String hashedPassword) {
        final String methodName = "getUserSalt";
        start(methodName);
        AuthenticateUser result = null;

        String sql = BASE_SELECT_QUERY + " WHERE userid = :userid AND password = :password;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("userid", userid);
            q.bind("password", hashedPassword);

            result = q.mapToBean(AuthenticateUser.class).one();

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public boolean updateLoginTimestamp(String userId) {
        final String methodName = "updateLoginTimestamp";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE aims_db.users_authentication " +
                "SET lastLogin=CURRENT_TIMESTAMP " +
                "WHERE userid= :userId;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bind("userId", userId);

            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }
}
