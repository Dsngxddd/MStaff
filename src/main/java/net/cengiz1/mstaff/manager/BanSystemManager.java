package net.cengiz1.mstaff.manager;

import me.leoko.advancedban.manager.PunishmentManager;
import me.leoko.advancedban.utils.Punishment;
import me.leoko.advancedban.utils.PunishmentType;
import me.leoko.advancedban.utils.SQLQuery;
import net.cengiz1.mstaff.MStaff;
import java.util.List;
import java.util.stream.Collectors;

public class BanSystemManager {
    public static List<String> getBans(String playerName) {
        boolean useLiteBans = MStaff.getInstance().getConfig().getBoolean("banSystem.litebans");
        boolean useAdvancedBan = MStaff.getInstance().getConfig().getBoolean("banSystem.advancedban");

        if (useLiteBans) {
            /*
            return getLiteBans(playerName);
             */
        } else if (useAdvancedBan) {
            return getAdvancedBans(playerName);
        }

        return List.of("Eklenti bilgisi bulunamadı.");
    }

/*
  private static List<String> getLiteBans(String playerName) {
    }

 */

    private static List<String> getAdvancedBans(String playerName) {
        try {
            PunishmentManager punishmentManager = PunishmentManager.get();
            List<Punishment> punishments = punishmentManager.getPunishments(SQLQuery.valueOf(playerName), PunishmentType.BAN);

            return punishments.stream()
                    .map(punishment -> {
                        String reason = punishment.getReason();
                        long start = punishment.getStart();
                        long end = punishment.getEnd();

                        String duration;
                        if (end == -1) {
                            duration = "Kalıcı";
                        } else {
                            long remainingTime = end - System.currentTimeMillis();
                            duration = remainingTime > 0
                                    ? formatDuration(remainingTime)
                                    : "Süresi dolmuş";
                        }

                        return "Sebep: " + reason + " - Süre: " + duration;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of("AdvancedBan yüklenemedi!");
        }
    }

    private static String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        return days + " gün, " + (hours % 24) + " saat, " + (minutes % 60) + " dakika";
    }


}
