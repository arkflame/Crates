package dev._2lstudios.crates.player;

import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.util.ConfigurationUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class CratesPlayerManager {
  private final CrateManager crateManager;
  
  private final Map<UUID, CratesPlayer> cratesPlayers;
  
  private final ConfigurationUtil configurationUtil;
  
  private final Server server;
  
  public CratesPlayerManager(CrateManager crateManager, ConfigurationUtil configurationUtil, Server server) {
    this.crateManager = crateManager;
    this.cratesPlayers = new HashMap<>();
    this.configurationUtil = configurationUtil;
    this.server = server;
  }
  
  public CratesPlayer getPlayer(UUID uuid) {
    if (this.cratesPlayers.containsKey(uuid))
      return this.cratesPlayers.get(uuid); 
    Map<Crate, Integer> pendingKeys = new HashMap<>();
    String dataFilePath = "%datafolder%/data/" + uuid + ".yml";
    YamlConfiguration yamlConfiguration = this.configurationUtil.getConfiguration(dataFilePath);
    if (yamlConfiguration != null) {
      ConfigurationSection pendingKeysSection = yamlConfiguration.getConfigurationSection("pendingkeys");
      if (pendingKeysSection != null) {
        Collection<String> keys = pendingKeysSection.getKeys(false);
        if (keys != null)
          for (String key : keys) {
            int amount = pendingKeysSection.getInt(key);
            pendingKeys.put(this.crateManager.getCrate(key), Integer.valueOf(amount));
          }  
      } 
    } 
    CratesPlayer cratesPlayer = new CratesPlayer(pendingKeys, this.server, uuid);
    this.cratesPlayers.put(uuid, cratesPlayer);
    return cratesPlayer;
  }
  
  public void savePlayer(UUID uuid, boolean async) {
    if (this.cratesPlayers.containsKey(uuid)) {
      CratesPlayer cratesPlayer = this.cratesPlayers.get(uuid);
      if (cratesPlayer.isChanged()) {
        Map<Crate, Integer> pendingKeys = cratesPlayer.getPendingKeys();
        if (!pendingKeys.isEmpty()) {
          YamlConfiguration dataFile = new YamlConfiguration();
          for (Map.Entry<Crate, Integer> entry : pendingKeys.entrySet())
            dataFile.set("pendingkeys." + ((Crate)entry.getKey()).getName(), entry.getValue()); 
          this.configurationUtil.saveConfiguration(dataFile, "%datafolder%/data/" + uuid + ".yml", async);
        } else {
          this.configurationUtil.deleteConfiguration("%datafolder%/data/" + uuid + ".yml", async);
        } 
        cratesPlayer.setChanged(false);
      } 
      this.cratesPlayers.remove(uuid);
    } 
  }
}
