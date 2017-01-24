package org.competition.programming.model;

import java.sql.ResultSet;

public class AdministratorModel extends UserModel {

    private AdministratorModel(String username, String password) {
        super(username, password, Type.ADMINISTRATOR);
        super.load(this.username, this.password);
    }

    public static UserModel getUser(String username, String password) {

        if (getNumberOfUsers() < 1 && isExist(username, password)) {
            setNumberOfUsers(getNumberOfUsers() + 1);
            return new AdministratorModel(username, password);
        }
        return null;
    }

    public static UserModel getUser(int idUser) {

        Database database = Database.getInstance();

        try (ResultSet resultSet = database.query("SELECT username,password FROM user WHERE idUser='" + idUser + "';")) {
            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                return new AdministratorModel(resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("getUserModel(int idUser)");
            e.printStackTrace();
        }
        return null;
    }
}
