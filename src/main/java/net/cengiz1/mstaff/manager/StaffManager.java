package net.cengiz1.mstaff.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class StaffManager {
    private static String STAFF_PERMISSION = ConfigManager.getStaffPermission();
    private static final Map<String, Integer> kickCounts = new HashMap<>();
    private static final Map<String, Integer> warnCounts = new HashMap<>();
    private static final Map<String, Integer> muteCounts = new HashMap<>();
    private static final Map<String, Integer> banCounts = new HashMap<>();

    /**
     * Oyuncunun yetkili olup olmadığını kontrol eder.
     *
     * @param player Kontrol edilecek oyuncu
     * @return Yetkiliyse true, değilse false
     */
    public static boolean isStaff(Player player) {
        return player.hasPermission(STAFF_PERMISSION);
    }

    /**
     * Oyuncunun kick sayısını döndürür.
     *
     * @param player Kontrol edilecek oyuncu
     * @return Kick sayısı
     */
    public static int getKickCount(Player player) {
        return kickCounts.getOrDefault(player.getName(), 0);
    }

    /**
     * Oyuncunun uyarı sayısını döndürür.
     *
     * @param player Kontrol edilecek oyuncu
     * @return Uyarı sayısı
     */
    public static int getWarnCount(Player player) {
        return warnCounts.getOrDefault(player.getName(), 0);
    }

    /**
     * Oyuncunun mute sayısını döndürür.
     *
     * @param player Kontrol edilecek oyuncu
     * @return Mute sayısı
     */
    public static int getMuteCount(Player player) {
        return muteCounts.getOrDefault(player.getName(), 0);
    }

    /**
     * Oyuncunun ban sayısını döndürür.
     *
     * @param player Kontrol edilecek oyuncu
     * @return Ban sayısı
     */
    public static int getBanCount(Player player) {
        return banCounts.getOrDefault(player.getName(), 0);
    }

    /**
     * Oyuncunun kick sayısını artırır.
     *
     * @param player İşlem yapılacak oyuncu
     */
    public static void incrementKickCount(Player player) {
        kickCounts.put(player.getName(), getKickCount(player) + 1);
    }

    /**
     * Oyuncunun uyarı sayısını artırır.
     *
     * @param player İşlem yapılacak oyuncu
     */
    public static void incrementWarnCount(Player player) {
        warnCounts.put(player.getName(), getWarnCount(player) + 1);
    }

    /**
     * Oyuncunun mute sayısını artırır.
     *
     * @param player İşlem yapılacak oyuncu
     */
    public static void incrementMuteCount(Player player) {
        muteCounts.put(player.getName(), getMuteCount(player) + 1);
    }

    /**
     * Oyuncunun ban sayısını artırır.
     *
     * @param player İşlem yapılacak oyuncu
     */
    public static void incrementBanCount(Player player) {
        banCounts.put(player.getName(), getBanCount(player) + 1);
    }

    /**
     * Oyuncunun işlem istatistiklerini sıfırlar.
     *
     * @param player Sıfırlanacak oyuncu
     */
    public static void resetCounts(Player player) {
        kickCounts.remove(player.getName());
        warnCounts.remove(player.getName());
        muteCounts.remove(player.getName());
        banCounts.remove(player.getName());
    }


    public static int getActionCount(String playerName, String actionType) {
        try {
            Connection connection = DatabaseManager.getConnection();
            String query = "SELECT COUNT(*) AS count FROM StaffActions WHERE player_name = ? AND action_type = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playerName);
            statement.setString(2, actionType);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int getActiveMinutes(String playerName) {
        try {
            Connection connection = DatabaseManager.getConnection();
            String query = "SELECT SUM(active_time) AS total_minutes FROM StaffActivity WHERE player_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total_minutes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}