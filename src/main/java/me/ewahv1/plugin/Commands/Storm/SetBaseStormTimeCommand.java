package me.ewahv1.plugin.Commands.Storm;

import me.ewahv1.plugin.Database.Connection;
import me.ewahv1.plugin.Listeners.Storm.StormListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;

public class SetBaseStormTimeCommand implements CommandExecutor {

    private StormListener stormListener;

    public SetBaseStormTimeCommand(StormListener stormListener) {
        this.stormListener = stormListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                try {
                    int baseStormTime = Integer.parseInt(args[0]);
                    stormListener.setBaseStormTime(baseStormTime);

                    // Obtiene una conexión a la base de datos
                    java.sql.Connection connection = Connection.getConnection();

                    // Crea un objeto PreparedStatement para enviar consultas SQL a la base de datos
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE StormSettings SET BaseStormTime = ? WHERE ID = 1");

                    // Establece los valores de las variables en la consulta SQL
                    preparedStatement.setInt(1, baseStormTime); // Establece el tiempo base de la tormenta

                    // Ejecuta la consulta SQL
                    preparedStatement.executeUpdate();

                    player.sendMessage("El tiempo base de la tormenta se ha establecido en " + baseStormTime + " segundos.");
                } catch (NumberFormatException e) {
                    player.sendMessage("Por favor, introduce un número válido.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                player.sendMessage("Uso: /setbasestormtime <segundos>");
            }
        } else {
            sender.sendMessage("Este comando solo puede ser utilizado por un jugador.");
        }
        return true;
    }
}