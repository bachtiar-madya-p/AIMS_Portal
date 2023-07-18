package id.ic.aims.controller;

import id.ic.aims.model.Role;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.sql.SQLException;

@Controller
public class RoleController extends BaseController{

    public RoleController() {
        log = getLogger(this.getClass());
    }

    public Role getRoles(String roleId)
    {
        Role role = new Role();

        String sql = "SELECT role FROM roles WHERE uid = :uid;";

        try(Handle handle = getHandle() ; Query q = handle.createQuery(sql))
        {
            q.bind("uid", roleId);
            role = q.mapToBean(Role.class).one();

        } catch (SQLException e) {
            log.error("getRoles", e.getMessage());
        }
        return role;
    }
}
