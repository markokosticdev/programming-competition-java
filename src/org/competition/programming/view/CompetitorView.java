package org.competition.programming.view;

import org.competition.programming.model.CompetitorModel;

public class CompetitorView extends WindowView {

    public static void menu() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--CompetitorModel--", marginLeft, marginTop);
        write();

        CompetitorModel competitorModel = (CompetitorModel) LoginView.getUserModel();

        if (competitorModel.getWorkModel().isHanded()) {

            if (competitorModel.getWorkModel().isEstimated()) {
                write("Your work is estimated", marginLeft);
                write();
                write("Quality: ", marginLeft, 's');
                write(competitorModel.getWorkModel().getQuality() + "");

                write("Code Accuracy: ", marginLeft, 's');
                write(competitorModel.getWorkModel().getCodeAccuracy() + "");

                write("Overall Impression: ", marginLeft, 's');
                write(competitorModel.getWorkModel().getOverallImpression() + "");

                write("Average Mark: ", marginLeft, 's');
                write(competitorModel.getWorkModel().getAverageMark() + "");
                write();

                if (!competitorModel.getWorkModel().getComment().equals("")) {
                    write("Comment: ", marginLeft, 's');
                    write(competitorModel.getWorkModel().getComment() + "");
                }
                write();
                write("0. Log out", marginLeft);

                int option;
                int n = 0;

                do {
                    write();
                    if (n != 0) write("You have entered the wrong option!", marginLeft);
                    write("Option: ", marginLeft, 's');
                    option = (int) input("ni");
                    n++;

                } while (option > 0 || option < 0);

                clearWindow();
                LoginView.logout();
                MenuView.menu();
            } else {
                write("Your work is not yet estimated", marginLeft);
                write();
                write("0. Log out", marginLeft);

                int option;
                int n = 0;

                do {
                    write();
                    if (n != 0) write("You have entered the wrong option!", marginLeft);
                    write("Option: ", marginLeft, 's');
                    option = (int) input("ni");
                    n++;

                } while (option > 0 || option < 0);

                clearWindow();
                LoginView.logout();
                MenuView.menu();
            }

        } else {
            write("0. Log out", marginLeft);
            write("1. Hand work", marginLeft);

            int option;
            int n = 0;

            do {
                write();
                if (n != 0) write("You have entered the wrong option!", marginLeft);
                write("Option: ", marginLeft, 's');
                option = (int) input("ni");
                n++;

            } while (option > 1 || option < 0);

            switch (option) {
                case 0:
                    clearWindow();
                    LoginView.logout();
                    MenuView.menu();
                    break;
                case 1:
                    clearWindow();
                    handWork();
                    break;
            }

        }
    }

    private static void handWork() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Hand work--", marginLeft, marginTop);
        write("Enter the address and press enter or exit by entering 0 (zero)", marginLeft);

        CompetitorModel competitorModel = (CompetitorModel) LoginView.getUserModel();

        String file = null;
        int n = 0;

        do {
            write();
            if (n != 0) write("File does not exist on this path!", marginLeft);
            write("Path to file: ", marginLeft, 's');
            file = (String) input("nl");
            if (file != null && file.charAt(0) == '0') break;
            n++;

        } while (!competitorModel.getWorkModel().addWork(file));

        if (file != null && file.charAt(0) == '0') {
            clearWindow();
            menu();
        } else {
            clearWindow();
            LoginView.logout();
            MenuView.menu();
        }
    }
}
