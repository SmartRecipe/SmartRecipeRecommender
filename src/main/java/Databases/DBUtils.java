package Databases;

public class DBUtils {
    public static final int SSH_PORT = 22;

    public static final int DB_PORT_FORWARDING = 6666;

    public static final String ENV_SSH_KEY = System.getenv("SSH_KEY");
    public static final int ENV_DB_PORT = Integer.parseInt(System.getenv("DB_PORT"));
    public static final String ENV_DB_NAME = System.getenv("DB_NAME");
    public static final String ENV_DB_ADDRESS = System.getenv("DB_ADDRESS");
}