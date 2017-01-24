package org.competition.programming.model;

import java.sql.ResultSet;

public class CommissionModel extends UserModel {

    private CommissionModel(String username, String password) {
        super(username, password, Type.COMMISSION);
        super.load(this.username, this.password);
    }

    public static UserModel getUser(String username, String password) {

        if (getNumberOfUsers() < 1 && isExist(username, password)) {
            setNumberOfUsers(getNumberOfUsers() + 1);
            return new CommissionModel(username, password);
        }
        return null;
    }

    public static UserModel getUser(int idUser) {

        Database database = Database.getInstance();

        try (ResultSet resultSet = database.query("SELECT username,password FROM user WHERE idUser='" + idUser + "';")) {
            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                return new CommissionModel(resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("getUserModel(int idUser)");
            e.printStackTrace();
        }
        return null;
    }
}
