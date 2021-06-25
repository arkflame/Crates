package dev._2lstudios.crates.util;

import java.util.List;
import org.bukkit.ChatColor;

public class StringUtil {
  public static String replace(String string, final Placeholder... placeholders) {
    for (final Placeholder placeholder : placeholders) {
      string = string.replace(placeholder.getReplaced(), placeholder.getReplacement());
    }

    return string;
  }

  public static List<String> replace(final List<String> strings, final Placeholder... placeholders) {
    for (int i = 0; i < strings.size(); i++) {
      strings.set(i, replace(strings.get(i), placeholders));
    }

    return strings;
  }

  public static List<String> translateColorCodes(final List<String> strings) {
    for (int i = 0; i < strings.size(); i++)
      strings.set(i, ChatColor.translateAlternateColorCodes('&', strings.get(i)));
    return strings;
  }

  public static String translateColorCodes(final String string) {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
}
