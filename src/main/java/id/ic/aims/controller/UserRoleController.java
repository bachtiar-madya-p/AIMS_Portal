package id.ic.aims.controller;

import id.ic.aims.model.UserRole;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserRoleController extends BaseController{

    public UserRoleController() {
        log = getLogger(this.getClass());
    }

    public List<UserRole> getUserRoles(String userId)
    {
        final String methodName = "getUserRoles";
        start(methodName);
        List<UserRole> userRoles = new ArrayList<>();

        String sql = "SELECT roleId FROM aims_db.user_roles WHERE userid = :userId;";

        try(Handle handle = getHandle() ; Query q = handle.createQuery(sql))
        {
            q.bind("userId", userId);
            userRoles = q.mapToBean(UserRole.class).list();

        } catch (SQLException e) {
            log.error(methodName, e.getMessage());
        }
        completed(methodName);
        return userRoles;
    }
}
