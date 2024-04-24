package org.zimbls.DieLigaDerKeks.util;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Properties;
import java.util.UUID;

public class LanguagePreferencesBasedProperties {
   private static final String DEFAULT_LANGUAGE = "en";
   private static final List<String> validLanguages = Arrays.asList("en", "de");
   private static ConcurrentHashMap<UUID, String> languagePreferences = new ConcurrentHashMap<>();

   public static void setLanguagePreference(UUID player, String language) throws Exception {
      if (!validLanguages.contains(language)) {
         throw new Exception("Invalid language. Please choose either 'en' or 'de'.");
      }
      languagePreferences.put(player, language);
   }

   public static String getProperty(UUID player, String key) throws Exception {
      return getLanguageProperties(player).getProperty(key);
   }

   public static Properties getLanguageProperties(UUID player) throws Exception {
      String language = languagePreferences.getOrDefault(player, DEFAULT_LANGUAGE);
      return loadLanguageFile(language);
   }

   private static Properties loadLanguageFile(String language) throws Exception {
      Properties properties = new Properties();
      try (InputStream resourceStream = LanguagePreferencesBasedProperties.class
              .getResourceAsStream("/messages_" + language + ".properties")) {
         properties.load(resourceStream);
      } catch (Exception e) {
         System.err.println("Failed to load language file for language: " + language);
         throw e;
      }
      return properties;
   }
}
