public class Card {
    private String name;
    private int damage;
    private int block;
    private String cardType;
    private String lore;
    private int level;

    public Card(String name, int damage, int block, String cardType, String lore, int level) {
        this.name = name;
        this.damage = damage;
        this.block = block;
        this.cardType = cardType;
        this.lore = lore;
        this.level = level;
    }

    public void getFullStats() throws java.lang.Exception {
        System.out.println("-----------------");
        Main.TypeLine("===NAME - " + name + "===\n===DAMAGE - " + damage + "===\n===BLOCK - " + block + "===\n===TYPE - " + cardType + "===\n===DESC: " + lore + "===\n");
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setBlock(int block) {
        this.block = block;
    }
}
