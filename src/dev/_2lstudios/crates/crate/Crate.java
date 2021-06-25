package dev._2lstudios.crates.crate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

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

public class Crate {
  private final Plugin plugin;
  private final CratesConfig cratesConfig;
  private final Collection<Hologram> holograms = new HashSet<>();
  private final Collection<Location> locations;
  private final String name;
  private final Inventory rewardsInventory;

  private String displayName;

  Crate(final Plugin plugin, final CratesConfig cratesConfig, final Collection<Location> locations, final String name,
      final String displayName,
      final Inventory rewardsInventory) {
    this.plugin = plugin;
    this.cratesConfig = cratesConfig;
    this.locations = locations;
    this.name = name;
    this.displayName = displayName;
    this.rewardsInventory = rewardsInventory;
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
    return this.locations;
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
    return this.rewardsInventory;
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
    for (final Location location1 : this.locations) {
      if (block.equals(location1.getBlock()))
        return true;
    }
    return false;
  }

  public boolean openKey(final Player player, final ItemStack itemStack) {
    if (player != null && isKey(itemStack)) {
      final PlayerInventory playerInventory = player.getInventory();
      if (playerInventory.firstEmpty() != -1) {
        final ItemStack[] rewards = trimContents(this.rewardsInventory.getContents())
            .<ItemStack>toArray(new ItemStack[0]);
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
    player.openInventory(this.rewardsInventory);
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

  private void appendLines(final Hologram hologram) {
    for (final String line : getHologramLines()) {
      hologram.appendTextLine(line);
    }
  }

  private void spawnHologram(final Location location) {
    final Location clonedLocation = location.clone().add(0, 1.5f, 0);
    final Hologram hologram = HologramsAPI.createHologram(plugin, clonedLocation);

    appendLines(hologram);

    this.holograms.add(hologram);
  }

  public void spawnHolograms() {
    despawnHolograms();

    for (final Location location : locations) {
      spawnHologram(location);
    }
  }

  public void despawnHolograms() {
    for (final Hologram hologram : holograms) {
      hologram.delete();
    }

    holograms.clear();
  }

  public void despawnHologram(final Location location) {
    final Iterator<Hologram> iterator = holograms.iterator();
    final Block block = location.getBlock();

    while (iterator.hasNext()) {
      final Hologram hologram = iterator.next();
      final Location location1 = hologram.getLocation();

      if (location1 == null || block == location1.getBlock()) {
        hologram.delete();
        iterator.remove();
      }
    }
  }

  public void addLocation(final Location location) {
    if (location != null) {
      this.locations.add(location);

      spawnHologram(location);
    }
  }

  public void removeLocation(final Location location) {
    final Iterator<Location> locationIterator = this.locations.iterator();
    final Block block = location.getBlock();

    while (locationIterator.hasNext()) {
      final Location location1 = locationIterator.next();

      if (location1 == null || block == location1.getBlock()) {
        locationIterator.remove();
      }
    }

    despawnHologram(location);
  }
}
