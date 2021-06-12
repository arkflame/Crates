package dev._2lstudios.crates.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigurationUtil {
  private final Plugin plugin;
  
  public ConfigurationUtil(Plugin plugin) {
    this.plugin = plugin;
  }
  
  public YamlConfiguration getConfiguration(String filePath) {
    File file = new File(replaceDataFolder(filePath));
    if (file.exists())
      return YamlConfiguration.loadConfiguration(file); 
    return new YamlConfiguration();
  }
  
  public void createConfiguration(String file) {
    Logger logger = this.plugin.getLogger();
    File configFile = new File(replaceDataFolder(file));
    String configFileName = configFile.getName();
    if (!configFile.exists()) {
      try {
        InputStream inputStream = this.plugin.getClass().getClassLoader().getResourceAsStream(configFileName);
        File parentFile = configFile.getParentFile();
        if (parentFile != null)
          parentFile.mkdirs(); 
        if (inputStream != null) {
          Files.copy(inputStream, configFile.toPath(), new java.nio.file.CopyOption[0]);
          logger.info("File '" + configFile + "' had been created!");
        } else {
          configFile.createNewFile();
        } 
        inputStream.close();
      } catch (Exception e) {
        logger.info("An exception was caught while creating '" + configFileName + "'!");
      } 
    } else {
      logger.info("Skipped '" + configFileName + "' creation because it already exists!");
    } 
  }
  
  public void saveConfiguration(YamlConfiguration yamlConfiguration, String file, boolean async) {
    if (async) {
      this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> saveConfiguration(yamlConfiguration, replaceDataFolder(file)));
    } else {
      saveConfiguration(yamlConfiguration, replaceDataFolder(file));
    } 
  }
  
  private void saveConfiguration(YamlConfiguration yamlConfiguration, String file) {
    try {
      yamlConfiguration.save(replaceDataFolder(file));
    } catch (IOException e) {
      this.plugin.getLogger().info("[%pluginname%] Unable to save configuration file!".replace("%pluginname%", this.plugin
            .getDescription().getName()));
    } 
  }
  
  public void deleteConfiguration(String filePath, boolean async) {
    if (async) {
      this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> deleteConfiguration(filePath));
    } else {
      deleteConfiguration(filePath);
    } 
  }
  
  private void deleteConfiguration(String filePath) {
    File file = new File(replaceDataFolder(filePath));
    if (file.exists())
      file.delete(); 
  }
  
  private String replaceDataFolder(String filePath) {
    return filePath.replace("%datafolder%", this.plugin.getDataFolder().toPath().toString());
  }
}
