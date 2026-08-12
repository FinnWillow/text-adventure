package studio.paperwing.text_adventure.core;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    private int id;
    private int health = 0;     // how much dammage it can absorb before it dies.
    private int strength = 0;   // how much dammage it can deal when it attacks.
    private int defence = 0;    // how much dammage it can absorb when it gets attacked.
    private boolean isDefended = false; // if it can or not defend the next attack.

    // the entity's inventory <slot, item>
    private List<Item> inventory = new ArrayList<>();

    public Entity(int id) {
        this.id = id;
    }

    public Entity copy() {
        Entity copy = new Entity(id);
        copy.health = health;
        copy.strength = strength;
        copy.defence = defence;
        copy.isDefended = isDefended;
        copy.inventory = new ArrayList<>(inventory);

        return copy;
    }
    
    public void dammage(int dmg) {
        if(isDefended) {
            isDefended = false;
            int dmgToTake = dmg - defence;

            if (dmgToTake > 0) {
                this.health -= dmg;
            }
        } else {
            this.health -= dmg;        
        }
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefence() {
        return defence;
    }

    public boolean isDefended() {
        return isDefended;
    }

    /**
     * Find the first occurence of the item with the specified ID.
     * @param id The id to find the item by.
     * @return The found item or null if nothing was found.
     */
    public Item getItem(String id) {
        for(Item item : inventory) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    /**
     * 
     * @param id
     * @param item
     * @return
     */
    public boolean giveItem(Item item) {
        Item mine = getItem(item.getId());

        if (mine.equals(null)) {
            inventory.add(item);
            return true;
        }

        if (mine.getStack() + item.getStack() > mine.getStackMax()) {
            mine.setStack(mine.getStackMax());
            return false;
        } else {
            mine.setStack(mine.getStack() + item.getStack());
            return true;
        }
    }

    /**
     * Takes the ammount of items from the item of id, or the item itself if ammount is -1;
     * @param id The id of the item to take.
     * @param amount The ammount of that item to take or -1 for all of them.
     * @return A copy of the item with the ammound specified, or the minimum possible.
     */
    public Item takeItem(String id, int amount) {
        Item mine = getItem(id);

        if (amount == -1) {
            inventory.remove(mine);
            return mine.copy();
        }

        int stack = mine.getStack() - amount;

        if (stack <= 0) {
            inventory.remove(mine);
        } else {
            mine.setStack(stack);
        }

        Item taken = mine.copy();
        if (stack > 0) {
            taken.setStack(amount);    
        }

        return taken;
    }

    public Entity setDefence(int defence) {
        this.defence = defence;
        return this;
    }

    public Entity setHealth(int health) {
        this.health = health;
        return this;
    }

    public Entity setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public Entity setDefended(boolean isDefended) {
        this.isDefended = isDefended;
        return this;
    }
}
