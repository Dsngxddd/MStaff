package net.cengiz1.mstaff.manager;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static FileConfiguration config;

    public static void initialize(FileConfiguration fileConfig) {
        config = fileConfig;
    }

    public static String getMySQLHost() {
        return config.getString("mysql.host", "localhost");
    }

    public static int getMySQLPort() {
        return config.getInt("mysql.port", 3306);
    }

    public static String getMySQLDatabase() {
        return config.getString("mysql.database", "database");
    }

    public static String getMySQLUsername() {
        return config.getString("mysql.username", "user");
    }

    public static String getMySQLPassword() {
        return config.getString("mysql.password", "pass");
    }

    public static String getDiscordWebhookURL() {
        return config.getString("settings.discordWebhookURL", "");
    }

    public static String getDailyReportTime() {
        return config.getString("settings.dailyReportTime", "12:00");
    }

    public static String getStaffPermission() {
        return config.getString("permissions.staff", "staffmanager.staff");
    }
}
