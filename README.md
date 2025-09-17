# TalkBlock

Plugin para integrar o chat de um servidor Minecraft (PaperMC) com um canal do Discord. Ele cria uma ponte de comunicação bidirecional para que as comunidades interajam em tempo real.

## Funcionalidades

**Chat Bidirecional:** mensagens do Minecraft aparecem no Discord e vice-versa, usando webhook com nome e avatar do jogador.  
**Mensagens de Conexão:** notifica no Discord quando jogadores entram ou saem do servidor (personalizável).

## Compatibilidade

O plugin é funcional em servidores que suportam a versão 1.21.4 do Minecraft. Futuramente pretendo adicionar suporte a mais versões.

## Configurando o projeto

**⚠ Importante: este passo deve ser realizado ANTES da compilação do projeto.**

Navegue até `src/main/resources/` e faça as devidas alterações no arquivo `config.template.yml`, começando pelo nome do arquivo, que deve ser `config.yml`. Após isso, informe os valores de acordo com as sugestões dos campos.

```yaml
# Token do Bot do Discord
# Caso não tenha um token, você pode seguir este tutorial para criar um bot: https://discordjs.guide/preparations/setting-up-a-bot-application.html#creating-your-bot
bot-token: "SEU_TOKEN_AQUI"

# ID do canal do Discord
# Para pegar o ID de um canal, ative o 'Modo de Desenvolvedor' do Discord e clique com o botão direito sobre o canal desejado
channel-id: "SEU_ID_DE_CANAL_AQUI"

# URL do Webhook do canal
# Para criar um Webhook, vá até 'Configurações do canal > Integrações > Webhooks > Novo Webhook'
webhook-url: "SUA_URL_DO_WEBHOOK_AQUI"

# URL para buscar avatar do jogador (%uuid% será substituído pelo UUID do jogador)
avatar-url: "https://visage.surgeplay.com/face/128/%uuid%"

# Mensagens de entrada e saída
# Você pode ativar/desativar usando o campo enabled; a mensagem também pode ser alterada :)
# É possível usar emojis do servidor se passar o ID deles; Ex: '<a:WinkU:865749015047045141> **%player%** entrou no servidor.'
connection-messages:
  join:
    enabled: true
    message: "**%player%** entrou no servidor."
  quit:
    enabled: true
    message: "**%player%** saiu do servidor."
```

## Gerando o `plugin.jar`

01. Clone o repositório  
02. Navegue até o novo diretório com `cd talkblock`  
03. Execute `mvn package` no terminal para compilar o projeto. Caso você não tenha o Maven configurado, é possível realizar essa etapa pelo IntelliJ em: `Maven -> TalkBlock -> Lifecycle -> package`

## Testando o plugin

Para testar o plugin, basta fazer upload do arquivo gerado pelo Maven dentro da pasta `/plugins` do seu servidor e reiniciá-lo para que o plugin seja carregado. Se tudo estiver configurado corretamente, mensagens do Discord aparecerão no Minecraft e vice-versa.

## Extra — Configurando um servidor local simples no Linux

Essa parte é mais um lembrete/revisão de como fiz o setup para subir um servidor na rede local em uma máquina Linux e me conectar a ela via SSH por meio do PowerShell do Windows para testar o plugin.

### Executar no Linux

#### Passo 01: Instalação de pacotes

Instale as ferramentas necessárias para a máquina:

```bash
sudo apt install openjdk-17-jdk openssh-server ufw screen -y
```

#### Passo 02: Configuração do firewall

Abra as portas para SSH (22) e Minecraft (25565):

```bash
sudo ufw allow OpenSSH && sudo ufw allow 25565 && sudo ufw enable
```

#### Passo 03: Identificação do IP

Identifique o IP local da máquina para fazer a conexão remota:

Exemplo de endereço local para conexão: `192.168.15.25`

```bash
hostname -I
```

### Executar no Windows — Instalando o servidor PaperMC

#### Passo 01: Transferir o plugin para o máquina do servidor local

```bash
scp "C:\<seu-caminho-do-plugin>\<talkblock.1.0-SNAPSHOT>.jar" <usuario-linux>@<ip-linux>:~
```

#### Passo 02: Conectar remotamente ao servidor

```bash
ssh <usuario>@<ip-maquina-do-servidor>
```

#### Passo 03: Criar diretório para o servidor Minecraft e os plugins

```bash
mkdir ~/servidor_mc
cd ~/servidor_mc
mkdir plugins
```

#### Passo 04: Mover o `plugin.jar` para a pasta `/plugins` do servidor Minecraft

**⚠ Importante: verifique o nome do arquivo .jar; provavelmente será algo como 'talkblock.1.0-SNAPSHOT.jar'**

```bash
mv ~/plugin.jar plugins/
```

#### Passo 05: Baixar o Paper (1.21.4 para este exemplo)

- Download oficial: https://papermc.io/downloads  
- Outras versões: https://gist.github.com/osipxd/6119732e30059241c2192c4a8d2218d9

```bash
wget https://api.papermc.io/v2/projects/paper/versions/1.21.4/builds/232/downloads/paper-1.21.4-232.jar
```

#### Passo 06: Aceitar o EULA

```bash
echo "eula=true" > eula.txt
```

### Executar no Windows — Rodando o servidor PaperMC

**Caso você não tenha o 'screen' instalado, execute este comando:**

```bash
sudo apt install screen
```

#### Passo 01: Inicie uma sessão 'screen'

```bash
screen -S minecraft
```

#### Passo 02: Execute o servidor (dentro do 'screen')

```bash
java -Xms2G -Xmx4G -jar paper.1.21.4-232.jar --nogui
```

#### Comandos úteis no terminal

- `stop` → Para o processo do servidor  
- `exit` → Encerra a sessão screen atual  
- `CTRL + A → D` → Volta para o terminal original  
- `screen -r minecraft` → Retorna à sessão 'minecraft'  
- `screen -ls` → Lista as sessões screen ativas  
- `fkill screen` → Encerra todas as sessões; útil se houver problema de porta em uso

## Conclusão

Pronto :) Se tudo deu certo, o servidor estará rodando no IP local da sua máquina (aquele do `hostname -I`).

Inicie o jogo pelo launcher com a versão 1.21.4, adicione o servidor na lista de mundos e teste.
