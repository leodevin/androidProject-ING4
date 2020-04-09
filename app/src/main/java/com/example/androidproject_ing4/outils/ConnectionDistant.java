package com.example.androidproject_ing4.outils;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// ------ TEST ----- //

/*
public class ConnectionDistant {
    // Your IP address must be static otherwise this will not work. You //can get your Ip address
//From Network and security in Windows.
    String ip = "127.0.0.1";
    // This is default if you are using JTDS driver.
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    // Name Of your database.
    String db = "Coach";
    // Userame and password are required for security.
    // so Go to sql server and add username and password for your database.
    String un = "root";
    String password = "root";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
            Log.d("Connection", " OK !");
        }
        catch (SQLException se)
        {
            Log.e("safiya", se.getMessage());
        }
        catch (ClassNotFoundException e) {
        }
        catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return conn;
    }
}
*/

public class ConnectionDistant {

    private static String TAG = "Connection";

    private String dbms = "mysql";
    private String serverName = "localhost";
    private String portNumber = "8889";
    private String dbName = "Coach";

    private String userName = "root";
    private String password = "root";

    private static Statement stmt;

    private String url = "jdbc:mysql://localhost:8889/Coach";

    public Connection getConnection() throws SQLException {

        Connection conn = null;
        /*Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);*/

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            // création d'un ordre SQL (statement)
            stmt = conn.createStatement();
            Log.d(TAG, "Connection etablished 1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "Connection FAILED 1");
        }
        /*conn = DriverManager.getConnection(
                "jdbc:" + this.dbms + "://" +
                        this.serverName +
                        ":" + this.portNumber + "/" + this.dbName, this.userName, this.password);*/
        return conn;
    }
}
