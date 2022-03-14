package de.szut.zuul;

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

    public Item dropItem(String name) {
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
            response += i.getName() + ", " + i.getWeight();
        }
        response += "\nabsorbedWeight: " + this.calculateWeight();
        return response;
    }
}
