package id.ic.aims.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import id.ic.aims.manager.ConnectionManager;
import id.ic.aims.manager.EncryptionManager;
import id.ic.aims.manager.PropertyManager;
import id.ic.aims.util.log.AppLogger;

@WebListener
public class ContextListener implements ServletContextListener {

    public final AppLogger log;

    public ContextListener() {
        log = new AppLogger(this.getClass());
    }

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("Application Init Started");
        PropertyManager.getInstance();
        EncryptionManager.getInstance();
        ConnectionManager.getInstance();
        log.info("Application Init Completed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent evt) {
        log.info("Application Shutdown Started");
        PropertyManager.getInstance().shutdown();
        EncryptionManager.getInstance().shutdown();
        ConnectionManager.getInstance().shutdown();
        log.info("Application Shutdown Completed");
    }
}
