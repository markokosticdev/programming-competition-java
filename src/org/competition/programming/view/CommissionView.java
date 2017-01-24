package org.competition.programming.view;

import org.competition.programming.model.CompetitorModel;
import org.competition.programming.model.UserModel;
import org.competition.programming.model.WorkModel;

import java.util.ArrayList;

public class CommissionView extends WindowView {

    public static void menu() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--CommissionModel--", marginLeft, marginTop);
        write("0. Log Out", marginLeft);
        write("1. List of Works", marginLeft);
        write("2. Estimate WorkModel", marginLeft);

        int option;
        int n = 0;

        do {
            write();
            if (n != 0) write("You have entered the wrong option!", marginLeft);
            write("Option: ", marginLeft, 's');
            option = (int) input("ni");
            n++;

        } while (option > 2 || option < 0);

        switch (option) {
            case 0:
                clearWindow();
                LoginView.logout();
                MenuView.menu();
                break;
            case 1:
                clearWindow();
                worksList();
                break;
            case 2:
                clearWindow();
                estimateWork();
                break;
        }
    }

    private static void estimateWork() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Estimate work--", marginLeft, marginTop);
        write("Do you want to look at the list of works?(yY/nN): ", marginLeft, 's');
        String answer = (String) input("nl");
        if (answer != null && (answer.equals("y") || answer.equals("Y")))
            worksList();
        write();
        write("Enter the name of the competitorModel and press enter or exit by entering 0 (zero)", marginLeft);

        String name;
        int n = 0;

        do {
            write();
            if (n != 0) write("No existing competitorModel!", marginLeft);
            write("CompetitorModel name: ", marginLeft, 's');
            name = (String) input("nl");
            if (name != null && name.length() >= 1 && name.charAt(0) == '0') break;
            n++;

        } while (!(name != null && name.length() >= 1) || !CompetitorModel.isExist(name));

        if (name.charAt(0) == '0') {
            clearWindow();
            menu();
        }
        CompetitorModel competitorModel = (CompetitorModel) UserModel.getUser(CompetitorModel.getUserId(name));
        competitorModel.getWorkModel().load();
        write();


        boolean validFormat = true;
        String[] extensions = {".c", ".cpp", ".h", ".java", ".php", ".html", ".css", ".js"};
        if (WorkModel.isValidЕxtension(competitorModel.getWorkModel(), ".txt")) {
            write("WorkModel: " + competitorModel.getWorkModel().getFile(), marginLeft);
            write();
            writeMultiLine(competitorModel.getWorkModel().getText(), 160, marginLeft);
        } else if (WorkModel.isValidЕxtension(competitorModel.getWorkModel(), extensions)) {
            write("WorkModel: " + competitorModel.getWorkModel().getFile(), marginLeft);
            write();
            writeMultiLine(competitorModel.getWorkModel().getText(), marginLeft);
        } else {
            write("WorkModel is not in the correct format!", marginLeft);
            competitorModel.getWorkModel().estimate(0, 0, 0);
            competitorModel.getWorkModel().comment("WorkModel is not in the correct format!");
            validFormat = false;
        }
        if (validFormat) {
            write("Enter mark's:", marginLeft);

            int quality = 0;
            int codeAccuracy = 0;
            int overallImpression = 0;
            n = 0;

            do {
                write();
                if (n != 0) write("Mark's are not in the range!", marginLeft);
                write("Quality: ", marginLeft, 's');
                quality = (int) input("ni");
                write("Code Accuracy: ", marginLeft, 's');
                codeAccuracy = (int) input("ni");
                write("Overall Impression: ", marginLeft, 's');
                overallImpression = (int) input("ni");
                write();
                n++;
            } while (!competitorModel.getWorkModel().estimate(quality, codeAccuracy, overallImpression));

            write("Do you want to comment work?(yY/nN): ", marginLeft, 's');
            answer = (String) input("nl");
            if (answer != null && (answer.equals("y") || answer.equals("Y"))){
                write();
                write("Comment: ", marginLeft, 's');
                competitorModel.getWorkModel().comment((String) input("nl"));
                write();
            }
        }
        clearWindow();
        menu();
    }

    private static void worksList() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--List of Works--", marginLeft, marginTop);

        ArrayList<WorkModel> workModels = WorkModel.loadWorks('p');

        if (workModels != null) {
            write(marginLeft, 's');
            System.out.format("|%-15s|", "Competitors");

            write();
            write();
            for (WorkModel workModel : workModels) {
                write(marginLeft, 's');
                System.out.format("|%-15s|", workModel.getCompetitorModel().getUsername());
                write();
            }
        }

        write();
        write("0. Back to Menu", marginLeft);
        write("1. Estimate WorkModel", marginLeft);

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
                menu();
                break;
            case 1:
                clearWindow();
                estimateWork();
                break;
        }
    }

    public static void rankingList() {

        int marginLeft = 20;
        int marginTop = 5;

        write("--Ranking list--", marginLeft, marginTop);

        ArrayList<WorkModel> workModels = WorkModel.loadWorks('o');

        if (workModels != null) {
            write(marginLeft, 's');
            System.out.format("|%-15s|%-15s|%-15s|%-18s|%-15s|%-30s|", "Competitor", "Quality", "Code Accuracy", "Overall Impression", "Average Mark", "Comment");

            write();
            write();
            for (WorkModel workModel : workModels) {
                write(marginLeft, 's');

                System.out.format("|%-15s|%-15s|%-15s|%-18s|%-15s|%-30s|", workModel.getCompetitorModel().getUsername(), workModel.getQuality(), workModel.getCodeAccuracy(), workModel.getOverallImpression(), workModel.getAverageMark(), workModel.getComment());

                write();
            }
        }

        write();
        write("0. Back to Menu", marginLeft);


        int option;
        int n = 0;

        do {
            write();
            if (n != 0) write("You have entered the wrong option!", marginLeft);
            write("Option: ", marginLeft, 's');
            option = (int) input("ni");
            n++;

        } while (option > 0 || option < 0);

        switch (option) {
            case 0:
                clearWindow();
                MenuView.menu();
                break;
        }
    }

}