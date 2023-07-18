package id.ic.aims.controller;

import id.ic.aims.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.sql.SQLException;

@Controller
public class UserController extends BaseController {

    private static final String BASE_SELECT_USER = "SELECT userid, firstName, lastName, fullName, employeeId, gender, " +
            "email, mobile, active, createDt, modifyDt FROM aims_db.users ";

    public UserController() {
        log = getLogger(this.getClass());
    }

    public boolean validateEmail(String email) {
        final String methodName = "validateEmail";
        start(methodName);
        boolean result = false;

        String sql = "SELECT IF ( EXISTS ( SELECT * FROM users  WHERE LOWER(email) = :email ),'true','false' )AS result";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            result = q.mapTo(Boolean.class).one();

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return result;
    }

    public User getUserInfoByEmail(String email) {
        final String methodName = "getUserInfoByEmail";
        start(methodName);
        User user = null;

        String sql = BASE_SELECT_USER + " WHERE email = :email;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            user = q.mapToBean(User.class).first();

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return user;
    }
}
