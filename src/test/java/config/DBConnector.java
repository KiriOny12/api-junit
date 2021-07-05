package config;

import org.junit.After;

import java.sql.*;


public class DBConnector {
    public ResourcesConnector resources = new ResourcesConnector();
    private Connection con;
    private Statement stmt;
    private  ResultSet result;


    public ResultSet getDBConnResult(String sql){
        return setUp(sql);
    }


    private ResultSet setUp(String sql) {

        try {
            String dbClass = "com.mysql.cj.jdbc.Driver";
            Class.forName(dbClass).newInstance();

            con = DriverManager.getConnection(
                    resources.getProperty("db", "db.kievphp.host"),
                    resources.getProperty("db", "db.kievphp.user"),
                    resources.getProperty("db", "db.kievphp.password")
            );

            // Statement object to send the SQL statement to the Database
            stmt = con.createStatement();
            result = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println("Wrong SQL");
        } catch (ClassNotFoundException e) {
            System.out.println("Problem in driver");
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.println("Problem in newInstance()");
        }
        return result;
    }


    @After
    public void closeConn(){
        System.out.println("close conn");
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                System.out.println("Problem in closeConn(), result.close()");
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Problem in closeConn(), stmt.close()");
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Problem in closeConn(), con.close()");
            }
        }
    }
}
