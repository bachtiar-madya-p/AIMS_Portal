package id.ic.aims.controller;

import id.ic.aims.manager.ConnectionManager;
import id.ic.aims.manager.PropertyManager;
import id.ic.aims.util.log.AppLogger;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

public class BaseController {

    protected AppLogger log;

    public BaseController() {
        // Empty Constructor
    }

    protected <T> AppLogger getLogger(Class<T> clazz) {
        return new AppLogger(clazz);
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }

    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    protected int getIntProperty(String key) {
        return PropertyManager.getInstance().getIntProperty(key);
    }

    protected boolean getBoolProperty(String key) {
        return PropertyManager.getInstance().getBooleanProperty(key);
    }

    protected Handle getHandle() throws SQLException {
        return Jdbi.open(ConnectionManager.getInstance().getDataSource());
    }

    protected Handle getHandle(RowMapper<?>... rowMappers) throws SQLException {
        Handle h = getHandle();

        if (rowMappers != null && rowMappers.length > 0) {
            for (RowMapper<?> mapper : rowMappers) {
                h.registerRowMapper(mapper);
            }
        }
        return h;
    }

    protected boolean executeUpdate(Update update) {
        return update.execute() > 0;
    }

    protected int executeAndGetId(Update update) {
        return update.executeAndReturnGeneratedKeys().mapTo(Integer.class).one();
    }

    protected boolean executeBatch(PreparedBatch batch) {
        int[] resultArr = batch.execute();

        for (int result : resultArr) {
            if (result < 0) {
                return false;
            }
        }
        return true;
    }
}
