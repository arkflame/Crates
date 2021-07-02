package dev._2lstudios.crates.crate;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.util.InventoryUtil;

public class Crate {
  private final Plugin plugin;
  private final CratesConfig cratesConfig;
  private final Map<Location, CrateBlock> crateBlocks = new HashMap<>();
  private final String name;
  private Inventory inventory;
  private String displayName;

  Crate(final Plugin plugin, final CratesConfig cratesConfig, final String name, final String displayName) {
    this.plugin = plugin;
    this.cratesConfig = cratesConfig;
    this.name = name;
    this.displayName = displayName;
  }

  public String getName() {
    return this.name;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getItemName() {
    return cratesConfig.getItemName(displayName);
  }

  public Collection<Location> getLocations() {
    return this.crateBlocks.keySet();
  }

  public List<String> getHologramLines() {
    return cratesConfig.getHologramLines(displayName);
  }

  public List<String> getItemLore() {
    final List<String> itemLore = cratesConfig.getItemLore(displayName);

    itemLore.add("ID: " + name);

    return itemLore;
  }

  public Inventory getInventory() {
    return this.inventory;
  }

  public ItemStack getKey() {
    final ItemStack keyItemStack = new ItemStack(Material.TRIPWIRE_HOOK);
    final ItemMeta keyItemMeta = keyItemStack.getItemMeta();

    keyItemMeta.setDisplayName(getItemName());
    keyItemMeta.setLore(getItemLore());
    keyItemMeta.addEnchant(Enchantment.DURABILITY, 0, false);
    keyItemStack.setItemMeta(keyItemMeta);

    return keyItemStack;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public boolean checkLocation(final Location location) {
    final Block block = location.getBlock();

    for (final Location location1 : this.crateBlocks.keySet()) {
      if (block.equals(location1.getBlock()))
        return true;
    }

    return false;
  }

  public boolean openKey(final Player player, final ItemStack itemStack) {
    if (player != null && isKey(itemStack)) {
      final PlayerInventory playerInventory = player.getInventory();
      if (playerInventory.firstEmpty() != -1) {
        final ItemStack[] rewards = trimContents(this.inventory.getContents()).<ItemStack>toArray(new ItemStack[0]);
        if (rewards.length > 0) {
          final int randomContent = (int) (Math.random() * rewards.length);
          final ItemStack reward = rewards[randomContent];
          final int amount = itemStack.getAmount() - 1;
          if (reward != null) {
            playerInventory.addItem(new ItemStack[] { reward.clone() });
            if (amount > 0) {
              itemStack.setAmount(amount);
            } else {
              playerInventory.remove(itemStack);
            }
          }
          return true;
        }
      }
    }
    return false;
  }

  public ItemStack getKey(final PlayerInventory playerInventory) {
    for (final ItemStack itemStack : playerInventory.getContents()) {
      if (isKey(itemStack))
        return itemStack;
    }
    return null;
  }

  public void openInventory(final Player player) {
    player.openInventory(this.inventory);
  }

  boolean isKey(final ItemStack itemStack) {
    if (itemStack != null && itemStack.hasItemMeta()) {
      final List<String> itemLore = itemStack.getItemMeta().getLore();
      if (itemLore != null) {
        final int loreSize = itemLore.size() - 1;
        if (loreSize > 0)
          return itemLore.get(loreSize).contains("ID: " + this.name);
      }
    }
    return false;
  }

  private Collection<ItemStack> trimContents(final ItemStack[] itemStacks) {
    final Collection<ItemStack> trimmedItemStacks = new HashSet<>();
    for (final ItemStack itemStack : itemStacks) {
      if (itemStack != null)
        trimmedItemStacks.add(itemStack);
    }
    return trimmedItemStacks;
  }

  public void despawnHolograms() {
    for (final CrateBlock crateBlock : crateBlocks.values()) {
      crateBlock.despawnHologram();
    }
  }

  public void spawnHolograms() {
    despawnHolograms();

    for (final CrateBlock crateBlock : crateBlocks.values()) {
      crateBlock.spawnHologram();
    }
  }

  public void addLocation(final Location location) {
    if (location != null) {
      final CrateBlock crateBlock = new CrateBlock(plugin, this, location);

      this.crateBlocks.put(location, crateBlock);

      crateBlock.spawnHologram();
    }
  }

  public void removeLocation(final Location location) {
    final Iterator<Entry<Location, CrateBlock>> locationIterator = this.crateBlocks.entrySet().iterator();

    while (locationIterator.hasNext()) {
      final Entry<Location, CrateBlock> entry = locationIterator.next();
      final Location location1 = entry.getKey();

      if (location.equals(location1)) {
        entry.getValue().despawnHologram();
        locationIterator.remove();
      }
    }
  }

  public boolean isInventory(Inventory inventory) {
    return this.inventory == inventory;
  }

  public void setRows(final int rows) {
    final Inventory inventory = Bukkit.createInventory(null, rows * 9);

    if (this.inventory != null) {
      InventoryUtil.copy(this.inventory, inventory);
      InventoryUtil.close(this.inventory);
      InventoryUtil.clear(this.inventory);
    }

    this.inventory = inventory;
  }

  public void addItem(final ItemStack item) {
    this.inventory.addItem(item);
  }
}
