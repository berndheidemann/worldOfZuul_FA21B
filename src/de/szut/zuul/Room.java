package de.szut.zuul;

import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 * <p>
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;


    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description) {
        this.exits = new HashMap<>();
        this.items = new HashMap<>();
        this.description = description;
    }

    public void putItem(Item item) {
        this.items.put(item.getName(), item);
    }


    public void setExit(String direction, Room neighbour) {
        this.exits.put(direction, neighbour);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    public Room getExit(String direction) {
        return this.exits.get(direction);
    }

    public String exitsToString() {
        StringBuilder exists = new StringBuilder();
        for (String key : this.exits.keySet()) {
            exists.append(key + " ");
        }
        return exists.toString();
    }

    public String itemsToString() {
        StringBuilder itemsAsStr = new StringBuilder();
        for (String key : this.items.keySet()) {
            Item item = this.items.get(key);
            itemsAsStr.append("- " + item.getName() + ", " + item.getDescription() + ", " + item.getWeight() + "\n");
        }
        return itemsAsStr.toString();
    }

    public String getLongDescription() {
        return String.format("You are %s\nExits: %s\nItems in this room:\n%s", description, exitsToString(), itemsToString());
    }
}
