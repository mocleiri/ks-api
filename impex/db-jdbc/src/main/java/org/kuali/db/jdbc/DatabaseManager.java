package org.kuali.db.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatabaseManager {
    private static final Log log = LogFactory.getLog(DatabaseManager.class);
    Credentials credentials;
    Credentials dbaCredentials;
    DatabaseManagerMode mode;
    String url;
    JDBCUtils jdbcUtils;

    protected void executeDbaSql(final JDBCConfiguration config, final String sql) throws SQLException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.setCredentials(getDbaCredentials());
        connectionHandler.setUrl(getUrl());
        connectionHandler.setDriver(config.getDriver());
        Connection conn = null;
        try {
            conn = connectionHandler.getConnection();
            SQLExecutor executor = new SQLExecutor();
            executor.setConn(conn);
            executor.executeSql(sql);
        } finally {
            JDBCUtils.closeQuietly(conn);
        }
    }

    public void dropDatabase() throws SQLException {
        JDBCConfiguration config = getJdbcUtils().getDatabaseConfiguration(getUrl());
        String sql = config.getDbaSql().getDropSql();
        log.info("-- Dropping database --");
        executeDbaSql(config, sql);
    }

    public void createDatabase() throws SQLException {
        JDBCConfiguration config = getJdbcUtils().getDatabaseConfiguration(getUrl());
        String sql = config.getDbaSql().getCreateSql();
        log.info("-- Creating database --");
        executeDbaSql(config, sql);
    }

    /**
     * @return the credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * @param credentials
     * the credentials to set
     */
    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * @return the dbaCredentials
     */
    public Credentials getDbaCredentials() {
        return dbaCredentials;
    }

    /**
     * @param dbaCredentials
     * the dbaCredentials to set
     */
    public void setDbaCredentials(final Credentials dbaCredentials) {
        this.dbaCredentials = dbaCredentials;
    }

    /**
     * @return the mode
     */
    public DatabaseManagerMode getMode() {
        return mode;
    }

    /**
     * @param mode
     * the mode to set
     */
    public void setMode(final DatabaseManagerMode mode) {
        this.mode = mode;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     * the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the jdbcUtils
     */
    public JDBCUtils getJdbcUtils() {
        return jdbcUtils;
    }

    /**
     * @param jdbcUtils
     * the jdbcUtils to set
     */
    public void setJdbcUtils(final JDBCUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

}
