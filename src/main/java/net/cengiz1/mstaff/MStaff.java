package net.cengiz1.mstaff;

import net.cengiz1.mstaff.commands.StaffCommand;
import net.cengiz1.mstaff.manager.ConfigManager;

import net.cengiz1.mstaff.manager.DatabaseManager;
import net.cengiz1.mstaff.utils.DailyReportScheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class MStaff extends JavaPlugin {
    private static MStaff instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        ConfigManager.initialize(getConfig());
        DatabaseManager.connect();

        // Günlük rapor planlayıcısı ayarlanıyor
        String dailyReportTime = ConfigManager.getDailyReportTime();
        if (dailyReportTime != null) {
            String[] timeParts = dailyReportTime.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            Bukkit.getScheduler().runTaskTimer(this, new DailyReportScheduler(), 0L, 20L * 60 * (hour * 60 + minute));
        }

        getCommand("staffmanager").setExecutor(new StaffCommand());
    }

    @Override
    public void onDisable() {
        DatabaseManager.disconnect();
    }

    public static MStaff getInstance() {
        return instance;
    }
}
