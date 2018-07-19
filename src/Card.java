package main.java.com.rohan;

public class Card {
    private String name;
    private int damage;
    private int block;
    private String cardType;
    private String lore;

    public Card(String name, int damage, int block, String cardType, String lore) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.cardType = cardType;
        this.lore = lore;
    }

    public void getFullStats() {
        System.out.println("-----------------");
        System.out.println("===NAME - " + name + "===\n===DAMAGE - " + damage + "===\n===BLOCK - " + block + "===\n===TYPE - " + cardType + "===\n===DESC: " + lore + "===");
        System.out.println("-----------------");
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getBlock() {
        return block;
    }

    public String getCardType() {
        return cardType;
    }

    public String getLore() {
        return lore;
    }
}
