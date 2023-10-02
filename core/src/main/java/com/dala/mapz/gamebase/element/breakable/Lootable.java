package com.dala.mapz.gamebase.element.breakable;

import com.dala.mapz.gamebase.element.Breakable;
import com.dala.mapz.gamebase.element.Material;

public class Lootable extends Breakable {

    private LootableWeapon weapon;
    /**
     * Créé un Element.
     *
     * @param x abscisse dans le monde.
     * @param y ordonnée dans le monde.
     */
    public Lootable(int x, int y, LootableWeapon lootable) {
        super(x, y);
        this.material = lootable.material;
        this.health = 1;
        this.weapon = lootable;
        this.width = 2;
        this.height = 2;
    }

    public LootableWeapon getLootableWeapon() {
        return weapon;
    }

    public enum LootableWeapon {
        AXE(new Weapon(1f, 2.5f, 1.5f, 20), 2, Material.AXE),
        DAGUE(new Weapon(0.5f, 0.5f, 0.5f, 5), 0, Material.DAGUE),
        PICKAXE(new Weapon(1f, 2f, 1f, 15), 1, Material.PICKAXE),
        HAMMER(new Weapon(1f, 1f, 2f, 30), 3, Material.HAMMER);

        Weapon weapon;
        int index;
        Material material;

        LootableWeapon(Weapon weapon, int index, Material material) {
            this.weapon = weapon;
            this.index = index;
            this.material = material;
        }

        public Weapon getWeapon() {
            return weapon;
        }

        public int getIndex() {
            return index;
        }
    }
}
