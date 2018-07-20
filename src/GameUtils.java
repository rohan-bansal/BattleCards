import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GameUtils {

    public ArrayList<Card> currentdeck;
    public ArrayList<Card> inventory;
    public ArrayList<Card> overall;

    public GameUtils() {
        currentdeck = new ArrayList<Card>() {{
            add(new Card("Wizard", 5, 1, "Basic", "A human with magical abilities.\nKnown to eat sandwiches from time to time."));
            add(new Card("Fire", 3, 0, "Basic", "A very hot gas/fuel substance\nthat can cause severe burns."));
            add(new Card("Water", 2, 0, "Basic", "Essential for life, no \ndefinite volume."));
            add(new Card("Fairy", 4, 2, "Basic", "A tiny magical human that can \nfly."));
            add(new Card("Electricity", 5, 0, "Basic", "A form of energy resulting \nfrom charged particles."));
        }};
        overall = new ArrayList<>(currentdeck);
        inventory = new ArrayList<>();
    }

    public void addCard(String name, int damage, int block, String cardType, String lore) {
        inventory.add(new Card(name, damage, block, cardType, lore));
        overall.add(new Card(name, damage, block, cardType, lore));
    }

    public void listcards(ArrayList<Card> deck) throws java.lang.Exception {
        if(deck.size() == 0) {
            Main.TypeLine(Main.ANSI_RED + "You do not have any cards in that deck." + Main.ANSI_RESET);
            return;
        }
        System.out.println(Main.ANSI_GREEN + "------------------");
        for(Card item : deck) {
            Main.TypeLine(item.getName() + "\n");
        }
        System.out.println("------------------" + Main.ANSI_RESET);
    }

    public void combineCards(Card card1, Card card2) throws java.lang.Exception {
        String combdata = Main.combofile;
        String line;
        ArrayList<String> data = new ArrayList<>();

        try(FileReader fr = new FileReader(combdata)) {
            BufferedReader br = new BufferedReader(fr);
            while((line = br.readLine()) != null) {
                data.add(line);
            }
        }
        for(String item : data) {
            if(item.contains(card1.getName() + " ## " + card2.getName()) || item.contains(card2.getName() + " ## " + card1.getName())) {
                String[] getdata = item.split(" = ");
                String[] getdata2 = getdata[1].split(" ");
                String[] getdata3 = getdata[1].split(" \"");
                Scanner in = new Scanner(System.in);
                String ph;

                Main.TypeLine(Main.ANSI_YELLOW + "Forging these two cards costs " + getdata2[1] + " dust. Continue? [y/n] : " + Main.ANSI_RESET);
                ph = in.nextLine();
                if(ph.equals("y")) {

                } else {
                    return;
                }

                if(Integer.parseInt(getdata2[0]) > Main.playerlevel) {
                    System.out.println(Main.ANSI_RED + "You need to become player level " + getdata2[0] + " to combine those cards.");
                    return;
                } else if(Main.dust < Integer.parseInt(getdata2[1])) {
                    Main.TypeLine(Main.ANSI_RED + "You do not have enough dust. (" + getdata2[1] + " needed)" + Main.ANSI_RESET);
                    return;
                } else {
                    addCard(getdata2[2], Integer.parseInt(getdata2[3]), Integer.parseInt(getdata2[4]), getdata2[5], getdata3[1]);
                    for(Card x : inventory) {
                        if(x.getName().equals(getdata2[2])) {
                            Main.dust -= Integer.parseInt(getdata2[1]);
                            Main.TypeLine(Main.ANSI_GREEN + "Successfully forged " + getdata2[2] + ".\n" + Main.ANSI_RESET);
                            Main.TypeLine(Main.ANSI_GREEN + "You now have " + Main.dust + " dust.\n" + Main.ANSI_RESET);
                            x.getFullStats();
                            Main.TypeLine(Main.ANSI_GREEN + "Do you want to add " + getdata2[2] + " to your battle deck? [y/n] : " + Main.ANSI_RESET);
                            ph = in.nextLine();
                            if(ph.equals("y")) {
                                currentdeck.add(new Card(getdata2[2],  Integer.parseInt(getdata2[3]), Integer.parseInt(getdata2[4]), getdata2[5], getdata3[1]));
                            } else {

                            }
                            break;
                        }
                    }
                }
                return;
            }
        }
        Main.TypeLine(Main.ANSI_RED + "Those two cards cannot be combined.\n" + Main.ANSI_RESET);
    }
}
