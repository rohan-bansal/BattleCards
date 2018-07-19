package main.java.com.rohan;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String datafile;
    public static String combofile;
    public static String jsondata;

    public static boolean newAccount;

    public static int level = 1;

    public static void main(String[] args) throws java.lang.Exception {
        GameUtils gameutils = new GameUtils();
        Scanner in = new Scanner(System.in);
        String ph;

        datafile = System.getProperty("user.dir");
        combofile = datafile + "/src/main/resources/com/rohan/Combinations.txt";
        datafile = datafile + "/src/main/resources/com/rohan/gameData.json";

        TypeLine(ANSI_GREEN + "==========AlchemyCards==========\n" + ANSI_RESET);
        while(true) {
            TypeLine(ANSI_GREEN + "\nUsername ['--' if you do not have an account] : " + ANSI_RESET);
            ph = in.nextLine();
            if(FileUtils.checkinDataBase(ph)) {
                TypeLine(ANSI_GREEN + "Enter PIN : " + ANSI_RESET);
                String ph3 = in.nextLine();
                if(FileUtils.checkinDataBase(ph, Integer.parseInt(ph3))) {
                    newAccount = false;
                    break;
                } else {
                    TypeLine(ANSI_RED + "PIN is incorrect." + ANSI_RESET);
                }
            } else if(ph.equals("--")) {
                TypeLine(ANSI_GREEN + "Create a username : " + ANSI_RESET);
                ph = in.nextLine();
                TypeLine(ANSI_GREEN + "Create a PIN : " + ANSI_RESET);
                String ph2 = in.nextLine();
                newAccount = true;
                FileUtils.createAccount(ph, Integer.parseInt(ph2));
            } else {
                TypeLine(ANSI_RED + "Username does not exist." + ANSI_RESET);
            }
        }
        gameLoop(gameutils);
    }

    static void gameLoop(GameUtils gameutils) throws java.lang.Exception {
        newAccount = true; //CHANGE THIS LATER TODO
        Scanner in = new Scanner(System.in);
        String ph;
        if(newAccount) {
            TypeLine(ANSI_BLUE + "AlchemyCards is a battle card game in which two cards use their attacks" +
                    "\non one another until either player's overall health is equal to or lower than 0." +
                    "\nType 'CARDS' to view current cards >> " + ANSI_RESET);
            ph = in.nextLine();

        } else { //OLD ACCOUNT FIX

        }
    }


    static void TypeLine(String line) throws java.lang.InterruptedException {
        for (int i = 0; i < line.length(); i++) {
            System.out.print(line.charAt(i));
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }
}
