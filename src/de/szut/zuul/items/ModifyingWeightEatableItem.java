package de.szut.zuul.items;

import de.szut.zuul.Player;

public class ModifyingWeightEatableItem extends EatableItem {

    private final double mod;

    public ModifyingWeightEatableItem(String name, String description, double weight, double mod) {
        super(name, description, weight);
        this.mod = mod;
    }

    @Override
    public void eat(Player player) {
        player.changeCapacity(mod);
    }
}
