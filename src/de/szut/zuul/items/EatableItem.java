package de.szut.zuul.items;

import de.szut.zuul.Player;
import de.szut.zuul.items.Item;

public abstract class EatableItem extends Item {


    public EatableItem(String name, String description, double weight) {
        super(name, description, weight);
    }

    public abstract void eat(Player player);
}
