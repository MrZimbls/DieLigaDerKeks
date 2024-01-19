package org.zimbls.DieLigaDerKeks.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImportMobCsv {
   public static Map<String, Mob> loadMobPoints(String filePath) {
      Map<String, Mob> mobPoints = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
               String mobName = parts[0].trim();
               int points = Integer.parseInt(parts[1].trim());
               String reward = parts[2].trim();
               int amount = Integer.parseInt(parts[3].trim());
               mobPoints.put(mobName, new Mob(mobName, points, reward, amount));
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return mobPoints;
   }
}
