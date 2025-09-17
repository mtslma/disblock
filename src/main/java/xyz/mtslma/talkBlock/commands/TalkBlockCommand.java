package xyz.mtslma.talkBlock.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.mtslma.talkBlock.TalkBlock;
import xyz.mtslma.talkBlock.managers.ConfigManager;

public class TalkBlockCommand implements CommandExecutor {

    private final TalkBlock plugin;

    public TalkBlockCommand(TalkBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifica se o jogador tem a permissão que definimos no plugin.yml
        if (!sender.hasPermission("talkblock.admin")) {
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para usar este comando.");
            return true;
        }

        // Verifica se o comando foi digitado com o sub-comando "reload"
        // Ex: /talkblock reload
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {

            // --- A MÁGICA ACONTECE AQUI ---
            // Recarrega o arquivo config.yml para a memória
            plugin.reloadConfig();
            // Carrega os valores do arquivo para o nosso ConfigManager
            ConfigManager.load(plugin);

            sender.sendMessage(ChatColor.GREEN + "A configuração do TalkBlock foi recarregada com sucesso!");

        } else {
            // Se o comando for digitado errado, envia uma mensagem de ajuda
            sender.sendMessage(ChatColor.YELLOW + "Uso correto: /" + label + " reload");
        }

        return true;
    }
}