package org.competition.programming.model;

import java.sql.*;

public abstract class UserModel {

    protected String username;
    protected String password;
    protected Type type;
    protected int idUser;
    protected static int numberOfUsers = 0;

    protected UserModel(String username, String password, Type type) {

        this.username = username;
        this.password = password;
        this.type = type;
    }

    public static UserModel getUser(String username, String password) {

        UserModel userModel = null;
        Type type = getType(username);
        if (type == Type.COMPETITOR) userModel = CompetitorModel.getUser(username, password);
        else if (type == Type.COMMISSION) userModel = CommissionModel.getUser(username, password);
        else if (type == Type.ADMINISTRATOR) userModel = AdministratorModel.getUser(username, password);
        return userModel;
    }

    public static UserModel getUser(int idUser) {

        UserModel userModel = null;
        Type type = getType(idUser);
        if (type == Type.COMPETITOR) userModel = CompetitorModel.getUser(idUser);
        else if (type == Type.COMMISSION) userModel = CommissionModel.getUser(idUser);
        else if (type == Type.ADMINISTRATOR) userModel = AdministratorModel.getUser(idUser);
        return userModel;
    }

    private static Type getType(String username) {


        Database database = Database.getInstance();

        try (ResultSet resultSet = database.query("SELECT type FROM user WHERE username='" + username + "';")) {
            return Type.getType(resultSet.getInt("type"));

        } catch (Exception e) {
            System.out.println("getType(int idUser)");
            e.printStackTrace();
        }

        return null;
    }

    private static Type getType(int idUser) {
        Database con = Database.getInstance();

        try (ResultSet rs = con.query("SELECT type FROM user WHERE idUser='" + idUser + "';")) {
            return Type.getType(rs.getInt("type"));

        } catch (Exception e) {
            System.out.println("getType(int idUser)");
            e.printStackTrace();
        }

        return Type.ADMINISTRATOR;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    protected static int getNumberOfUsers() {
        return numberOfUsers;
    }

    protected static void setNumberOfUsers(int numberOfUsers) {
        UserModel.numberOfUsers = numberOfUsers;
    }

    public void resetNumberOfUsers() {
        numberOfUsers = 0;
    }


    protected static boolean isExist(String username, String password) {

        if (username == null || username.length() < 1 || password == null || password.length() < 1) return false;

        Database database = Database.getInstance();

        try (ResultSet resultSet = database.query("SELECT idUser FROM user WHERE username='" + username + "' AND password='" + password + "';")) {
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("isExist(String username, String password)");
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isExist(String username) {

        if (username == null || username.length() < 1) return false;

        Database database = Database.getInstance();

        try (ResultSet resultSet = database.query("SELECT idUser FROM user WHERE username='" + username + "';")) {
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (Exception e) {
            System.out.println("isExist(String username)");
            e.printStackTrace();
        }
        return true;
    }

    protected void load(String username, String password) {

        Database database = Database.getInstance();

        try (ResultSet resultSet = database.query("SELECT idUser FROM user WHERE username='" + username + "' AND password='" + password + "';")) {
            this.idUser = resultSet.getInt("idUser");
        } catch (Exception e) {
            System.out.println("load(String username, String password)");
            e.printStackTrace();
        }
    }

    public static boolean validType(Type type) {
        return type == Type.COMPETITOR || type == Type.COMMISSION || type == Type.ADMINISTRATOR;
    }

    public static int getUserId(String username) {
        if (username == null || username.length() < 1) return 0;

        Database database = Database.getInstance();
        try (ResultSet resultSet = database.query("SELECT idUser FROM user WHERE username='" + username + "';")) {
            return resultSet.getInt("idUser");
        } catch (Exception e) {
            System.out.println("getUserId(String username)");
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean deleteWorkAndUser(String username) {

        deleteWork(username);
        return deleteUser(username);
    }

    public static boolean deleteUser(String username) {
        if (username == null || username.length() < 1) return false;

        Database database = Database.getInstance();
        int resultSet = database.update("DELETE FROM user WHERE username='" + username + "';");

        try {
            if (resultSet == 0) return false;
            else return true;

        } catch (Exception e) {
            System.out.println("deleteUser(String username)");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteWork(String username) {
        if (username == null || username.length() < 1) return false;

        Database con = Database.getInstance();
        int rs = con.update("DELETE FROM work WHERE idCompetitor='" + getUserId(username) + "';");

        try {
            if (rs == 0) return false;
            else return true;

        } catch (Exception e) {
            System.out.println("deleteWork(String username)");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addUser(String username, String password, Type type) {
        if (username == null || password == null || username.length() < 1 || password.length() < 1 || !validType(type))
            return false;

        Database con = Database.getInstance();
        int rs = con.update("INSERT INTO user (username, password, type) VALUES ('" + username + "','" + password + "','" + type.getTypeCode() + "');");

        try {
            if (rs == 0) return false;
            else return true;

        } catch (Exception e) {
            System.out.println("addUser(String username,String password,Type type)");
            e.printStackTrace();
        }
        return false;
    }
}