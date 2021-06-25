package dev._2lstudios.crates.crate;

import dev._2lstudios.crates.util.StringUtil;
import java.util.ArrayList;
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

public class Crate {
  private final Plugin plugin;
  private final Collection<Hologram> holograms = new HashSet<>();
  private final Collection<Location> locations;
  private final List<String> hologramLines;
  private final List<String> lore;
  private final String name;
  private final String itemName;
  private final Inventory rewardsInventory;

  private String displayName;

  Crate(final Plugin plugin, final Collection<Location> locations, final List<String> hologramLines, final String name,
      final String displayName, final String keyName, final List<String> lore,
      final Inventory rewardsInventory) {
    this.plugin = plugin;
    this.locations = locations;
    this.hologramLines = hologramLines;
    this.name = name;
    this.displayName = displayName;
    this.itemName = keyName;
    this.lore = lore;
    this.rewardsInventory = rewardsInventory;
  }

  public String getName() {
    return this.name;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getItemDisplayName() {
    return this.itemName.replace("%name%", this.displayName);
  }

  public Collection<Location> getLocations() {
    return this.locations;
  }

  public List<String> getHologramLines() {
    return StringUtil.replace(new ArrayList<>(this.hologramLines), "%name%", this.displayName);
  }

  public List<String> getLore() {
    return StringUtil.replace(new ArrayList<>(this.lore), "%name%", this.displayName);
  }

  public Inventory getInventory() {
    return this.rewardsInventory;
  }

  public ItemStack getKey() {
    final ItemStack keyItemStack = new ItemStack(Material.TRIPWIRE_HOOK);
    final ItemMeta keyItemMeta = keyItemStack.getItemMeta();

    keyItemMeta.setDisplayName(getItemDisplayName());
    keyItemMeta.setLore(getLore());
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
