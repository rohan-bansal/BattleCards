import java.util.ArrayList;
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
    public static final String ANSI_BOLD = "\u001B[1m";

    public static String datafile;
    public static String combofile;

    public static String jsondata;

    public static boolean newAccount;

    public static int playerlevel = 1;
    public static int playlevel = 1;
    public static int dust = 100;

    public static void main(String[] args) throws java.lang.Exception {
        GameUtils gameutils = new GameUtils();
        Scanner in = new Scanner(System.in);
        String ph;

        datafile = System.getProperty("user.dir");
        if(datafile.endsWith("/src")) {
            datafile = datafile.substring(0, datafile.indexOf("/src"));
            System.out.println(datafile);
        }
        combofile = datafile + "/src/data/Combinations.txt";
        datafile = datafile + "/src/data/gameData.json";

        TypeLine(ANSI_GREEN + "==========BattleCards==========\n" + ANSI_RESET);
        while(true) {
            TypeLine(ANSI_GREEN + "\nUsername ['--' if you do not have an account] : " + ANSI_RESET);
            ph = in.nextLine();
            if(FileUtils.checkinDataBase(ph)) {
                TypeLine(ANSI_GREEN + "Enter PIN : " + ANSI_RESET);
                String ph3 = in.nextLine();
                if(FileUtils.checkinDataBase(ph, Integer.parseInt(ph3))) {
                    if(newAccount) {

                    } else {
                        newAccount = false;
                    }
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
        Scanner in = new Scanner(System.in);
        String ph;
        if (newAccount) {
            TypeLine(ANSI_BLUE + "BattleCards is a battle card game in which two cards use their attacks" +
                    "\non one another until either player's overall health is equal to or lower than 0." +
                    "\n\nType 'CARDS' to view current cards >> " + ANSI_RESET);
            ph = in.nextLine();
            if (ph.toLowerCase().equals("cards")) {
                gameutils.listcards(gameutils.currentdeck);
                TypeLine(ANSI_BLUE + "These are your current cards.\nUsing the cards, you face opponents and try to defeat them.\nHere is the menu: " + ANSI_RESET);
                Menu(in, gameutils);
            }
        } else {
            Menu(in, gameutils);
        }
    }

    static void Menu(Scanner in, GameUtils gu) throws java.lang.Exception {
        String ph;
        TypeLine(ANSI_BLUE + "\n==========MENU==========" + ANSI_RESET);
        TypeLine(ANSI_GREEN + "\n\t[p] Play level " + playlevel + ANSI_PURPLE + "\n\t[f] Forge a New Card" + ANSI_YELLOW + "\n\t[u] to upgrade a card" + ANSI_WHITE + "\n\t[c] to view Battle Deck" +
                "\n\t[m] Modify Battle Deck\n\t[v] View Card Inventory\n\t[l] Check player level\n\t[d] View amount of dust in balance\n\t[e] View this menu\n\t'STATS' (nameOfCard) to see card statistics\n\t" + "\u001B[22m" + "        i.e STATS Wizard" + ANSI_RESET);
        while(true) {
            TypeLine(ANSI_YELLOW + "\n[type a letter] --> " + ANSI_RESET);
            ph = in.nextLine();
            if(ph.toLowerCase().equals("e")) {
                Menu(in, gu);
            } else if(ph.toLowerCase().equals("l")) {
                TypeLine(ANSI_BOLD + ANSI_GREEN + "Your player is currently at level " + playerlevel + "\n" + ANSI_RESET);
            } else if(ph.toLowerCase().equals("v")) {
                gu.listcards(gu.inventory);
            } else if(ph.toLowerCase().equals("c")) {
                gu.listcards(gu.currentdeck);
            } else if(ph.toLowerCase().equals("m")) {
                gu.modifyDeck();
            } else if(ph.toLowerCase().equals("u")) {
                TypeLine(ANSI_PURPLE + "Name of the Card: " + ANSI_RESET);
                ph = in.nextLine();
                gu.upgradeCard(ph);
            } else if(ph.toLowerCase().equals("f")) {
                String[] temp = new String[2];
                ArrayList<Card> temp3 = new ArrayList<>();
                int temp2 = 0;
                TypeLine(ANSI_PURPLE + "Name of Card 1: " + ANSI_RESET);
                ph = in.nextLine();
                temp[0] = ph;
                TypeLine(ANSI_PURPLE + "Name of Card 2: " + ANSI_RESET);
                String ph2 = in.nextLine();
                temp[1] = ph2;
                for(String x : temp) {
                    for(Card part2 : gu.overall) {
                        if((part2.getName().toLowerCase()).equals(x.toLowerCase())) {
                            temp2++;
                            temp3.add(part2);
                        }
                    }
                }
                if(temp2 != 2) {
                    TypeLine(ANSI_RED + "One card (or both) is not a part of your deck.");
                } else {
                    gu.combineCards(temp3.get(0),  temp3.get(1));
                }
            } else if(ph.toLowerCase().equals("p")) {

            } else if(ph.toLowerCase().equals("d")) {
                TypeLine(ANSI_GREEN + ANSI_BOLD + "Dust: " + dust + ANSI_RESET);
            } else if(ph.length() > 1) {
                if(ph.substring(0,5).toLowerCase().equals("stats")) {
                    for(Card part : gu.currentdeck) {
                        if((part.getName().toLowerCase()).equals(ph.substring(6).toLowerCase())) {
                            part.getFullStats();
                        }
                    }
                }
            } else {
                TypeLine(ANSI_RED + "That is not a valid letter." + ANSI_RESET);
            }
        }
    }


    static void TypeLine(String line) throws java.lang.InterruptedException {
        for (int i = 0; i < line.length(); i++) {
            System.out.print(line.charAt(i));
            TimeUnit.MILLISECONDS.sleep(20);
        }
    }
}
