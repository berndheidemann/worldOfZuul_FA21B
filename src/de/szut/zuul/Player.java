package de.szut.zuul;

import de.szut.zuul.items.EatableItem;
import de.szut.zuul.items.Item;

import java.util.LinkedList;

public class Player {

    private final double INITIAL_LOAD_CAPACITY = 10.0;
    private Room currentRoom;
    private double loadCapacity;
    private LinkedList<Item> items;

    public Player() {
        this.loadCapacity = INITIAL_LOAD_CAPACITY;
        this.items = new LinkedList<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void goTo(Room room) {
        this.currentRoom = room;
    }

    public boolean takeItem(Item item) {
        if (isTakePossible(item)) {
            this.items.add(item);
            return true;
        }
        return false;
    }

    private boolean isTakePossible(Item item) {
        if (item.getWeight() + calculateWeight() <= loadCapacity) {
            return true;
        } else {
            return false;
        }
    }

    private double calculateWeight() {
        double weightSum = 0;
        for (Item i : this.items) {
            weightSum += i.getWeight();
        }
        return weightSum;
    }

    public Item getAndRemoveItemFromInventory(String name) {
        for (Item i : this.items) {
            if (name.equals(i.getName())) {
                this.items.remove(i);
                return i;
            }
        }
        return null;
    }

    public String showStatus() {
        String response = "Status of the player\nloadCapacity: " + this.loadCapacity + "\n";
        response += "takenItems:\n";
        for (Item i : this.items) {
            response += " - " + i.getName() + ", " + i.getWeight() + "\n";
        }
        response += "\nabsorbedWeight: " + this.calculateWeight();
        return response;
    }

    public void changeCapacity(double mod) {
        this.loadCapacity *= mod;
    }

    public boolean eat(String itemName) {
        Item i = getAndRemoveItemFromInventory(itemName);
        if (i == null) {
            return false;
        }
        if (i instanceof EatableItem) {
            EatableItem ei = (EatableItem) i;
            ei.eat(this);
            return true;
        } else {
            this.items.add(i);
        }
        return false;
    }

    public void dropAllItems() {
        while (this.items.size() > 0) {
            this.currentRoom.putItem(this.items.get(0));
            this.items.remove(this.items.get(0));
        }
    }
}
