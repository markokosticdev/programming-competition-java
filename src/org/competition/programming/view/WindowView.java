package org.competition.programming.view;

import java.util.ArrayList;
import java.util.Scanner;

public class WindowView {

    public static void write() {

        System.out.println();
    }

    public static void write(String text) {

        System.out.println(text);
    }

    public static void write(String text, char linija) {

        if (linija == 'n')
            System.out.println(text);
        else if (linija == 's')
            System.out.print(text);
    }

    public static void write(String text, int marginLeft) {

        String ml = "";
        for (int i = 0; i < marginLeft; i++)
            ml += " ";

        System.out.println(ml + text);
    }

    public static void write(String text, int marginLeft, char line) {

        String ml = "";
        for (int i = 0; i < marginLeft; i++)
            ml += " ";

        if (line == 'n')
            System.out.println(ml + text);
        else if (line == 's')
            System.out.print(ml + text);
    }

    public static void write(String text, int marginLeft, int marginTop) {

        for (int i = 0; i < marginTop; i++) System.out.println();
        write(text, marginLeft);
    }

    public static void write(int marginLeft) {

        String ml = "";
        for (int i = 0; i < marginLeft; i++)
            ml += " ";
        System.out.println(ml);
    }

    public static void write(int marginLeft, char line) {

        String ml = "";
        for (int i = 0; i < marginLeft; i++)
            ml += " ";

        if (line == 'n')
            System.out.println(ml);
        else if (line == 's')
            System.out.print(ml);
    }

    public static void writeMultiLine(String tekst, int wrapOn, int marginLeft) {
        String[] textlines = tekst.split("\\n+|\\p{Blank}+");
        ArrayList<String> textWrap = new ArrayList<>();
        String textLine = "";

        for (int i = 0; i < textlines.length; i++) {
            if (textLine.isEmpty()) {
                textLine += textlines[i];
            } else if (textLine.length() + textlines[i].length() < wrapOn) {

                if (!textlines[i].equals("\n"))
                    textLine += " " + textlines[i];
            } else {
                textWrap.add(textLine.trim());
                textLine = textlines[i];
            }
        }

        if (!textLine.isEmpty()) {
            textWrap.add(textLine.trim());
        }

        for (String text : textWrap) {
            write(text, marginLeft);
        }
    }

    public static void writeMultiLine(String tekst, int marginLeft) {
        String[] textLines = tekst.split("\\n+");
        ArrayList<String> textWrap = new ArrayList<>();

        for (int i = 0; i < textLines.length; i++) {

            textWrap.add(textLines[i]);
        }

        for (String text : textWrap) {
            write(text, marginLeft);
        }
    }

    public static Object input(String metod) {

        Scanner input = new Scanner(System.in);
        if (metod.equals("nl")) {
            if (input.hasNextLine())
                return input.nextLine();
            else return null;
        } else if (metod.equals("ni")) {
            if (input.hasNextInt())
                return input.nextInt();
            else return 101;
        } else return null;
    }

    public static void exitApplication() {

        System.exit(0);
    }

    public static void clearWindow() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

}
