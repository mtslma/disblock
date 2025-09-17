package xyz.mtslma.talkBlock.listeners; // Use seu pacote

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import xyz.mtslma.talkBlock.TalkBlock;

public class MinecraftChatListener implements Listener {

    private final TalkBlock plugin;

    public MinecraftChatListener(TalkBlock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        plugin.getDiscordManager().sendWebhookMessage(event.getPlayer(), event.getMessage());
    }
}