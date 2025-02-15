import java.util.*;

public class Character implements Comparable<Character>{
    
    // declare fields
    private String name;
    private int creditCount;
    private ArrayList<Item> items = new ArrayList<>();

    // constructor
    public Character(String name, int creditCount, ArrayList<Item> items) {
        this.name = name;
        this.creditCount = creditCount;
        this.items = items;
    }

    // to string method for 'System.out.println()'
    public String toString() {
        return name + " (" + creditCount + ")";
    }

    // compare method which allows 'Collections.sort()'
    public int compareTo(Character o) {
        // go before
        if (this.name.compareTo(o.name) < 0) {
            return -1;
        }
        // go after
        else if (this.name.compareTo(o.name) > 0) {
            return 1;
        }
        // equal
        else {
            return 0;
        }
    }

    // add items to character's inventory
    public void addItem(String itemName, int itemValue) {
        
        // create a new item and add it to the list of items
        Item item = new Item(itemName, itemValue);
        items.add(item);
    }

    // drop items from character's inventory
    public boolean dropItem(String itemName) {
        
        // remove an item if its name matches the given name and return true 
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                this.items.remove(i);
                return true;
            }
        }
        return false;
    }

    // sell item to vendor
    public boolean sellItemToVendor(String itemName) {
        
        // remove an item from character's 'inventory' and add that item's value to character's credit count if its name matches the given name and return true
        for (Item i : items) {
            if (i.getName().equals(itemName)) {
                items.remove(i);
                creditCount += i.getValue();
                return true;
            }
        }

        // otherwise return false
        return false;   
    }

    // sell item to another character
    public boolean sellItemToCharacter(String itemName, Character buyer) {

        // remove item from seller's 'inventory' and add the item's value to seller's credit count
        // add item to buyer's 'inventory' and subtract the item's value to buyer's credit count if their credit count is greater than or equal to the item's value and return true
        // iterator reference: https://stackoverflow.com/questions/18448671/how-to-avoid-concurrentmodificationexception-while-removing-elements-from-arr
        Iterator<Item> iterator = items.iterator();

        while(iterator.hasNext()) {
            Item i = iterator.next();

            if (i.getName().equals(itemName)) {
                if (buyer.getCreditCount() >= i.getValue()) {
                    // seller
                    iterator.remove();
                    creditCount += i.getValue();

                    // buyer
                    buyer.addItem(itemName, i.getValue());
                    buyer.creditCount -= i.getValue();
                    return true;
                }
            }
        }

        // otherwise return false
        return false;
    }

    // getter setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getCreditCount() {
        return creditCount;
    }

    public void setCreditCount(int creditCount) {
        this.creditCount = creditCount;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
