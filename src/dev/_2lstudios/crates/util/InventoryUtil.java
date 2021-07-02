package dev._2lstudios.crates.util;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class InventoryUtil {
    public static int getValidRow(final int slot) {
        return Math.max(Math.min(slot, 9), 1);
    }

    public static int getValidRow(final String string) {
        try {
            final int slot = Integer.parseInt(string);

            return getValidRow(slot);
        } catch (final NumberFormatException e) {
        }

        return 3;
    }

    public static void copy(Inventory inventory, Inventory inventory2) {
        for (int i = 0; i < Math.min(inventory.getSize(), inventory2.getSize()); i++) {
            inventory2.setItem(i, inventory.getItem(i));
        }
    }

    public static void close(Inventory inventory) {
        for (final HumanEntity viewer : inventory.getViewers()) {
            viewer.closeInventory();
        }
    }

    public static void clear(Inventory inventory) {
        inventory.clear();
    }
}
