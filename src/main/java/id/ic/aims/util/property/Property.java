package id.ic.aims.util.property;

public class Property {

    private Property() {}

    // ENV PROPERTY
    // APP
    public static final String APP_CONTEXT_FQDN = "context.fqdn";
    public static final String APP_CONTEXT_PATH = "context.path";
    public static final String APP_SERVER_KEY = "app.server.key";
    public static final String APP_LOGIN_URL = "app.login.url";
    public static final String APP_CONTEXT_REGISTRATION_PATH = "context.registration.path";

    public static final String USER_MAX_IDLE_TIMEOUT = "user.max.idle.time-out";

    // DB
    public static final String DB_JDBC_URL = "db.jdbc.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_POOL_SIZE = "db.pool-size";
    public static final String DB_DRIVER_CLASSNAME = "db.driver.className";
    public static final String DB_TYPE = "db.type";

    public static final String COUNTRY_XLS_FILE = "country.xls.source-file";

    //Proxy config
    public static final String PROXY_ENABLE = "proxy.enable";
    public static final String PROXY_EXCLUDE = "proxy.exclude";
    public static final String PROXY_HOST = "proxy.host";
    public static final String PROXY_PORT = "proxy.port";
    public static final String PROXY_SCHEMA = "proxy.scheme";

    //User field max length validation
    public static final String MAX_USER_LASTNAME_LENGTH = "user.lastname.max-length";
    public static final String MAX_USER_FULLNAME_LENGTH = "user.fullname.max-length";

}
