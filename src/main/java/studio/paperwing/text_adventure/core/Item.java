package studio.paperwing.text_adventure.core;

public class Item {
    // an item is something an entoty has. it can be purchased, sold and used. sometimes it also has durability.
    private String id = "item";
    private String desc = "This is a unset item.";
    private int buyAmount = 0;
    private int sellAmount = 0;
    private int durability = -1;
    private int stack = 0;
    private int stackMax = 1;

    public Item(String id) {
        this.id = id;
    }

    public Item copy() {
        Item copy = new Item(id);
        copy.desc = desc;
        copy.buyAmount = buyAmount;
        copy.sellAmount = sellAmount;
        copy.durability = durability;
        copy.stack = stack;
        copy.stackMax = stackMax;
        return copy;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public int getSellAmount() {
        return sellAmount;
    }

    public int getDurability() {
        return durability;
    }

    public int getStack() {
        return stack;
    }

    public int getStackMax() {
        return stackMax;
    }

    public Item setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Item setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
        return this;
    }

    public Item setSellAmount(int sellAmount) {
        this.sellAmount = sellAmount;
        return this;
    }

    public Item setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public Item setStack(int stack) {
        this.stack = stack;
        return this;
    }

    public Item setStackMax(int stackMax) {
        this.stackMax = stackMax;
        return this;
    }
}
