package org.competition.programming.view;

public class MenuView extends WindowView {

    public static void menu() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Programming Competition--", marginLeft, marginTop);
        write("0. Exit Application", marginLeft);
        write("1. Log In", marginLeft);
        write("2. Ranking list", marginLeft);

        int opcija;
        int br = 0;
        do {
            if (br != 0) write("You have entered the wrong option!", marginLeft);
            write("Option: ", marginLeft, 's');
            opcija = (int) input("ni");
            br++;

        } while (opcija > 2 || opcija < 0);

        switch (opcija) {
            case 0:
                exitApplication();
                break;
            case 1:
                clearWindow();
                LoginView.menu();
                break;
            case 2:
                clearWindow();
                CommissionView.rankingList();
                break;
        }
    }

    public static void main(String[] args) {
        MenuView.menu();
    }
}
