package main.java.com.rohan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class GameUtils {

    public ArrayList<Card> currentdeck;
    public ArrayList<Card> inventory;

    public GameUtils() {
        currentdeck = new ArrayList<Card>() {{
            add(new Card("Wizard", 5, 1, "Basic", "A human with magical abilities.\nKnown to eat sandwiches from time to time."));
            add(new Card("Fire", 3, 0, "Basic", "A very hot gas/fuel substance\nthat can cause severe burns."));
            add(new Card("Water", 2, 0, "Basic", "Essential for life, no \ndefinite volume."));
            add(new Card("Fairy", 4, 2, "Basic", "A tiny magical human that can \nfly."));
            add(new Card("Electricity", 5, 0, "Basic", "A form of energy resulting \nfrom charged particles."));
        }};
        inventory = new ArrayList<>();
    }

    public void addCard(String name, int damage, int block, String cardType, String lore) {
        inventory.add(new Card(name, damage, block, cardType, lore));
    }

    public void listcards(ArrayList<Card> deck) {
        System.out.println("------------------");
        for(Card item : deck) {
            System.out.println(item.getName());
        }
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
                if(Integer.parseInt(getdata2[0]) > Main.level) {
                    System.out.println(Main.ANSI_RED + "You need to become level " + getdata2[0] + " to combine those cards.");
                } else {
                    inventory.add(new Card(getdata2[1], Integer.parseInt(getdata2[2]), Integer.parseInt(getdata2[3]), getdata2[4], getdata3[1]));
                    for(Card x : inventory) {
                        if(x.getName().equals(getdata2[1])) {
                            x.getFullStats();
                        }
                    }
                }
                break;
            }
        }
    }
}
