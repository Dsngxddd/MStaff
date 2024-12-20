package net.cengiz1.mstaff.utils;

import net.cengiz1.mstaff.MStaff;
import org.bukkit.Bukkit;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebhookUtility {
    public static void sendWebhook(String webhookUrl, String message) {
        Bukkit.getScheduler().runTaskAsynchronously(MStaff.getInstance(), () -> {
            try {
                URL url = new URL(webhookUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");

                String jsonPayload = "{\"content\": \"" + message + "\"}";

                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(jsonPayload.getBytes());
                    outputStream.flush();
                }

                connection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
