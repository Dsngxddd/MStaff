package net.cengiz1.mstaff;

import net.cengiz1.mstaff.commands.StaffCommand;
import net.cengiz1.mstaff.manager.ConfigManager;

import net.cengiz1.mstaff.manager.DatabaseManager;
import net.cengiz1.mstaff.utils.DailyReportScheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MStaff extends JavaPlugin {
    private static MStaff instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        ConfigManager.initialize(getConfig());
        DatabaseManager.connect();

        String dailyReportTime = ConfigManager.getDailyReportTime();
        if (dailyReportTime != null) {
            String[] timeParts = dailyReportTime.split(":");
            try {
                int hour = Integer.parseInt(timeParts[0]);
                int minute = Integer.parseInt(timeParts[1]);
                long currentTime = System.currentTimeMillis();
                long reportTime = currentTime + (hour * 3600 + minute * 60) * 1000;
                long delay = reportTime - currentTime; // Gecikme süresi
                long delayInTicks = delay / 50;

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        new DailyReportScheduler().run();
                    }
                }.runTaskTimer(this, delayInTicks, 20L * 60 * 60 * 24);
            } catch (NumberFormatException e) {
                getLogger().warning("dailyReportTime config değeri geçersiz: " + dailyReportTime);
            }
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
