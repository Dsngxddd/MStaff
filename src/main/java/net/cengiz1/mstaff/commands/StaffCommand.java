package net.cengiz1.mstaff.commands;

import net.cengiz1.mstaff.utils.DailyReportScheduler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Bu komutu yalnızca oyuncular kullanabilir.");
            return true;
        }

        if (!player.hasPermission("staffmanager.command.staff")) {
            player.sendMessage("Bu komutu kullanmak için yetkiniz yok.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("Bir oyuncu adı belirtmelisiniz veya 'senddiscord' komutunu kullanmalısınız.");
            return true;
        }

        if (args[0].equalsIgnoreCase("senddiscord")) {
            // Günlük raporu Discord'a gönder
            new DailyReportScheduler().run();
            player.sendMessage("Günlük rapor Discord'a gönderildi!");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage("Belirttiğiniz oyuncu çevrimdışı veya bulunamadı.");
            return true;
        }

        StaffGUI.openStaffMenu(player, target);
        return true;
    }
}