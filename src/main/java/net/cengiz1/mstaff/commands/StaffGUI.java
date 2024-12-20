package net.cengiz1.mstaff.commands;

import net.cengiz1.mstaff.manager.BanSystemManager;
import net.cengiz1.mstaff.manager.StaffManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StaffGUI {

    public static void openStaffMenu(Player player, Player target) {
        if (!StaffManager.isStaff(target)) {
            player.sendMessage("Bu oyuncu yetkili değil!");
            return;
        }Inventory gui = Bukkit.createInventory(null, 27, target.getName() + " İstatistikleri");
        int activeMinutes = StaffManager.getActiveMinutes(target.getName());
        String activeTime = formatTime(activeMinutes);

        gui.setItem(11, createItem(Material.PAPER, "Aktif Süre", "Toplam Süre: " + activeTime));

        int bans = StaffManager.getActionCount(target.getName(), "ban");
        int mutes = StaffManager.getActionCount(target.getName(), "mute");
        int kicks = StaffManager.getActionCount(target.getName(), "kick");
        int warns = StaffManager.getActionCount(target.getName(), "warn");

        gui.setItem(13, createItem(Material.BOOK, "İşlem Sayıları",
                "Ban: " + bans,
                "Mute: " + mutes,
                "Kick: " + kicks,
                "Warn: " + warns));

        List<String> banDetails = BanSystemManager.getBans(target.getName());
        if (banDetails.isEmpty()) {
            banDetails.add("Bu oyuncunun ban kaydı yok");
        }
        gui.setItem(15, createItem(Material.WRITABLE_BOOK, "Ban Detayları", banDetails.toArray(new String[0])));
        player.openInventory(gui);
    }

    private static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            List<String> loreList = new ArrayList<>();
            for (String line : lore) {
                loreList.add(line);
            }
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
        return item;
    }

    private static String formatTime(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return hours + " saat " + remainingMinutes + " dakika";
    }
}