package org.competition.programming.view;

import org.competition.programming.model.Type;
import org.competition.programming.model.UserModel;

public class AdministratorView extends WindowView {

    public static void menu() {

        int marginLeft=20;
        int marginTop=5;

        write("--AdministratorModel--",marginLeft,marginTop);
        write("0. Log Out",marginLeft);
        write("1. Add CompetitorModel",marginLeft);
        write("2. Add CommissionModel",marginLeft);
        write("3. Delete UserModel",marginLeft);

        int option;
        int n=0;

        do{
            write();
            if(n!=0) write("You have entered the wrong option!",marginLeft);
            write("Option: ",marginLeft,'s');
            option= (int) input("ni");
            n++;

        }while(option>3 || option<0);

        switch (option){
            case 0:
                clearWindow();
                LoginView.logout();
                MenuView.menu();
                break;
            case 1:
                clearWindow();
                addCompetitor();
                break;
            case 2:
                clearWindow();
                addCommission();
                break;
            case 3:
                clearWindow();
                deleteUser();
                break;
        }
    }

    private static void addCompetitor() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Add CompetitorModel--", marginLeft, marginTop);
        write();
        write("Enter the name of the competitor and press enter or exit by entering 0 (zero)", marginLeft);

        String username = null;
        String password = null;
        int n = 0;

        do {
            write();
            if (n != 0) write("You have entered the wrong data or a competitor already exists!", marginLeft);
            write("Username: ", marginLeft, 's');
            username = (String) input("nl");
            write();
            write("Password: ", marginLeft, 's');
            password = (String) input("nl");
            if (username != null && username.length() >= 1  && username.charAt(0) == '0') break;
            n++;

        } while (!(username != null && username.length() >= 1) || UserModel.isExist(username));

        if (username.charAt(0) == '0' || UserModel.addUser(username, password, Type.COMPETITOR)) {
            clearWindow();
            menu();
        }
    }

    private static void addCommission() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Add CommissionModel--", marginLeft, marginTop);
        write();
        write("Enter the name of the competitor and press enter or exit by entering 0 (zero)", marginLeft);

        String username = null;
        String password = null;
        int n = 0;

        do {
            write();
            if (n != 0) write("You have entered the wrong data or a competitor already exists!", marginLeft);
            write("Username: ", marginLeft, 's');
            username = (String) input("nl");
            write();
            write("Password: ", marginLeft, 's');
            password = (String) input("nl");
            if (username != null && username.length() >= 1  && username.charAt(0) == '0') break;
            n++;

        } while (!(username != null && username.length() >= 1) || UserModel.isExist(username));

        if (username.charAt(0) == '0' || UserModel.addUser(username, password, Type.COMMISSION)) {
            clearWindow();
            menu();
        }
    }

    private static void deleteUser() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Delete UserModel--", marginLeft, marginTop);
        write();
        write("Enter the name of the competitor and press enter or exit by entering 0 (zero)", marginLeft);

        String username;
        int n = 0;

        do {
            write();
            if (n != 0) write("No existing competitor!", marginLeft);
            write("Username: ", marginLeft, 's');
            username = (String) input("nl");
            if (username != null && username.length() >= 1 && username.charAt(0) == '0') break;
            n++;

        } while (!(username != null && username.length() >= 1) || !UserModel.isExist(username));

        if (username.charAt(0) == '0' || (UserModel.deleteWorkAndUser(username))) {
            clearWindow();
            menu();
        }
    }
}
