package me.ewahv1.plugin.Commands.Totem;

import me.ewahv1.plugin.Listeners.Items.FailTotemListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;

import me.ewahv1.plugin.Database.Connection;

public class ToggleTotemCommand implements CommandExecutor {
    private FailTotemListener failTotemListener;

    public ToggleTotemCommand(FailTotemListener failTotemListener) {
        this.failTotemListener = failTotemListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean isTotemActive = failTotemListener.isTotemActive();
            failTotemListener.setTotemActive(!isTotemActive);
            updateDatabase(!isTotemActive);
            player.sendMessage("La mecánica de failtotem ahora está " + (!isTotemActive ? "activada" : "desactivada") + ".");
        } else {
            sender.sendMessage("Este comando solo puede ser utilizado por un jugador.");
        }
        return true;
    }

    private void updateDatabase(boolean status) {
        try {
            PreparedStatement statement = Connection.getConnection().prepareStatement("UPDATE totemsettings SET Status = ? WHERE ID = 1");
            statement.setBoolean(1, status);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
