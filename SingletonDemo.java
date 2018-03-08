package com.abayomi.singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonDemo {

    private static volatile SingletonDemo instance = null;
    private static volatile Connection conn = null;

    private SingletonDemo() {

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (conn != null) {
            throw new RuntimeException("Use getConnection() method to create");
        }

        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to create");
        }
    }

    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }

        return instance;
    }

    public Connection getConnection() {
        if (conn == null) {
            synchronized (SingletonDemo.class) {
                if (conn == null) {
                    try {
                        String dbUrl = "jdbc:derby:memory:codejava/webdb;create=true";

                        conn = DriverManager.getConnection(dbUrl);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return conn;
    }
}
