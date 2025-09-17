package xyz.mtslma.talkBlock.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.mtslma.talkBlock.TalkBlock;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordManager {

    private final TalkBlock plugin;

    // O construtor recebe a instância principal do plugin
    public DiscordManager(TalkBlock plugin) {
        this.plugin = plugin;
    }

    public void sendWebhookMessage(Player player, String message) {
        String webhookUrl = ConfigManager.getWebhookUrl();
        if (webhookUrl == null || webhookUrl.isEmpty() || webhookUrl.equals("SUA_URL_DO_WEBHOOK_AQUI")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                String avatarUrl = ConfigManager.getAvatarUrl().replace("%uuid%", player.getUniqueId().toString());
                String escapedMessage = message.replace("\"", "\\\"");

                String jsonPayload = "{"
                        + "\"username\": \"" + player.getName() + "\","
                        + "\"avatar_url\": \"" + avatarUrl + "\","
                        + "\"content\": \"" + escapedMessage + "\""
                        + "}";

                URL url = new URL(webhookUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "TalkBlock Minecraft Plugin");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode >= 300) {
                    plugin.getLogger().warning("Falha ao enviar webhook, código de resposta: " + responseCode);
                    plugin.getLogger().warning("Resposta: " + new String(connection.getErrorStream().readAllBytes()));
                }

                connection.disconnect();

            } catch (Exception e) {
                plugin.getLogger().severe("Ocorreu um erro ao tentar enviar a mensagem via webhook:");
                e.printStackTrace();
            }
        });
    }

    public void sendSystemMessage(String message) {
        String webhookUrl = ConfigManager.getWebhookUrl();
        if (webhookUrl == null || webhookUrl.isEmpty() || webhookUrl.equals("SUA_URL_DO_WEBHOOK_AQUI")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                // Monta um JSON simples, apenas com o conteúdo da mensagem
                String escapedMessage = message.replace("\"", "\\\"");
                String jsonPayload = "{\"content\": \"" + escapedMessage + "\"}";

                URL url = new URL(webhookUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("User-Agent", "TalkBlock Minecraft Plugin");
                connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                connection.getResponseCode(); // Apenas envia, não precisa de muito tratamento de erro aqui
                connection.disconnect();

            } catch (Exception e) {
                plugin.getLogger().warning("Ocorreu um erro ao tentar enviar uma mensagem de sistema via webhook.");
                e.printStackTrace();
            }
        });
    }
}