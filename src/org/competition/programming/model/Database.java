package org.competition.programming.model;

import java.sql.*;

public class Database {

    public boolean test = false;
    private Connection connection;
    private static Database instance;

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:res/db/programming_competition.db");
            if (test) System.out.println("Successful connected to Database!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String sql) {

        if (test) System.out.println(sql);
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int update(String sql) {

        if (test) System.out.println(sql);
        int resultSet = 0;
        try {
            Statement st = connection.createStatement();
            resultSet = st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static Database getInstance() {
        if (Database.instance == null)
            Database.instance = new Database();
        return Database.instance;
    }

    @Override
    protected Database clone() throws CloneNotSupportedException {
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
