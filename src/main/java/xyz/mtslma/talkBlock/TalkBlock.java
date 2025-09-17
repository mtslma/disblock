package xyz.mtslma.talkBlock;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.mtslma.talkBlock.commands.TalkBlockCommand;
import xyz.mtslma.talkBlock.listeners.DiscordChatListener;
import xyz.mtslma.talkBlock.listeners.MinecraftChatListener;
import xyz.mtslma.talkBlock.listeners.PlayerConnectionListener;
import xyz.mtslma.talkBlock.managers.ConfigManager; // NOVO IMPORT
import xyz.mtslma.talkBlock.managers.DiscordManager;

public final class TalkBlock extends JavaPlugin {

    private JDA jda;
    private DiscordManager discordManager;

    @Override
    public void onEnable() {
        // Carrega as configurações
        saveDefaultConfig();
        ConfigManager.load(this);

        // Registrando um comando para testar
        getCommand("talkblock").setExecutor(new TalkBlockCommand(this));

        // Inicializa os gerenciadores
        this.discordManager = new DiscordManager(this);

        try {
            getLogger().info("Iniciando conexão com o Bot do Discord...");
            String token = ConfigManager.getBotToken();

            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .build()
                    .awaitReady();
            // Registrando o listener do Discord
            jda.addEventListener(new DiscordChatListener(this));
            getLogger().info("Bot do Discord conectado com sucesso!");
        } catch (Exception e) {
            getLogger().severe("FALHA AO CONECTAR O BOT DO DISCORD! Verifique o token.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Registrando os listeners do Minecraft
        getServer().getPluginManager().registerEvents(new MinecraftChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this), this);

        getLogger().info("Plugin TalkBlock habilitado!");
    }

    @Override
    public void onDisable() {
        if (jda != null) {
            jda.shutdownNow();
        }
        getLogger().info("Plugin TalkBlock desabilitado.");
    }

    // Getter para os listeners conseguirem acessar o discordManager
    public DiscordManager getDiscordManager() {
        return discordManager;
    }
}