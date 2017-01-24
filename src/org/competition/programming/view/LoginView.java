package org.competition.programming.view;

import org.competition.programming.model.UserModel;

public class LoginView extends WindowView {

    private static UserModel userModel;

    public static UserModel getUserModel() {
        return userModel;
    }

    public static void setUserModel(UserModel userModel) {
        LoginView.userModel = userModel;
    }

    public static void logout() {
        userModel.resetNumberOfUsers();
        setUserModel(null);
    }

    public static void menu() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Log In--", marginLeft, marginTop);
        write("Enter your username and password", marginLeft);

        String username = null;
        String password = null;
        int n = 0;

        do {
            if (n == 3) exitApplication();
            write();
            if (n != 0) write("You have entered the wrong name or password!", marginLeft);
            write("Username: ", marginLeft, 's');
            username = (String) input("nl");
            write();
            write("Password: ", marginLeft, 's');
            password = (String) input("nl");
            write();
            if (username != null && password != null && UserModel.isExist(username)){
                userModel = UserModel.getUser(username, password);
                System.out.println("ja sam");}
            else userModel = null;
            n++;

        } while (userModel == null);


        switch (userModel.getType()) {
            case COMPETITOR:
                clearWindow();
                CompetitorView.menu();
                break;
            case COMMISSION:
                clearWindow();
                CommissionView.menu();
                break;
            case ADMINISTRATOR:
                clearWindow();
                AdministratorView.menu();
                break;
        }
    }
}
