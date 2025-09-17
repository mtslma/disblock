package xyz.mtslma.talkBlock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.mtslma.talkBlock.TalkBlock;
import xyz.mtslma.talkBlock.managers.ConfigManager;
import xyz.mtslma.talkBlock.managers.DiscordManager;

public class PlayerConnectionListener implements Listener {

    private final TalkBlock plugin;
    private final DiscordManager discordManager;

    public PlayerConnectionListener(TalkBlock plugin) {
        this.plugin = plugin;
        this.discordManager = plugin.getDiscordManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Verifica se a mensagem de entrada está ativada no config.yml
        if (!ConfigManager.isJoinMessageEnabled()) {
            return;
        }

        // Pega a mensagem do config e substitui o placeholder %player% pelo nome do jogador
        String message = ConfigManager.getJoinMessage()
                .replace("%player%", event.getPlayer().getName());

        // Envia a mensagem para o Discord
        // Usamos um novo método que vamos criar no DiscordManager
        discordManager.sendSystemMessage(message);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Verifica se a mensagem de saída está ativada no config.yml
        if (!ConfigManager.isQuitMessageEnabled()) {
            return;
        }

        // Pega a mensagem do config e substitui o placeholder %player% pelo nome do jogador
        String message = ConfigManager.getQuitMessage()
                .replace("%player%", event.getPlayer().getName());

        // Envia a mensagem para o Discord
        discordManager.sendSystemMessage(message);
    }
}