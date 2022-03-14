package de.szut.zuul.items;

import de.szut.zuul.Player;

public class LoosingAllItemsEatableItem extends EatableItem {

    public LoosingAllItemsEatableItem(String name, String description, double weight) {
        super(name, description, weight);
    }

    @Override
    public void eat(Player player) {
        player.dropAllItems();
    }
}
