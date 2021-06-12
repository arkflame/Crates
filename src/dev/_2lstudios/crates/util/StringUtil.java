package dev._2lstudios.crates.util;

import java.util.List;
import org.bukkit.ChatColor;

public class StringUtil {
  public static List<String> replace(List<String> strings, String replaced, String replacer) {
    for (int i = 0; i < strings.size(); i++)
      strings.set(i, ((String)strings.get(i)).replace(replaced, replacer)); 
    return strings;
  }
  
  public static List<String> translateAlternateColorCodes(char character, List<String> strings) {
    for (int i = 0; i < strings.size(); i++)
      strings.set(i, ChatColor.translateAlternateColorCodes(character, strings.get(i))); 
    return strings;
  }
}
