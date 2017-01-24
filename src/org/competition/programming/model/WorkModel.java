package org.competition.programming.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class WorkModel {

    private CompetitorModel competitorModel;
    private String file;
    private double quality;
    private double codeAccuracy;
    private double overallImpression;
    private double averageMark;
    private String comment;

    public WorkModel(CompetitorModel competitorModel) {

        this.competitorModel = competitorModel;
        this.file = "";
        this.quality = 101;
        this.codeAccuracy = 101;
        this.overallImpression = 101;
        this.averageMark = 101;
        this.comment = "";
    }

    public WorkModel(CompetitorModel competitorModel, String file, double quality, double codeAccuracy, double overallImpression, double averageMark, String comment) {

        this.competitorModel = competitorModel;
        this.file = file;
        this.quality = quality;
        this.codeAccuracy = codeAccuracy;
        this.overallImpression = overallImpression;
        this.averageMark = averageMark;
        this.comment = comment;
    }

    public CompetitorModel getCompetitorModel() {
        return competitorModel;
    }

    public void setCompetitorModel(CompetitorModel competitorModel) {
        this.competitorModel = competitorModel;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {

        if (quality <= 100 && quality >= 0)
            this.quality = quality;
    }

    public double getCodeAccuracy() {
        return codeAccuracy;
    }

    public void setCodeAccuracy(double codeAccuracy) {
        if (codeAccuracy <= 100 && codeAccuracy >= 0)
            this.codeAccuracy = codeAccuracy;
    }

    public double getOverallImpression() {
        return overallImpression;
    }

    public void setOverallImpression(double overallImpression) {

        if (overallImpression <= 100 && overallImpression >= 0)
            this.overallImpression = overallImpression;
    }

    public String getAverageMark() {

        return (new DecimalFormat("#.#")).format(averageMark);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean load() {

        Database database = Database.getInstance();
        ResultSet resultSet = database.query("SELECT * FROM work WHERE idCompetitor='" + competitorModel.getIdUser() + "';");
        try {
            boolean loaded;
            if (!resultSet.isBeforeFirst()) {
                loaded = false;
            } else {
                this.file = resultSet.getString("file");
                this.quality = resultSet.getDouble("quality");
                this.codeAccuracy = resultSet.getDouble("codeAccuracy");
                this.overallImpression = resultSet.getDouble("overallImpression");
                this.averageMark = resultSet.getDouble("averageMark");
                this.comment = resultSet.getString("comment");
                resultSet.close();
                loaded = true;
            }
            return loaded;
        } catch (Exception e) {
            System.out.println("load()");
            e.printStackTrace();
        }
        return false;
    }

    public boolean addWork(String file) {


        if (isHanded()) {

            return addToFolder(file);
        } else {

            return addToFolder(file) && addToBase(file);
        }


    }

    private boolean addToFolder(String file) {

        File fileIn = new File(file);

        if (fileIn.exists() && !fileIn.isDirectory()) {
            try {
                File fileOut = new File("res" + File.separator + "work" + File.separator + competitorModel.getUsername() +
                        fileIn.getName().substring(fileIn.getName().lastIndexOf(".")));

                Files.copy(fileIn.toPath(), fileOut.toPath(), StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                System.out.println("addToFolder(String  file)");
                e.printStackTrace();
            }
            return true;
        } else return false;
    }

    private boolean addToBase(String fajl) {

        file = competitorModel.getUsername() + fajl.substring(fajl.lastIndexOf("."));

        Database database = Database.getInstance();
        int resultSet = database.update("INSERT INTO work VALUES ('" + competitorModel.getIdUser() + "','" + file + "','" + quality + "','" + codeAccuracy + "','" + overallImpression + "','" + averageMark + "','" + comment + "');");
        try {
            if (resultSet == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("addToBase(String  fajl)");
            e.printStackTrace();
        }
        return false;
    }

    public boolean isHanded() {

        load();
        File file = new File("res" + File.separator + "work" + File.separator + this.file);
        return file.exists() && !file.isDirectory();
    }

    public boolean isEstimated() {

        if (isHanded()) {

            Database database = Database.getInstance();
            ResultSet resultSet = database.query("SELECT * FROM work WHERE idCompetitor='" + competitorModel.getIdUser() + "';");
            try {
                boolean vrati;
                if (!resultSet.isBeforeFirst()) {

                    vrati = false;
                } else {

                    vrati = resultSet.getDouble("quality") != 101 && resultSet.getDouble("codeAccuracy") != 101 && resultSet.getDouble("overallImpression") != 101 && resultSet.getDouble("averageMark") != 101;
                }
                resultSet.close();
                return vrati;
            } catch (Exception e) {
                System.out.println("isEstimated()");
                e.printStackTrace();
            }
            return false;
        } else {
            return false;
        }
    }

    private double workAverageMark() {

        return averageMark = (quality + codeAccuracy + overallImpression) / 3;
    }

    public boolean estimate(double workQuality, double workCodeAccuracy, double workOverallImpression) {

        setQuality(workQuality);
        setCodeAccuracy(workCodeAccuracy);
        setOverallImpression(workOverallImpression);
        workAverageMark();

        if ((workQuality <= 100 && workQuality >= 0) && (workCodeAccuracy <= 100 && workCodeAccuracy >= 0) && (workOverallImpression <= 100 && workOverallImpression >= 0)) {
            Database con = Database.getInstance();
            int rs = con.update("UPDATE work SET quality = '" + this.quality + "', codeAccuracy = '" + this.codeAccuracy + "', overallImpression = '" + this.overallImpression + "',averageMark = '" + this.averageMark + "' WHERE idCompetitor='" + competitorModel.getIdUser() + "';");

            try {
                if (rs == 0) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("estimate(double quality, double codeAccuracy, double overallImpression)");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean comment(String text) {

        this.comment = text;

        Database con = Database.getInstance();
        int rs = con.update("UPDATE work SET comment = '" + text + "' WHERE idCompetitor='" + competitorModel.getIdUser() + "';");

        try {
            if (rs == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("comment(String text)");
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<WorkModel> loadWorks(char mod) {

        Database con = Database.getInstance();
        String sql;
        switch (mod) {
            case 'p':
                sql = "SELECT * FROM work WHERE quality='101' AND codeAccuracy='101' AND overallImpression='101';";
                break;
            case 'o':
                sql = "SELECT * FROM work WHERE quality!='101' AND codeAccuracy!='101' AND overallImpression!='101';";
                break;
            case 's':
            default:
                sql = "SELECT * FROM work;";
                break;
        }

        try (ResultSet rs = con.query(sql)) {
            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                ArrayList<WorkModel> workModels = new ArrayList<>();

                while (rs.next()) {
                    workModels.add(new WorkModel(
                            (CompetitorModel) UserModel.getUser(rs.getInt("idCompetitor")),
                            rs.getString("file"),
                            rs.getDouble("quality"),
                            rs.getDouble("codeAccuracy"),
                            rs.getDouble("overallImpression"),
                            rs.getDouble("averageMark"),
                            rs.getString("comment")
                    ));
                }
                return workModels;
            }
        } catch (Exception e) {
            System.out.println("load()");
            e.printStackTrace();
        }
        return null;
    }

    public String getText() {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get("res" + File.separator + "work" + File.separator + file));

            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean isValidЕxtension(WorkModel workModel, String[] extensions) {
        for (String extension : extensions)
            if (workModel.getFile().substring(workModel.getFile().lastIndexOf(".")).equals(extension))
                return true;
        return false;

    }

    public static boolean isValidЕxtension(WorkModel workModel, String extension) {
        return workModel.getFile().substring(workModel.getFile().lastIndexOf(".")).equals(extension);

    }

}