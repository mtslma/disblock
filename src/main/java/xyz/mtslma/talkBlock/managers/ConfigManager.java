package xyz.mtslma.talkBlock.managers;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.mtslma.talkBlock.TalkBlock;

public class ConfigManager {

    private static String botToken;
    private static String channelId;
    private static String webhookUrl;
    private static String avatarUrl;

    // Configurações de entrada e saída do servidor
    private static boolean joinMessageEnabled;
    private static String joinMessage;
    private static boolean quitMessageEnabled;
    private static String quitMessage;

    // Função para carregar as configurações do plugin
    public static void load(TalkBlock plugin) {

        // Pegando uma instância do config.yml
        FileConfiguration config = plugin.getConfig();

        // Atribuindo valores para as variáveis
        botToken = config.getString("bot-token");
        channelId = config.getString("channel-id");
        webhookUrl = config.getString("webhook-url");
        avatarUrl = config.getString("avatar-url");

        joinMessageEnabled = config.getBoolean("connection-messages.join.enabled");
        joinMessage = config.getString("connection-messages.join.message");
        quitMessageEnabled = config.getBoolean("connection-messages.quit.enabled");
        quitMessage = config.getString("connection-messages.quit.message");
    }

    // Getters e Setters
    public static String getAvatarUrl() {
        return avatarUrl;
    }

    public static void setAvatarUrl(String avatarUrl) {
        ConfigManager.avatarUrl = avatarUrl;
    }

    public static String getBotToken() {
        return botToken;
    }

    public static void setBotToken(String botToken) {
        ConfigManager.botToken = botToken;
    }

    public static String getChannelId() {
        return channelId;
    }

    public static void setChannelId(String channelId) {
        ConfigManager.channelId = channelId;
    }

    public static String getWebhookUrl() {
        return webhookUrl;
    }

    public static void setWebhookUrl(String webhookUrl) {
        ConfigManager.webhookUrl = webhookUrl;
    }

    public static String getJoinMessage() {
        return joinMessage;
    }

    public static void setJoinMessage(String joinMessage) {
        ConfigManager.joinMessage = joinMessage;
    }

    public static boolean isJoinMessageEnabled() {
        return joinMessageEnabled;
    }

    public static void setJoinMessageEnabled(boolean joinMessageEnabled) {
        ConfigManager.joinMessageEnabled = joinMessageEnabled;
    }

    public static String getQuitMessage() {
        return quitMessage;
    }

    public static void setQuitMessage(String quitMessage) {
        ConfigManager.quitMessage = quitMessage;
    }

    public static boolean isQuitMessageEnabled() {
        return quitMessageEnabled;
    }

    public static void setQuitMessageEnabled(boolean quitMessageEnabled) {
        ConfigManager.quitMessageEnabled = quitMessageEnabled;
    }
}
