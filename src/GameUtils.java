import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class GameUtils {

    public ArrayList<Card> currentdeck;
    public ArrayList<Card> inventory;
    public ArrayList<Card> overall;
    int multiplier;

    public GameUtils() {
        currentdeck = new ArrayList<Card>() {{
            add(new Card("Wizard", 5, 1, "Basic", "A human with magical abilities.\nKnown to eat sandwiches from time to time.", 1));
            add(new Card("Fire", 3, 0, "Basic", "A very hot gas/fuel substance\nthat can cause severe burns.", 1));
            add(new Card("Water", 2, 0, "Basic", "Essential for life, no \ndefinite volume.", 1));
            add(new Card("Fairy", 4, 2, "Basic", "A tiny magical human that can \nfly.", 1));
            add(new Card("Electricity", 5, 0, "Basic", "A form of energy resulting \nfrom charged particles.", 1));
        }};
        overall = new ArrayList<>(currentdeck);
        inventory = new ArrayList<>(currentdeck);
    }

    public void addCard(String name, int damage, int block, String cardType, String lore) {
        inventory.add(new Card(name, damage, block, cardType, lore, 1));
        overall.add(new Card(name, damage, block, cardType, lore, 1));
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
                    Main.TypeLine(Main.ANSI_RED + "You need to become player level " + getdata2[0] + " to combine those cards.");
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
                                currentdeck.add(new Card(getdata2[2],  Integer.parseInt(getdata2[3]), Integer.parseInt(getdata2[4]), getdata2[5], getdata3[1], 1));
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

    public void upgradeCard(String name) throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        String ph;
        for(Card item : overall) {
            if(item.getName().toLowerCase().equals(name.toLowerCase())) {
                switch(item.getCardType()) {
                    case "Basic":
                        multiplier = 8;
                        break;
                    case "Magic":
                        multiplier = 10;
                        break;
                    case "Science":
                        multiplier = 9;
                        break;
                    case "Life":
                        multiplier = 10;
                        break;
                }
                Main.TypeLine(Main.ANSI_YELLOW + "Upgrading " + item.getName() + " costs " + (item.getLevel() * multiplier) + " dust. Continue? [y/n] : " + Main.ANSI_RESET);
                ph = in.nextLine();
                if(ph.equals("y")) {
                    if(item.getLevel()*multiplier > Main.dust) {
                        Main.TypeLine(Main.ANSI_RED + "Not enough dust." + Main.ANSI_RESET);
                        return;
                    }
                    Main.dust -= item.getLevel() * multiplier;
                    item.setLevel(item.getLevel() + 1);
                    Main.TypeLine(Main.ANSI_GREEN + "Successfully upgraded " + item.getName() + " to level " + item.getLevel() + ".\nYou now have " + Main.dust + " dust left.\nAfter Upgrade: \n" + Main.ANSI_RESET);
                    item.setDamage(item.getDamage() + 1);
                    item.setBlock(item.getBlock() + 1);
                    item.getFullStats();
                    return;
                } else {
                    return;
                }
            }
        }
        Main.TypeLine(Main.ANSI_RED + "The card specified is not in your inventory/Battle deck, or does not exist." + Main.ANSI_RESET);
    }

    void modifyDeck() throws java.lang.Exception {
        Scanner in = new Scanner(System.in);
        String ph;
        Main.TypeLine(Main.ANSI_YELLOW + "To add a card from inventory to battle deck, type 'ADD (cardName)'\nTo remove a card from battledeck, type 'REMOVE (cardName)'\n" +
                "To see battle deck, type 'b'\nTo see inventory, type 'i'\nTo quit, type 'q'\n\n" + Main.ANSI_RESET);
        while(true) {
            Main.TypeLine(Main.ANSI_YELLOW + "\n>> " + Main.ANSI_RESET);
            ph = in.nextLine();
            if(ph.toLowerCase().equals("b")) {
                listcards(currentdeck);
            } else if(ph.toLowerCase().equals("i")) {
                listcards(inventory);
            } else if(ph.toLowerCase().equals("q")) {
                return;
            } else if(ph.length() > 1) {
                if(ph.substring(0,3).toLowerCase().equals("add")) {
                    if(isCard(ph.substring(4), overall)) {
                        if(currentdeck.contains(toCard(ph.substring(4)))) {
                            Main.TypeLine(Main.ANSI_RED + "This card is already in the battle deck." + Main.ANSI_RESET);
                        } else {
                            currentdeck.add(toCard(ph.substring(4)));
                        }
                    } else {
                        Main.TypeLine(Main.ANSI_RED + "That card does not exist." + Main.ANSI_RESET);
                    }

                } else if(ph.substring(0,6).toLowerCase().equals("remove")) {
                    if(isCard(ph.substring(7), overall)) {
                        if(currentdeck.contains(toCard(ph.substring(7)))) {
                            currentdeck.remove(currentdeck.indexOf(toCard(ph.substring(7))));
                        } else {
                            Main.TypeLine(Main.ANSI_RED + "This card is not in your battle deck." + Main.ANSI_RESET);
                        }
                    } else {
                        Main.TypeLine(Main.ANSI_RED + "That card does not exist." + Main.ANSI_RESET);
                    }

                }
            }
        }
    }

     boolean isCard(String ph, ArrayList<Card> deck) {
        for(Card item : deck) {
            if(item.getName().toLowerCase().equals(ph.toLowerCase())) {
                return true;
            }
        }
        return false;
     }

     Card toCard(String ph) {
        for(Card item : overall) {
            if(item.getName().toLowerCase().equals(ph.toLowerCase())) {
                return item;
            }
        }
        return new Card("Blank", 0, 0, "Blank", "Blank", 0);
     }
}
