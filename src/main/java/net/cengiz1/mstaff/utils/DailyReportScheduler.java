package net.cengiz1.mstaff.utils;

import net.cengiz1.mstaff.MStaff;
import net.cengiz1.mstaff.manager.DatabaseManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DailyReportScheduler extends BukkitRunnable {
    @Override
    public void run() {
        try {
            Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM StaffActivity WHERE date = CURDATE()");
            ResultSet resultSet = statement.executeQuery();

            StringBuilder report = new StringBuilder("**Günlük Yetkili Raporu**\n");
            while (resultSet.next()) {
                report.append(String.format(
                        "- Yetkili: %s\n" +
                                "  * Banlar: %d\n" +
                                "  * Mute'lar: %d\n" +
                                "  * Kickler: %d\n" +
                                "  * Warn'lar: %d\n" +
                                "  * Aktif Süre: %d dakika\n",
                        resultSet.getString("staff_name"),
                        resultSet.getInt("bans"),
                        resultSet.getInt("mutes"),
                        resultSet.getInt("kicks"),
                        resultSet.getInt("warns"),
                        resultSet.getInt("active_time")
                ));
            }

            WebhookUtility.sendWebhook(MStaff.getInstance().getConfig().getString("webhook"), report.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
