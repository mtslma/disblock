package xyz.mtslma.talkBlock.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import xyz.mtslma.talkBlock.TalkBlock;

public class DiscordChatListener extends ListenerAdapter {

    private final TalkBlock plugin;

    public DiscordChatListener(TalkBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Ignora mensagens enviadas por outros bots para evitar loops
        if (event.getAuthor().isBot()) {
            return;
        }

        // Verifica se a mensagem veio do canal configurado no plugin.yml
        String configuredChannelId = plugin.getConfig().getString("channel-id");
        if (!event.getChannel().getId().equals(configuredChannelId)) {
            return;
        }

        // Tag azul pra identificar que a mensagem veio do Discord
        String message = ChatColor.BLUE + "[Discord] "
                + ChatColor.WHITE + event.getAuthor().getEffectiveName()
                + ChatColor.GRAY + ": " + event.getMessage().getContentDisplay();

        // Enviando a mensagem
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.broadcastMessage(message);
        });
    }
}