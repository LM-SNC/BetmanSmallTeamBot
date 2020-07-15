package art.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RrMiniGames extends ListenerAdapter {

    private ConnectionManager connectionManager;
    private boolean isGame;
    private Member firstMemberInGame = null;
    private Member secondMemberInGame = null;
    private boolean isSearching;
    boolean[] gameActivity = new boolean[128];
    int gameId;

    private MessageReaction.ReactionEmote searchReaction = null;

    private Message searchMessage = null;
    private int winnerMoney = 0;
    CustomManager customManager;
    private int bet;
    int searchBet;

    TextChannel textChannel;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        connectionManager = new ConnectionManager();
        customManager = new CustomManager();
//        new Thread(this::sleepMethod).start(); - true method
        //new Thread(() -> {code code code}).start();
        System.out.println(isGame);
        textChannel = event.getJDA().getTextChannelById("732375187252838521");
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {


//            int score = 5;
//            connectionManager.onRemoveScore(event.getMember().getId(),score);
        if (!event.getTextChannel().getId().equalsIgnoreCase(textChannel.getId())) {
            return;
        }

        String[] message = event.getMessage().getContentRaw().split(" ");
        if (!isGame && !event.getAuthor().isBot()) {
            if (isSearching) {

                customManager.embedNotification(textChannel, event.getMember(), "<:xmark:732621758787616808>", Color.RED, 0, "Невозможно начать поиск оппонента");
                return;
            }
            if (event.getChannel().getId().equalsIgnoreCase("732375187252838521") && message[0].equalsIgnoreCase("!russian-roulette") && message.length == 2) {
                if (message[1].matches("[-+]?\\d+")) {
                    if (Integer.parseInt(message[1]) <= 0) {
                        customManager.embedNotification(textChannel, event.getMember(), "<:xmark:732621758787616808>", Color.RED, 0, "Число должно быть больше нуля");
                        return;
                    }
                    int money;
                    money = connectionManager.getMoney(event.getMember().getId());

                    if (money >= Integer.parseInt(message[1])) {
//                    isGame = true;
//                    new Thread(this::sleepMethod).start();
                        gameId++;
                        new Thread(() -> timeout(gameId, event.getMember())).start();
                        isSearching = true;
                        firstMemberInGame = event.getMember();
                        bet = Integer.parseInt(message[1]);
                        searchBet = bet;
                        searchMessage = customManager.embedNotification(textChannel, event.getMember(), "", Color.CYAN, 0, "Вы принимаете игру?" + "\n\n" + "Ставка: " + "`" + bet + "`" + "\n" + "Оставшееся время: 5 минут");
                        searchMessage.addReaction(event.getJDA().getEmoteById("729000468730085426")).complete();
                    } else {
                        customManager.embedNotification(textChannel, event.getMember(), "<:xmark:732621758787616808>", Color.RED, 0, "У вас недостаточно средств");
                    }
                } else {
                    customManager.embedNotification(textChannel, event.getMember(), "<:xmark:732621758787616808>", Color.RED, 0, "В качестве аргумента могут быть использованы только числа");
                }

            } else if (event.getChannel().getId().equalsIgnoreCase("732375187252838521") && message[0].equalsIgnoreCase("!russian-roulette") && message.length < 2) {
                customManager.embedNotification(textChannel, event.getMember(), "<:xmark:732621758787616808>", Color.RED, 0, "Правильное использование команды: !russian-roulette" + "`ставка`");
            }
        } else if (!event.getAuthor().isBot()) {
            customManager.embedNotification(textChannel, event.getMember(), "<:xmark:732621758787616808>", Color.RED, 0, "В данный момент кто-то уже играет, попробуйте позже");
        }
    }

    public void Game(Event event) {
        int randomIntFirst;
        int randomIntSecond;


        randomIntFirst = (int) (Math.random() * 7);
        textChannel.sendMessage(firstMemberInGame.getEffectiveName() + ", Если выпадает число " + randomIntFirst + ", то вы будете убиты!").queueAfter(2, TimeUnit.SECONDS);
        randomIntSecond = (int) (Math.random() * 7);
        textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Раскручиваем барабан, подносим к виску" + "\n" + "```").queueAfter(4, TimeUnit.SECONDS);
        randomIntSecond = (int) (Math.random() * 7);
        textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Спускаем курок" + "\n" + "```").queueAfter(6, TimeUnit.SECONDS);
        randomIntSecond = (int) (Math.random() * 7);
        if (randomIntSecond == randomIntFirst) {
            textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Бах!" + "\n" + "```").queueAfter(8, TimeUnit.SECONDS);
            customManager.embedNotification(textChannel, secondMemberInGame, "<:accept:729000468730085426>", Color.GREEN, 10, "Победитель " + secondMemberInGame.getEffectiveName());
            connectionManager.onAddScore(secondMemberInGame.getId(), bet);
            connectionManager.onRemoveScore(firstMemberInGame.getId(), bet);
//            textChannel.sendMessage(firstMemberInGame + ", вы проиграли! Теперь ваш баланс состовляет: ").queueAfter(10, TimeUnit.SECONDS);
        } else {
            textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Слышен щелчок, похоже вам везёт!" + "\n" + "```").queueAfter(8, TimeUnit.SECONDS);
            textChannel.sendMessage(secondMemberInGame.getEffectiveName() + ", Если выпадает число " + randomIntFirst + ", то вы будете убиты!").queueAfter(10, TimeUnit.SECONDS);
            textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Передаём оружие второму игроку" + "\n" + "```").queueAfter(12, TimeUnit.SECONDS);
            randomIntSecond = (int) (Math.random() * 7);
            textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Спускаем курок" + "\n" + "```").queueAfter(14, TimeUnit.SECONDS);

            if (randomIntSecond == randomIntFirst) {
                textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Бах!" + "\n" + "```").queueAfter(16, TimeUnit.SECONDS);
                customManager.embedNotification(textChannel, firstMemberInGame, "<:accept:729000468730085426>", Color.RED, 18, "Победитель " + firstMemberInGame.getEffectiveName());
                connectionManager.onAddScore(firstMemberInGame.getId(), bet);
                connectionManager.onRemoveScore(secondMemberInGame.getId(), bet);
//                textChannel.sendMessage(secondMemberInGame.getEffectiveName() + ", вы проиграли! Теперь ваш баланс состовляет: ").queueAfter(18, TimeUnit.SECONDS);
            } else {
                textChannel.sendMessage("```basic" + "\n" + randomIntSecond + "︱Слышен щелчок, похоже вам везёт!" + "\n" + "```").queueAfter(16, TimeUnit.SECONDS);
                customManager.embedNotification(textChannel, firstMemberInGame, "<:accept:729000468730085426>", Color.GREEN, 18, "Ничья!");
            }

        }
    }

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

        if (searchMessage == null) {
            return;
        }
        if (event.getMessageId().equalsIgnoreCase(searchMessage.getId()) && !event.getUser().isBot() && !isGame) {
            if (event.getReactionEmote().getId().equalsIgnoreCase("729000468730085426")) {
                if (event.getMember().getId().equalsIgnoreCase(firstMemberInGame.getId())) {
                    return;
                }

                int money = 0;
                money = connectionManager.getMoney(event.getMember().getId());

                if (money >= searchBet) {
                    isGame = true;
                    new Thread(this::sleepMethod).start();
                    secondMemberInGame = event.getMember();
                    Game(event);
                    gameActivity[gameId] = true;
                } else {
                    customManager.embedNotification(textChannel, event.getMember(), "<:xmark:732621758787616808>", Color.RED, 0, "У вас недостаточно средств");
                }
            }
        }
    }

    private void sleepMethod() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isGame = false;
        System.out.println(isGame);
        isSearching = false;
        secondMemberInGame = null;
        firstMemberInGame = null;
        searchMessage = null;
    }

    public void timeout(int gameId, Member member) {
        try {
            Thread.sleep(300000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!gameActivity[gameId]) {
            customManager.embedNotification(textChannel, member, "<:xmark:732621758787616808>", Color.RED, 0, "У вас недостаточно средств");
        }

    }
}
