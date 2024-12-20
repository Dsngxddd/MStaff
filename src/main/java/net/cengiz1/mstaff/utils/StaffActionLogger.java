package net.cengiz1.mstaff.utils;

import net.cengiz1.mstaff.MStaff;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaffActionLogger {
    public static void logAction(Player staff, String action, Player target) {
        String webhookUrl = MStaff.getInstance().getConfig().getString("webhook");

        if (webhookUrl == null || webhookUrl.isEmpty()) {
            return; // Webhook URL'si yoksa işlem yapma
        }

        String message = String.format(
                "**Yetkili İşlemi**\n" +
                        "- Yetkili: %s\n" +
                        "- İşlem: %s\n" +
                        "- Hedef: %s\n" +
                        "- Sunucu: %s",
                staff.getName(), action, target.getName(), Bukkit.getServer().getName()
        );

        WebhookUtility.sendWebhook(webhookUrl, message);
    }
}
