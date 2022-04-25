package tr.mustafaesattemel.joinleavemessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinLeaveMessages extends JavaPlugin implements CommandExecutor, Listener {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("reloadjlm").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Join - Leave Messages plugin has been started.");

    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Join - Leave Messages plugin is closing. See ya.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        if (e.getPlayer().hasPlayedBefore()){

            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&',getConfig()
                    .getString("Has-Played-Before").replace("%player%",e.getPlayer().getName())));


        }else{

            String firstMessage = getConfig().getString("First-Join-Message").replace("%player%",e.getPlayer().getName());
            getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',firstMessage));
            e.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){

        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&',getConfig()
                .getString("Quit-Leave-Message").replace("%player%",e.getPlayer().getName())));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("reloadjlm")){
            if(sender instanceof Player){
                if (sender.hasPermission("reload.jlm")){
                    this.reloadConfig();
                    sender.sendMessage("Join Leave Messages reloaded.");
                }else{
                    sender.sendMessage("You don't have permission for that.");
                }

            }

        }
        return true;
    }
}
