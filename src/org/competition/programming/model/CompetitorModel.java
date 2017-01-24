package org.competition.programming.model;

import java.sql.ResultSet;

public class CompetitorModel extends UserModel {

    private WorkModel workModel;

    private CompetitorModel(String username, String password) {
        super(username, password, Type.COMPETITOR);
        super.load(this.username, this.password);
        this.workModel = new WorkModel(this);
    }

    public WorkModel getWorkModel() {
        return workModel;
    }

    public void setWorkModel(WorkModel workModel) {
        this.workModel = workModel;
    }

    public static UserModel getUser(String username, String password) {

        if (getNumberOfUsers() < 1 && isExist(username, password)) {
            setNumberOfUsers(getNumberOfUsers() + 1);
            return new CompetitorModel(username, password);
        }
        return null;
    }

    public static UserModel getUser(int idUser) {

        Database database = Database.getInstance();

        try (ResultSet resultSet = database.query("SELECT username,password FROM user WHERE idUser='" + idUser + "';")) {
            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                return new CompetitorModel(resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("getUserModel(int idUser)");
            e.printStackTrace();
        }
        return null;
    }
}