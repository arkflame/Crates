package dev._2lstudios.crates.crate;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class CrateBlock {
    private final Plugin plugin;
    private final Crate crate;
    private final Location location;
    private Hologram hologram;

    CrateBlock(final Plugin plugin, final Crate crate, final Location location) {
        this.plugin = plugin;
        this.crate = crate;
        this.location = location;
    }

    private void appendLines(final Hologram hologram) {
        for (final String line : crate.getHologramLines()) {
            hologram.appendTextLine(line);
        }
    }

    public void spawnHologram() {
        final Location clonedLocation = location.clone().add(0, 1.5f, 0);
        final Hologram hologram = HologramsAPI.createHologram(plugin, clonedLocation);

        appendLines(hologram);

        this.hologram = hologram;
    }

    public void despawnHologram() {
        if (hologram != null) {
            hologram.delete();
            hologram = null;
        }
    }
}
